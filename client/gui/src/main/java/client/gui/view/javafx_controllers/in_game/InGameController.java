package client.gui.view.javafx_controllers.in_game;

import client.ClientPropertyLoader;
import client.gui.view.javafx_controllers.AbstractJavaFxController;
import client.gui.view.javafx_controllers.in_game.components.Chat;
import client.gui.view.javafx_controllers.in_game.components.Map;
import client.gui.view.javafx_controllers.in_game.components.Top;
import client.gui.view.javafx_controllers.in_game.components.card_spaces.CardHolder;
import client.gui.view.javafx_controllers.in_game.components.card_spaces.player_hands.PowerUpHand;
import client.gui.view.javafx_controllers.in_game.components.card_spaces.player_hands.WeaponHand;
import client.gui.view.javafx_controllers.in_game.components.pc_board.PcBoard;
import common.Constants;
import common.dto_model.*;
import common.enums.*;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.requests.Request;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
   private ObservableMap<PcColourEnum,PcDTO> pcs = FXCollections.observableHashMap();
   private ObservableMap<SquareDTO,SquareDTO> squares = FXCollections.observableHashMap();
   private ObjectProperty<KillShotTrackDTO> killShotTrackData = new SimpleObjectProperty<>();
   public static final Effect selectedObjectEffect = new DropShadow( 20, Color.WHITESMOKE );
   
   public InGameController() {
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
      killShotTrackData.addListener( pcBoardController );
      
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
   
   private void deselectAll(){
      mapController.deselectAll();
      cardHolderLeftController.deselectAll();
      cardHolderRightController.deselectAll();
      weaponHandController.deselectAll();
      powerUpHandController.deselectAll();
      topController.deselectAll();
      chatController.disappear();
   }
   
   
   //Button methods
   @FXML
   private void passClicked(){
      try {
         player.pass();
         deselectAll();
      } catch ( IOException e ) {
         error( "Server unreachable" );
      }
   }
   
   @FXML
   private void applyClicked(ActionEvent actionEvent) {
      try {
         player.ok();
         deselectAll();
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
      deselectAll();

   }

   @Override
   public void request(Request request) {
      String grenade = "Do you wanna use your Tagback Grenade? (yes/no)";
      try {
         Alert alert = new Alert( Alert.AlertType.CONFIRMATION );
         alert.setContentText( request.toString() );
         alert.setHeaderText( null );
         alert.setOnCloseRequest( Event::consume );
         if(request.toString().equalsIgnoreCase( grenade )){
            Timer timer = new Timer( ClientPropertyLoader.getInstance().getRequestTimer(), e -> alert.close() );
            timer.start();
         }
         String ans = alert.showAndWait().orElse( new ButtonType( request.getChoices().get(request.getChoices().size()-1) ) ).toString();
         player.response( ans );
         if(request.toString().equalsIgnoreCase( grenade ) && "yes".equalsIgnoreCase( ans )){
            Stage stage = new Stage();
            stage.setAlwaysOnTop( true );
            stage.setResizable( false );
            HashMap<Node,EventHandler> clickHandlers= new HashMap<>();
            for(Node n:powerUpHandController.getCardNodes()){
               clickHandlers.put( n,n.getOnMouseClicked() );
               n.setOnMouseClicked( mouseEvent -> {
                  clickHandlers.get( n ).handle( mouseEvent );
                  stage.close();
               } );
            }
            stage.setOnCloseRequest( e ->{
               for(Node n: clickHandlers.keySet()){
                  n.setOnMouseClicked( clickHandlers.get( n ) );
               }
            } );
         }
      }catch ( IOException e ){
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   @Override
   public ConnectionMethodEnum acquireConnectionMethod() {
      return null;
   }
   
   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
      return null;
   }
   
   @Override
   public ControllerMethodsEnum authMethod() {
      return null;
   }
   
   @Override
   public String acquireUsername() {
      return null;
   }
   
   @Override
   public UUID acquireToken() {
      return null;
   }
   
   @Override
    public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      killShotTrackData.set( event.getDTO() );
    }
   
   @Override
   public void resumeGame(GameDTO game) {
      mapController.setMap( game.getGameBoardDTO().getNumberOfMap() );
      for(SquareDTO s: game.getGameBoardDTO().getSquares())
         squares.put( s,s );
      for(PcDTO pc:game.getPcs())
         pcs.put( pc.getColour(),pc );
      killShotTrackData.set( game.getKillShotTrackDTO() );
      
   }
   
   @Override
   public void winners(List<String> gameWinners) {
   
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
      }
      pcs.put( eventColor,event.getDTO() );
    }

    @Override
    public void onSquareUpdate(SquareEvent event) {
      
      squares.put( event.getDTO(),event.getDTO() );
    }
   
   @Override
   public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
   }
   
   
}