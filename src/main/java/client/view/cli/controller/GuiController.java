package client.view.cli.controller;

import client.socket.proxies.SocketLoginController;
import common.rmi_interfaces.RemoteLoginController;
import common.rmi_interfaces.RemotePlayer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.UUID;

public class GuiController implements AbstractClientController {
   
   @Override
   public RemoteLoginController getLoginController() throws IOException, NotBoundException {
      RemoteLoginController loginController;
      Alert rmiOrSocket = new Alert( Alert.AlertType.CONFIRMATION, "Do you want to connect with socket?\nI suggest it, cause rmi is just 2 lines of code", new ButtonType( "Socket" ), new ButtonType( "Rmi" ) );
      rmiOrSocket.setHeaderText( null );
      rmiOrSocket.setTitle( "Select connection" );
      rmiOrSocket.setOnCloseRequest( event -> System.exit( 1 ) );
      System.out.println("Modificato");
      Optional<ButtonType> resonse = rmiOrSocket.showAndWait();
      switch (resonse.get().getText().toLowerCase()) {
         case "rmi":
            Registry registry = LocateRegistry.getRegistry(HOST,RMI_PORT);
            loginController = ( RemoteLoginController ) registry.lookup( "LoginController" );
            break;
         case "socket":  default:
            loginController = new SocketLoginController(HOST,SOCKET_PORT);
            break;
      }
      return loginController;
   }
   
   @Override
   public RemotePlayer loginRegister(RemoteLoginController loginController) throws IOException {
      UUID token = null;
      Alert firstTime = new Alert( Alert.AlertType.CONFIRMATION, "is this your first time?", new ButtonType( "Login", ButtonBar.ButtonData.NO ), new ButtonType( "Register", ButtonBar.ButtonData.YES ) );
      switch (firstTime.showAndWait().get().getButtonData()){
         case YES:
            TextInputDialog usernameDialog = new TextInputDialog();
            usernameDialog.setTitle( "Username" );
            usernameDialog.setHeaderText( null );
            usernameDialog.setContentText( "Insert your username" );
            String username = usernameDialog.showAndWait().orElse( "username" );
            token = loginController.register(username);
            System.out.println("Registrazione");
            return loginController.login(token);
         case NO:
            System.out.println("Login");
            return loginController.login(token);
         default:
            System.exit( 1 );
            return null;
      }
   }
}
