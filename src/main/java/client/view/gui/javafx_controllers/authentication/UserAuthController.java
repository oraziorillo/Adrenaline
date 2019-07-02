package client.view.gui.javafx_controllers.authentication;

import client.controller.socket.ClientSocketHandler;
import client.controller.socket.LoginControllerSocketProxy;
import client.view.gui.GuiView;
import client.view.gui.javafx_controllers.AbstractJavaFxController;
import common.enums.ConnectionMethodEnum;
import common.events.ModelEventListener;
import common.remote_interfaces.RemoteLoginController;
import javafx.concurrent.Task;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static javafx.application.Platform.runLater;

public class UserAuthController extends AbstractJavaFxController {
   private HashSet<Alert> alerts = new HashSet<>();
   private GuiView view;
   
   public UserAuthController() throws RemoteException {
   }
   
   public void setView(GuiView view) {
      this.view = view;
   }
   
   @Override
   public void ack(String message) {
//      Alert infos = new Alert( Alert.AlertType.INFORMATION );
//      infos.setTitle( "infos" );
//      infos.setHeaderText( null );
//      infos.setContentText( "The king of the arena says:" );
//      infos.setResizable( true );
//      TextArea messageArea = new TextArea(message);
//      messageArea.setWrapText( true );
//      messageArea.setEditable( false );
//      infos.getDialogPane().setExpandableContent( messageArea );
//      infos.getDialogPane().setExpanded( true );
//      infos.setOnCloseRequest( e->alerts.remove( infos ) );
//      infos.show();
      
   }
   
   @Override
   public void chatMessage(String message) throws RemoteException {
      throw new IllegalStateException( "Can't write in chat" );
   }
   
   @Override
   public ModelEventListener getListener() throws RemoteException {
      return this;
   }
   
   @Override
   public void setEnabled(boolean enabled) {
      //Actually meaningless
   }
   
   
   @Override
   public ConnectionMethodEnum acquireConnectionMethod(){
      RemoteLoginController loginController;
      Alert rmiOrSocket = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to connect with socket?"+System.lineSeparator()+"I suggest it, cause rmi is just 2 lines of code", new ButtonType("SOCKET"), new ButtonType("RMI"));
      rmiOrSocket.setHeaderText(null);
      rmiOrSocket.setTitle("Select connection");
      Optional<ButtonType> response = rmiOrSocket.showAndWait();
      System.out.println(response.get().getText());
      return ConnectionMethodEnum.parseString(response.get().getText().toLowerCase());
   }
   
   
   @Override
   public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
      try {
         switch (cme) {
            case RMI:
               Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
               return ( RemoteLoginController ) registry.lookup("LoginController");
            case SOCKET:
            default:
//               ClientSocketHandler handler = new ClientSocketHandler(new Socket(HOST, SOCKET_PORT), this);
//               Thread thread = new Thread(new Task<>() {
//                  @Override
//                  protected Object call() throws Exception {
//                     handler.run();
//                     return null;
//                  }
//               }, "SocketHandler");
//               thread.start();
//               return handler;
               return new LoginControllerSocketProxy( new Socket( HOST,SOCKET_PORT ),topView );
         }
      } catch ( IOException | NotBoundException connectionEx) {
         error("Server unreachable");
         connectionEx.printStackTrace();
         return null;    //This line won't be executed, but is needed to avoid variable initialization error on return
      }
   }
   
   
   @Override
   public boolean wantsToRegister() {
      Alert firstTime = new Alert(
              Alert.AlertType.CONFIRMATION,
              "is this your first time?",
              new ButtonType("Login", ButtonBar.ButtonData.NO),
              new ButtonType("Register", ButtonBar.ButtonData.YES)
      );
      return firstTime.showAndWait().get().getButtonData().equals(ButtonBar.ButtonData.YES);
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
   public String requestString(String message) {
      TextInputDialog input = new TextInputDialog("Gimme strinnngs");
      input.setHeaderText(null);
      return input.showAndWait().orElse( "" );
   }
}
