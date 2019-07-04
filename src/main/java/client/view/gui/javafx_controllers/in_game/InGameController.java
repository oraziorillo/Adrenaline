package client.view.gui.javafx_controllers.in_game;

import client.ClientPropertyLoader;
import client.view.gui.javafx_controllers.in_game.components.Chat;
import client.view.gui.javafx_controllers.in_game.components.Map;
import client.view.gui.javafx_controllers.in_game.components.Top;
import client.view.gui.javafx_controllers.in_game.components.card_spaces.CardHolder;
import client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands.PowerUpHand;
import client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands.WeaponHand;
import client.view.gui.javafx_controllers.in_game.components.pc_board.PcBoard;
import client.view.gui.javafx_controllers.AbstractJavaFxController;
import common.Constants;
import common.dto_model.KillShotTrackDTO;
import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.dto_model.SquareDTO;
import common.enums.AmmoEnum;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.requests.Request;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

public class InGameController extends AbstractJavaFxController {
   public HBox bottom;
   @FXML private BorderPane mainPane;
   @FXML private GridPane killShotTrack;
   @FXML private Map mapController;
   @FXML private CardHolder cardHolderLeftController;
   @FXML private CardHolder cardHolderRightController;
   private WeaponHand weaponHandController = new WeaponHand();
   private PowerUpHand powerUpHandController = new PowerUpHand();
   @FXML private HBox underMapButtons;
   @FXML private Top topController;
   @FXML private Chat chatController;
   @FXML private PcBoard pcBoardController;
   private transient BooleanProperty finalFrenzy = new SimpleBooleanProperty( false );
   private transient ObservableMap<PcColourEnum,PcDTO> pcs = FXCollections.observableHashMap();
   private transient ObservableMap<SquareDTO,SquareDTO> squares = FXCollections.observableHashMap();
   private transient ObjectProperty<KillShotTrackDTO> killShotTrackData = new SimpleObjectProperty<>();
   
   public InGameController() throws RemoteException {
   }
   
   public void initialize() {
      //Add player hands
      bottom.getChildren().add( spacerFactory() );
      bottom.getChildren().add( powerUpHandController.getNode() );
      bottom.getChildren().add( spacerFactory() );
      bottom.getChildren().add( weaponHandController.getNode() );
      bottom.getChildren().add( spacerFactory() );
      bottom.getChildren().get( 1 ).toFront();   //move chat to the right
      //init pc listeners
      pcs.addListener( mapController.playerObserver );
      pcs.addListener( topController.pcListener );
      pcs.addListener( pcBoardController );
      //init squares listeners
      squares.addListener( mapController.squareObserver );
      squares.addListener( cardHolderLeftController );
      squares.addListener( cardHolderRightController );
      squares.addListener( topController.squareListener );
      //init killshottrack listeners
      killShotTrackData.addListener(topController);
      //dispose card holders and set colors
      cardHolderLeftController.setCorner(CardinalDirectionEnum.WEST);
      cardHolderLeftController.setColor( AmmoEnum.RED );
      cardHolderRightController.setCorner(CardinalDirectionEnum.EAST);
      cardHolderRightController.setColor( AmmoEnum.YELLOW );
      //pass host services
      topController.setHostServices( hostServices );
      //make under map buttons overlap a little
      for (int i = 0, size = underMapButtons.getChildren().size(); i < size; i++) {
         Node n = underMapButtons.getChildren().get(i);
         n.setTranslateX(2 * (size - i));
         n.setViewOrder(i);
      }
   }
   
   private Region spacerFactory(){
      Region spacer = new Region();
      spacer.setMaxWidth( Double.MAX_VALUE );
      HBox.setHgrow( spacer, Priority.ALWAYS );
      return spacer;
   }

   
   //Button methods
   @FXML
   private void passClicked(){
      try {
         player.pass();
      } catch ( IOException e ) {
         error( "Server unreachable" );
      }
   }
   
