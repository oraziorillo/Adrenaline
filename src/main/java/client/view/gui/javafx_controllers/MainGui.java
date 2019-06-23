package client.view.gui.javafx_controllers;

import client.view.gui.GuiView;
import client.view.gui.javafx_controllers.components.Chat;
import client.view.gui.javafx_controllers.components.Map;
import client.view.gui.javafx_controllers.components.Top;
import client.view.gui.javafx_controllers.components.card_spaces.CardHand;
import client.view.gui.javafx_controllers.components.card_spaces.CardHolder;
import common.dto_model.PcBoardDTO;
import common.dto_model.PowerUpCardDTO;
import common.dto_model.WeaponCardDTO;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import common.model_dtos.WeaponCardDTOFirstVersion;
import common.remote_interfaces.ModelChangeListener;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;


public class MainGui extends GuiView {
   @FXML
   GridPane killShotTrack;
   @FXML
   Map mapController;
   @FXML
   CardHolder cardHolderLeftController;
   @FXML
   CardHolder cardHolderRightController;
   @FXML
   CardHand<WeaponCardDTOFirstVersion> weaponHandController;
   @FXML
   CardHand<PowerUpCardDTO> powerUpHandController;
   @FXML
   HBox underMapButtons;
   @FXML
   Top topController;
   @FXML
   Chat chatController;

   public MainGui() throws RemoteException {
   }


   public void initialize() {
      mapController.setMap(0);
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
   }

   public void setPlayer(RemotePlayer player) {
   }

   public void setHostServices(HostServices hostServices) {
      topController.setHostServices(hostServices);
   }

   @Override
   public void ack(String message) {
      chatController.showServerMessage(message);
      chatController.appear();
   }

   @Override
   public ModelChangeListener getListener() {
      return null;
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
   public void onSquareTargetableChange(int row, int col, boolean newValue) {

   }

   @Override
   public void onMovement(PcColourEnum pc, int oldRow, int oldCol, int newRow, int newCol) {

   }

   @Override
   public void onWeaponCollect(PcColourEnum pc, int droppedWeapon, int grabbedWeapon) {

   }

   @Override
   public void onAmmoCollect(PcColourEnum pc) {

   }

   @Override
   public void onDrawPowerUp(PcColourEnum pc, int newIndex) {

   }

   @Override
   public void onDiscardPowerUp(PcColourEnum pc, int oldIndex) {

   }

   @Override
   public void onPcBoardChange(PcBoardDTO newPcBoard) {

   }

   @Override
   public void onRefill(int typeOfDeck, int row, int col) {

   }

   @Override
   public void onKill(PcColourEnum shooter, PcColourEnum killed, boolean isOverkill) {

   }

   @Override
   public void onSpawn(PcColourEnum pc, int newRow, int newCol) {

   }

   @Override
   public void onAdrenaline(int level) {

   }

   @Override
   public void onFinalFrenzy() {

   }
}