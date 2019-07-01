package client.view.gui.javafx_controllers.in_game;

import client.view.gui.javafx_controllers.in_game.components.Chat;
import client.view.gui.javafx_controllers.in_game.components.Map;
import client.view.gui.javafx_controllers.in_game.components.Top;
import client.view.gui.javafx_controllers.in_game.components.card_spaces.CardHolder;
import client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands.PowerUpHand;
import client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands.WeaponHand;
import client.view.gui.javafx_controllers.in_game.components.pc_board.PcBoard;
import client.view.gui.javafx_controllers.AbstractJavaFxController;
import common.dto_model.KillShotTrackDTO;
import common.dto_model.PcDTO;
import common.dto_model.SquareDTO;
import common.enums.AmmoEnum;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.rmi.RemoteException;

public class InGameController extends AbstractJavaFxController {
   public HBox bottom;
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
   private final ObjectProperty<PcColourEnum> color = new SimpleObjectProperty<>(PcColourEnum.GREEN);
   private BooleanProperty finalFrenzy = new SimpleBooleanProperty( false );
   private ObservableMap<PcColourEnum,PcDTO> pcs = FXCollections.observableHashMap();
   private ObservableMap<SquareDTO,SquareDTO> squares = FXCollections.observableHashMap();
   private ObjectProperty<KillShotTrackDTO> killShotTrackData = new SimpleObjectProperty<>();
   
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
      //TODO: set map for test
      mapController.setMap(0);
      //init pc listeners
      pcs.addListener( mapController.playerObserver );
      squares.addListener( topController.squareListener );
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
      setPlayer( player );
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
    public void onGameBoardUpdate(GameBoardEvent event) throws RemoteException{
      for(SquareDTO s:event.getDTO().getSquares())
         squares.put( s,s );
    }
   
   @Override
   public void chatMessage(String message) throws RemoteException {
      chatController.showUserMessage( message );
   }
   
    @Override
    public void onKillShotTrackUpdate(KillShotTrackEvent event) throws RemoteException {
      killShotTrackData.set( event.getDTO() );
    }

    @Override
    public void onPcBoardUpdate(PcBoardEvent event) throws RemoteException {
      PcDTO relatedPc = pcs.get( event.getDTO().getColour() );
      relatedPc.setPcBoard( event.getDTO() );
      pcs.put( relatedPc.getColour(),relatedPc );
      //TODO: testalo, non sono sicuro che triggheri i listener
    }

    @Override
    public void onPcUpdate(PcEvent event) throws RemoteException{
      pcs.put( event.getDTO().getColour(),event.getDTO() );
    }

    @Override
    public void onSquareUpdate(SquareEvent event) throws RemoteException {
      squares.put( event.getDTO(),event.getDTO() );
    }
}