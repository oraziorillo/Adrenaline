package client.gui.controllers;

import client.gui.GuiView;
import client.gui.controllers.components.Chat;
import client.gui.controllers.components.Map;
import client.gui.controllers.components.Top;
import client.gui.controllers.components.card_spaces.CardHand;
import client.gui.controllers.components.card_spaces.CardHolder;
import common.enums.CardinalDirectionEnum;
import common.model_dtos.PowerUpCardDTO;
import common.model_dtos.WeaponCardDTOFirstVersion;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class MainGui extends GuiView {
   @FXML
   GridPane killShotTrack;
   @FXML
   Map mappaController;
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
   
   
   public void initialize() {
      mappaController.setMap(0);
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
   public void ack(String message) throws IOException {
      chatController.shoWMessage( message );
      chatController.appear();
   }
}