   @FXML
   private void applyClicked(ActionEvent actionEvent) {
      try {
         player.ok();
      } catch ( IOException e ) {
         error( "Server unreachable" );
      }
   }
   
   @FXML
   private void reloadClicked(ActionEvent actionEvent) {
      try {
         player.reload();
      } catch ( IOException e ) {
         error( "Server unreachable" );
      }
   }
   
   @FXML
   private void skipClicked(ActionEvent actionEvent) {
      try {
         player.skip();
      } catch ( IOException e ) {
         error( "Server unreachable" );
      }
   }
   
   @FXML
   private void undoClicked(){
      try {
         player.undo();
      } catch ( IOException e ) {
         error( "Server unreachable" );
      }
   }
   
   @Override
   public void setHostServices(HostServices hostServices) {
      super.setHostServices(hostServices);
      topController.setHostServices(hostServices);
   }
   
   @Override
   public void setPlayer(RemotePlayer player) {
      super.setPlayer( player );
      topController.setPlayer(player);
      chatController.setPlayer(player);
      cardHolderRightController.setPlayer( player );
      cardHolderLeftController.setPlayer( player );
      mapController.setPlayer( player );
      pcBoardController.setPlayer(player);
      cardHolderLeftController.setPlayer( player );
      cardHolderRightController.setPlayer( player );
      powerUpHandController.setPlayer( player );
      weaponHandController.setPlayer( player );
   }

    @Override
   public void ack(String message) {
      chatController.showServerMessage(message);
      chatController.appear();
   }
   
   @Override
   public ModelEventListener getListener() {
      return this;
   }

   @Override
   public boolean isReachable() throws RemoteException {
      return true;
   }

   @Override
    public void onGameBoardUpdate(GameBoardEvent event) {
      mapController.setMap( event.getDTO().getNumberOfMap() );
      for(SquareDTO s:event.getDTO().getSquares())
         squares.put( s,s );
    }
   
   @Override
   public void chatMessage(String message) {
      chatController.showUserMessage( message );
   }


   @Override
   public void notifyEvent(LobbyEvent event) {

   }

   @Override
   public void request(Request request) {
      try {
         switch (request.toString()) {
            case "Do you wanna use your Tagback Grenade? (yes/no)":
               Alert requestAlert = new Alert( Alert.AlertType.CONFIRMATION, request.toString(), ButtonType.YES, ButtonType.NO );
               Timer timer = new Timer( ClientPropertyLoader.getInstance().getRequestTimer(), e -> requestAlert.close() );
               requestAlert.setHeaderText( null );
               timer.start();
               Optional<ButtonType> response = requestAlert.showAndWait();
               player.response( response.orElse( ButtonType.NO ).getText().toLowerCase() );
               break;
         }
      }catch ( IOException e ){
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }

   @Override
    public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      killShotTrackData.set( event.getDTO() );
    }

    @Override
    public void onPcBoardUpdate(PcBoardEvent event) {
      PcDTO relatedPc = pcs.remove( event.getDTO().getColour() );
      relatedPc.setPcBoard( event.getDTO() );
      pcs.put( relatedPc.getColour(),relatedPc );
    }

    @Override
    public void onPcUpdate(PcEvent event) {
       PcColourEnum eventColor = event.getDTO().getColour();
       if(!event.isCensored()) {
         topController.setColour( eventColor );
         pcBoardController.setPcColour( eventColor );
         powerUpHandController.setCards( event.getDTO().getPowerUps().toArray(new PowerUpCardDTO[Constants.MAX_POWER_UPS_IN_HAND+1]));
         weaponHandController.setCards( event.getDTO().getWeapons() );
         //mapController.setColor
      }
      pcs.put( eventColor,event.getDTO() );
    }

    @Override
    public void onSquareUpdate(SquareEvent event) {
      squares.put( event.getDTO(),event.getDTO() );
    }
   
   @Override
   public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
      //TODO
   }
}