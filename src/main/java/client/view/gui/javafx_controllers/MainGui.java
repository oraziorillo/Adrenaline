package client.view.gui.javafx_controllers;

import client.view.gui.GuiView;
import client.view.gui.javafx_controllers.components.Chat;
import client.view.gui.javafx_controllers.components.Map;
import client.view.gui.javafx_controllers.components.Top;
import client.view.gui.javafx_controllers.components.card_spaces.CardHand;
import client.view.gui.javafx_controllers.components.card_spaces.CardHolder;
import client.view.gui.javafx_controllers.components.pc_board.PcBoard;
import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.dto_model.SquareDTO;
import common.dto_model.WeaponCardDTOFirstVersion;
import common.enums.AmmoEnum;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;

import static common.Constants.*;


public class MainGui extends GuiView {
   @FXML private transient GridPane killShotTrack;
   @FXML private transient Map mapController;
   @FXML private transient CardHolder cardHolderLeftController;
   @FXML private transient CardHolder cardHolderRightController;
   @FXML private transient CardHand<WeaponCardDTOFirstVersion> weaponHandController;
   @FXML private transient CardHand<PowerUpCardDTO> powerUpHandController;
   @FXML private transient HBox underMapButtons;
   @FXML private transient Top topController;
   @FXML private transient Chat chatController;
   @FXML private transient PcBoard pcBoardController;
   private transient BooleanProperty finalFrenzy = new SimpleBooleanProperty( false );
   private transient ObservableMap<PcColourEnum,PcDTO> pcs = FXCollections.observableHashMap();
   private transient ObservableMap<SquareDTO,SquareDTO> squares = FXCollections.observableHashMap();
   
   public MainGui() throws RemoteException {
   }
   
   
   public void initialize() {
      mapController.setMap(0);
      //init finalFrenzy listeners
      finalFrenzy.addListener( pcBoardController );
      //init pc listeners
      pcs.addListener( mapController.playerObserver );
      //init squares listeners
      squares.addListener( mapController.squareObserver );
      //dispose card holders and set colors
      cardHolderLeftController.setCorner(CardinalDirectionEnum.WEST);
      cardHolderRightController.setCorner(CardinalDirectionEnum.EAST);
      cardHolderRightController.setBackgroundColor( AmmoEnum.YELLOW );
      topController.cardHolderController.setBackgroundColor( AmmoEnum.BLUE );
      //make under map buttons overlap a little
      for (int i = 0, size = underMapButtons.getChildren().size(); i < size; i++) {
         Node n = underMapButtons.getChildren().get(i);
         n.setTranslateX(2 * (size - i));
         n.setViewOrder(i);
      }
      test();
   }
   
   private void test() {
      for (int i = 0; i < 3; i++) {
         weaponHandController.setCard( new WeaponCardDTOFirstVersion( "martello_ionico", 1, 1 ), i );
         powerUpHandController.setCard( new PowerUpCardDTO(), i );
      }
      for(int i=0;i<PcColourEnum.values().length;i++){
         SquareDTO s=new SquareDTO();
         s.setRow( 1 );
         s.setCol( 3 );
         PcDTO pc = new PcDTO();
         pc.setColour( PcColourEnum.values()[i] );
         pc.setCurrSquare( s );
      }
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
   
   public void setHostServices(HostServices hostServices) {
      topController.setHostServices(hostServices);
   }
   
   @Override
   public void setPlayer(RemotePlayer player) {
      super.setPlayer( player );
      topController.setPlayer(player);
      chatController.setPlayer(player);
      
   }
   
   @Override
   public void ack(String message) {
      chatController.showServerMessage(message);
      chatController.appear();
   }
   
   /**
    * Cause every message is immediatly displayed in the chat, no acks are pending
    *
    * @return an empty Collection
    */
   @Override
   public Collection<String> getPendingAcks() {
      return new HashSet<>();
   }
   
   @Override
   public PropertyChangeListener getListener() {
      return this;
   }
   
   @Override
   public void propertyChange(PropertyChangeEvent evt) {
      switch (evt.getPropertyName()){
         case MOVE_TO: case DRAW_POWER_UP: case DISCARD_POWER_UP: case KILL_OCCURRED: case ADRENALINE_UP: case SPAWN://Eventi Pc
            PcDTO pc = ( PcDTO ) evt.getNewValue();
            pcs.put( pc.getColour(),pc );
            break;
         case SET_TARGETABLE: case COLLECT: case REFILL: //Eventi square
            SquareDTO square = ( SquareDTO ) evt.getNewValue();
            squares.put( square,square );
            break;
         case FINAL_FRENZY:   //eventi game
            finalFrenzy.setValue( ( Boolean ) evt.getNewValue() );
            break;
         case ADD_AMMO: case ADD_DAMAGE: case ADD_MARKS: case PAY_AMMO: case INCREASE_NUMBER_OF_DEATHS: case INCREASE_POINTS: //eventi PcBoard
            //TODO: gestisci eventi pcBoard
      }
   }
}