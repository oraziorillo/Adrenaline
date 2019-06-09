package client;


import client.controller.SocketLoginController;
import client.view.gui.controllers.guiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import server.RemoteLoginController;
import server.controller.RemotePlayer;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class GuiClient extends Application {
    
    
    private RemoteLoginController loginController;
    private RemotePlayer player;
    private static final boolean COMPLETE = false;
    
    public static void main(String[] args) {
        launch( GuiClient.class,args );
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        if(COMPLETE) {
            Alert rmiOrSocket = new Alert( Alert.AlertType.CONFIRMATION, "Do you want to connect with socket?\nI suggest it, cause rmi is just 2 lines of code", new ButtonType( "Socket" ), new ButtonType( "Rmi" ) );
            Optional<ButtonType> resonse = rmiOrSocket.showAndWait();
            switch (resonse.get().getText().toLowerCase()) {
                case "socket":
                    loginController = new SocketLoginController();
                    System.out.println( "Usa socket" );
                    break;
                case "rmi":
                    //loginController = TODO: boh... registry.lookup?
                    System.out.println( "Usa rmi" );
                    System.exit( 1 );
                    break;
                default:
                    System.exit( 1 );
            }
            player = loginRegister();
        }
        FXMLLoader loader = new FXMLLoader(GuiClient.class.getResource( "/fxml/gui.fxml" ));
        Parent root = loader.load();
        guiController controller = loader.getController();
        primaryStage.setTitle( "TITOLO, Orazio pensalo tu" );
        primaryStage.setScene( new Scene( root ) );
        primaryStage.show();
        
    }
    
    private RemotePlayer loginRegister() throws IOException {
        UUID token = null;
        Alert firstTime = new Alert( Alert.AlertType.CONFIRMATION, "is this your first time?", new ButtonType( "Login", ButtonBar.ButtonData.NO ), new ButtonType( "Register", ButtonBar.ButtonData.YES ) );
        switch (firstTime.showAndWait().get().getButtonData()){
            case YES:
                String username = "username"; //TODO: chiedi lo username con alert
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
