package client.view.gui.javafx_controllers.view_states;

import client.controller.socket.ClientSocketHandler;
import common.remote_interfaces.RemoteLoginController;
import javafx.concurrent.Task;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.UUID;

import static javafx.application.Platform.runLater;

public class UserAuthState extends ViewState {
   
   
   /**
    * Builds an alert containing the ack and shows it
    * @param message the message of the ack
    */
   @Override
   public synchronized void ack(String message) {
      runLater( () -> {
         Alert infos = new Alert( Alert.AlertType.INFORMATION );
         infos.setTitle( "infos" );
         infos.setHeaderText( null );
         infos.setContentText( "The king of the arena says:" );
         infos.setResizable( true );
         TextArea messageArea = new TextArea(message);
         messageArea.setWrapText( true );
         messageArea.setEditable( false );
         infos.getDialogPane().setExpandableContent( messageArea );
         infos.getDialogPane().setExpanded( true );
         infos.show();
      } );
   }
   
   
   @Override
   public RemoteLoginController acquireConnection() throws RemoteException {
      RemoteLoginController loginController;
      Alert rmiOrSocket = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to connect with socket?"+System.lineSeparator()+"I suggest it, cause rmi is just 2 lines of code", new ButtonType("SOCKET"), new ButtonType("RMI"));
      rmiOrSocket.setHeaderText(null);
      rmiOrSocket.setTitle("Select connection");
      Optional<ButtonType> resonse = rmiOrSocket.showAndWait();
      try {
         switch (resonse.get().getText().toLowerCase()) {
            case "rmi":
               Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
               loginController = (RemoteLoginController) registry.lookup("LoginController");
               break;
            case "socket":
            default:
               ClientSocketHandler handler = new ClientSocketHandler(new Socket(HOST, SOCKET_PORT));
               loginController = handler;
               Thread thread = new Thread(new Task<>() {
                  @Override
                  protected Object call() throws Exception {
                     handler.run();
                     return null;
                  }
               }, "SocketHandler");
               thread.start();
               break;
         }
      } catch ( IOException | NotBoundException connectionEx) {
         error("Server unreachable");
         connectionEx.printStackTrace();
         return null;    //This line won't be executed, but is needed to avoid variable initialization error on return
      }
      return loginController;
   }
   
   @Override
   public boolean wantsToRegister() throws RemoteException {
      Alert firstTime = new Alert(
              Alert.AlertType.CONFIRMATION,
              "is this your first time?",
              new ButtonType("Login", ButtonBar.ButtonData.NO),
              new ButtonType("Register", ButtonBar.ButtonData.YES)
      );
      return firstTime.showAndWait().get().getButtonData().equals(ButtonBar.ButtonData.YES);
   }
   
   @Override
   public String acquireUsername() throws RemoteException {
      TextInputDialog usernameDialog = new TextInputDialog();
      usernameDialog.setTitle("Username");
      usernameDialog.setHeaderText(null);
      usernameDialog.setContentText("Insert your username");
      return usernameDialog.showAndWait().orElse("username");
   }
   
   @Override
   public UUID acquireToken() throws RemoteException {
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
   public String requestString(String message) throws RemoteException {
      TextInputDialog input = new TextInputDialog("Gimme strinnngs");
      input.setHeaderText(null);
      return input.showAndWait().orElse( "" );
   }
   
   @Override
   public ViewState nextState() {
      return new InGameState();
   }
}
