package client.controller;

import client.controller.socket.LoginControllerSocketProxy;
import client.view.gui.controllers.*;
import common.enums.CardinalDirectionEnum;
import common.model_dtos.PowerUpCardDTO;
import common.model_dtos.WeaponCardDTO;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.UUID;


public class GuiController implements RemoteView, AbstractClientController {
   @FXML
   GridPane killShotTrack;
   @FXML
   Map mappaController;
   @FXML
   CardHolder cardHolderLeftController;
   @FXML
   CardHolder cardHolderRightController;
   @FXML
   CardHand<WeaponCardDTO> weaponHandController;
   @FXML
   CardHand<PowerUpCardDTO> powerUpHandController;
   @FXML
   HBox underMapButtons;
   @FXML
   Top topController;
   @FXML
   Chat chatController;
   private RemotePlayer player;


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
         weaponHandController.setCard( new WeaponCardDTO( "martello_ionico", 1, 1 ), i );
         powerUpHandController.setCard( new PowerUpCardDTO(), i );
      }
   }

   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }

   public void setHostServices(HostServices hostServices) {
      topController.setHostServices(hostServices);
   }

   @Override
   public void ack(String message) {
      chatController.shoWMessage(message);
   }

   @Override
   public RemoteLoginController getLoginController() throws IOException, NotBoundException {
      RemoteLoginController loginController;
      Alert rmiOrSocket = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to connect with socket?\nI suggest it, cause rmi is just 2 lines of code", new ButtonType("Socket"), new ButtonType("Rmi"));
      rmiOrSocket.setHeaderText(null);
      rmiOrSocket.setTitle("Select connection");
      rmiOrSocket.setOnCloseRequest(event -> System.exit(1));
      System.out.println("Modificato");
      Optional<ButtonType> resonse = rmiOrSocket.showAndWait();
      switch (resonse.get().getText().toLowerCase()) {
         case "rmi":
            Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
            loginController = (RemoteLoginController) registry.lookup("LoginController");
            break;
         case "socket":
         default:
            loginController = new LoginControllerSocketProxy(HOST, SOCKET_PORT);
            break;
      }
      return loginController;
   }

   @Override
   public RemotePlayer loginRegister(RemoteLoginController loginController) throws IOException {
      //TODO sdoppiare questa classe
      UUID token = null;
      Alert firstTime = new Alert(
              Alert.AlertType.CONFIRMATION,
              "is this your first time?",
              new ButtonType("Login", ButtonBar.ButtonData.NO),
              new ButtonType("Register", ButtonBar.ButtonData.YES)
      );
      switch (firstTime.showAndWait().get().getButtonData()) {
         case YES:
            TextInputDialog usernameDialog = new TextInputDialog();
            usernameDialog.setTitle("Username");
            usernameDialog.setHeaderText(null);
            usernameDialog.setContentText("Insert your username");
            String username = usernameDialog.showAndWait().orElse("username");
            //TODO:handle username already used exception
            token = loginController.register(username, this);
            System.out.println("Registrazione");
            return loginController.login(token);
         case NO:
            System.out.println("Login");
            //TODO il token è sempre nullo qui
            return loginController.login(token);
         default:
            System.exit(1);
            return null;
      }
   }
}