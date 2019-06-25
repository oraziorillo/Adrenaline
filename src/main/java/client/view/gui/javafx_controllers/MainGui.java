package client.view.gui.javafx_controllers;

import client.view.gui.GuiView;
import client.view.gui.javafx_controllers.components.Chat;
import client.view.gui.javafx_controllers.components.Map;
import client.view.gui.javafx_controllers.components.Top;
import client.view.gui.javafx_controllers.components.card_spaces.CardHand;
import client.view.gui.javafx_controllers.components.card_spaces.CardHolder;
import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.dto_model.SquareDTO;
import common.dto_model.WeaponCardDTOFirstVersion;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
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
   private transient ObservableMap<PcColourEnum,PcDTO> pcDTOS = FXCollections.observableHashMap();
   
   public MainGui() throws RemoteException {
   }
   
   
   public void initialize() {
      mapController.setMap(0);
      pcDTOS.addListener( mapController );
      cardHolderLeftController.setCorner(CardinalDirectionEnum.WEST);
      cardHolderRightController.setCorner(CardinalDirectionEnum.EAST);
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
      PcDTO dto1= new PcDTO();
      SquareDTO square1 = new SquareDTO();
      square1.setRow( 2 );
      square1.setCol( 1 );
      dto1.setCurrSquare( square1 );
      dto1.setColour( PcColourEnum.BLUE );
      pcDTOS.put( dto1.getColour(),dto1 );
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
   
   
   //TODO i seguenti due metodi sono stati inseriti solo per non avere errore
   
   @Override
   public PropertyChangeListener getListener() {
      return null;
   }
   
   @Override
   public void propertyChange(PropertyChangeEvent evt) {
   
   }
}