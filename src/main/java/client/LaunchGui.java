package client;

import client.socket_proxies.SocketLoginController;
import client.view.gui.controllers.guiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import common.RemoteLoginController;
import common.RemotePlayer;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.UUID;

public class LaunchGui extends Application {
    
    private RemoteLoginController loginController;
    private RemotePlayer player;
    private static final boolean COMPLETE = true;
    public static final String HOST = "localhost";
    public static final int SOCKET_PORT = 10000;
    public static final int RMI_PORT = 9999;
    
    public static void main(String[] args) {
        launch( args );
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException, NotBoundException {
        if(COMPLETE) {
            Alert rmiOrSocket = new Alert( Alert.AlertType.CONFIRMATION, "Do you want to connect with socket?\nI suggest it, cause rmi is just 2 lines of code", new ButtonType( "Socket" ), new ButtonType( "Rmi" ) );
            Optional<ButtonType> resonse = rmiOrSocket.showAndWait();
            switch (resonse.get().getText().toLowerCase()) {
                case "socket":
                    loginController = new SocketLoginController(HOST,SOCKET_PORT);
                    System.out.println( "Usa socket" );
                    break;
                case "rmi":
                    Registry registry = LocateRegistry.getRegistry(HOST,RMI_PORT);
                    loginController = ( RemoteLoginController ) registry.lookup( "LoginController" );
                    System.out.println( "Usa rmi" );
                    break;
                default:
                    System.exit( 1 );
            }
            player = loginRegister();
        }
        FXMLLoader loader = new FXMLLoader( LaunchGui.class.getResource( "/fxml/gui.fxml" ));
        Parent root = loader.load();
        guiController controller = loader.getController();
        System.out.println(getHostServices());
        controller.setHostServices(getHostServices());
        controller.setPlayer(player);
        primaryStage.setTitle( "TITOLO, Orazio pensalo tu" );
        primaryStage.setScene( new Scene( root ) );
        primaryStage.show();
        
    }
    
    private RemotePlayer loginRegister() throws IOException {
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
