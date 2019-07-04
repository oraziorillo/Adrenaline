package client.view.gui.javafx_controllers.authentication;

import client.ClientPropertyLoader;
import client.controller.socket.LoginControllerSocketProxy;
import client.view.gui.javafx_controllers.AbstractJavaFxController;
import common.dto_model.GameDTO;
import common.enums.ConnectionMethodEnum;
import common.enums.ControllerMethodsEnum;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.requests.Request;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteLoginController;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.UUID;

import static common.enums.ControllerMethodsEnum.LOG_IN;
import static common.enums.ControllerMethodsEnum.SIGN_UP;

public class UserAuthController extends AbstractJavaFxController {
   
   public UserAuthController() {
   }
   
   @Override
   public void ack(String message) {
   
   }
   
   @Override
   public void chatMessage(String message) {
   
   }
   
   @Override
   public void notifyEvent(LobbyEvent event) {

   }

   @Override
   public void request(Request request) {

   }
   
   @Override
   public ConnectionMethodEnum acquireConnectionMethod(){
      Alert rmiOrSocket = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to connect with socket?"+System.lineSeparator()+"I suggest it, cause rmi is just 2 lines of code", new ButtonType("SOCKET"), new ButtonType("RMI"));
      rmiOrSocket.setHeaderText(null);
      rmiOrSocket.setTitle("Select connection");
      Optional<ButtonType> response = rmiOrSocket.showAndWait();
      return response.isPresent()?ConnectionMethodEnum.parseString(response.get().getText().toLowerCase()):ConnectionMethodEnum.QUIT;
   }
   
   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
      try {
         switch (cme) {
            case RMI:
               Registry registry = LocateRegistry.getRegistry( ClientPropertyLoader.getInstance().getHostAddress(), ClientPropertyLoader.getInstance().getRmiPort());
               return ( RemoteLoginController ) registry.lookup("LoginController");
            case SOCKET:
            default:
               return new LoginControllerSocketProxy( new Socket( ClientPropertyLoader.getInstance().getHostAddress(),ClientPropertyLoader.getInstance().getSocketPort() ),topView );
         }
      } catch ( IOException | NotBoundException connectionEx) {
         error("Server unreachable");
         connectionEx.printStackTrace();
         return null;    //This line won't be executed, but is needed to avoid variable initialization error on return
      }
   }
   
   @Override
   public ControllerMethodsEnum authMethod() {
      Alert firstTime = new Alert(
              Alert.AlertType.CONFIRMATION,
              "is this your first time?",
              new ButtonType("Login", ButtonBar.ButtonData.NO),
              new ButtonType("Register", ButtonBar.ButtonData.YES)
      );
      return firstTime.showAndWait().get().getButtonData().equals( ButtonBar.ButtonData.YES )?SIGN_UP:LOG_IN;
   }
   
   @Override
   public String acquireUsername() {
      TextInputDialog usernameDialog = new TextInputDialog();
      usernameDialog.setTitle("Username");
      usernameDialog.setHeaderText(null);
      usernameDialog.setContentText("Insert your username");
      return usernameDialog.showAndWait().orElse("username");
   }
   
   @Override
   public UUID acquireToken() {
      TextInputDialog tokenDialog = new TextInputDialog("Login");
      tokenDialog.setHeaderText("Insert your token");
      tokenDialog.setContentText(null);
      Button okButton = (Button) tokenDialog.getDialogPane().lookupButton( ButtonType.OK);
      tokenDialog.getEditor().textProperty().addListener((observableValue, s, t1) -> {
         try {
            UUID.fromString(t1);
            tokenDialog.setContentText(null);
            okButton.setVisible(true);
         } catch (IllegalArgumentException illegalUUID) {
            tokenDialog.setContentText("Not a valid token");
            okButton.setVisible(false);
         }
      });
      return UUID.fromString(tokenDialog.showAndWait().get());
   }
   
   @Override
   public void onPcBoardUpdate(PcBoardEvent event) {
   
   }
   
   @Override
   public void onSquareUpdate(SquareEvent event) {
   
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
   
   }
   
   @Override
   public void onPcUpdate(PcEvent event) {
   
   }
   
   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
   
   }
   
   @Override
   public void resumeGame(GameDTO game) {
      throw new IllegalStateException( "Should be in InGameState" );
   }
   
   @Override
   public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
      //nothing to observe
   }
}
