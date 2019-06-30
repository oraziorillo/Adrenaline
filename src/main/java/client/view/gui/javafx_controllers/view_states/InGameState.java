package client.view.gui.javafx_controllers.view_states;

import client.view.gui.javafx_controllers.components.Chat;
import client.view.gui.javafx_controllers.components.Map;
import client.view.gui.javafx_controllers.components.Top;
import client.view.gui.javafx_controllers.components.card_spaces.CardHand;
import client.view.gui.javafx_controllers.components.card_spaces.CardHolder;
import client.view.gui.javafx_controllers.components.pc_board.PcBoard;
import common.dto_model.*;
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

import java.io.IOException;
import java.rmi.RemoteException;

public class InGameState extends ViewState {
   @FXML private GridPane killShotTrack;
   @FXML private Map mapController;
   @FXML private CardHolder cardHolderLeftController;
   @FXML private CardHolder cardHolderRightController;
   @FXML private CardHand<WeaponCardDTO> weaponHandController;
   @FXML private CardHand<PowerUpCardDTO> powerUpHandController;
   @FXML private HBox underMapButtons;
   @FXML private Top topController;
   @FXML private Chat chatController;
   @FXML private PcBoard pcBoardController;
   private final ObjectProperty<PcColourEnum> color = new SimpleObjectProperty<>(PcColourEnum.GREEN);
   private BooleanProperty finalFrenzy = new SimpleBooleanProperty( false );
   private ObservableMap<PcColourEnum,PcDTO> pcs = FXCollections.observableHashMap();
   private ObservableMap<SquareDTO,SquareDTO> squares = FXCollections.observableHashMap();
   private ObjectProperty<KillShotTrackDTO> killShotTrackData = new SimpleObjectProperty<>();
   
   public void initialize() {
      mapController.setMap(0);
      //init pc listeners
      pcs.addListener( mapController.playerObserver );
      //init squares listeners
      squares.addListener( mapController.squareObserver );
      squares.addListener( cardHolderLeftController );
      squares.addListener( cardHolderRightController );
      squares.addListener( topController.cardHolderController );
      //dispose card holders and set colors
      cardHolderLeftController.setCorner(CardinalDirectionEnum.WEST);
      cardHolderLeftController.setColor( AmmoEnum.RED );
      cardHolderRightController.setCorner(CardinalDirectionEnum.EAST);
      cardHolderRightController.setColor( AmmoEnum.YELLOW );
      topController.cardHolderController.setCorner( CardinalDirectionEnum.NORTH );
      topController.cardHolderController.setColor( AmmoEnum.BLUE );
      //pass host services
      topController.setHostServices( hostServices );
      //make under map buttons overlap a little
      for (int i = 0, size = underMapButtons.getChildren().size(); i < size; i++) {
         Node n = underMapButtons.getChildren().get(i);
         n.setTranslateX(2 * (size - i));
         n.setViewOrder(i);
      }
      test();
   }
   
   InGameState() throws RemoteException {
       super();
      this.color.set( PcColourEnum.GREEN );
   }

   private void test() {
      /*
      WeaponCardDTO[] weapons = new WeaponCardDTO[3];
      PowerUpCardDTO[] powerups = new PowerUpCardDTO[3];
      for (int i = 0; i < 3; i++) {
         weapons[i] = new WeaponCardDTO( "martello_ionico", 1, 1 );
         powerups[i] = PowerUpCardDTO.getCardBack();
         System.out.println(powerups[i].getImagePath());
      }
      weaponHandController.setCards( weapons );
      powerUpHandController.setCards( powerups );
      for(int i=0;i<PcColourEnum.values().length;i++){
         SquareDTO s=new SquareDTO();
         s.setRow( 1 );
         s.setCol( 3 );
         PcDTO pc = new PcDTO();
         pc.setColour( PcColourEnum.values()[i] );
         pc.setCurrSquare( s );
      }

       */
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
   public ViewState nextState() throws RemoteException {
      return new UserAuthState();
   }
   
   @Override
   public void setPlayer(RemotePlayer player) {
      super.setPlayer( player );
      topController.setPlayer(player);
      chatController.setPlayer(player);
      cardHolderRightController.setPlayer( player );
      cardHolderLeftController.setPlayer( player );
      topController.cardHolderController.setPlayer( player );
      
   }
   
   @Override
   public void ack(String message) {
      chatController.showServerMessage(message);
      chatController.appear();
   }
   
   @Override
   public ModelEventListener getListener() throws RemoteException {
      return null;
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
      //TODO: kill shot track update
    }

    @Override
    public void onPcBoardUpdate(PcBoardEvent event) throws RemoteException {
      PcDTO relatedPc = pcs.get( event.getDTO().getColour() );
      relatedPc.setPcBoard( event.getDTO() );
      pcs.put( relatedPc.getColour(),relatedPc );

    }

    @Override
    public void onPcUpdate(PcEvent event) throws RemoteException{
      pcs.put( event.getDTO().getColour(),event.getDTO() );
    }

    @Override
    public void onSquareUpdate(SquareEvent event) throws RemoteException {
      squares.put( event.getDTO(),event.getDTO() );
    }
   
   @Override
   public String requestString(String message) throws RemoteException {
      return null;
   }
}