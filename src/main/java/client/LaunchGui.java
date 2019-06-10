package client;

import client.view.cli.controller.AbstractClientController;
import client.view.cli.controller.GuiController;
import client.view.gui.controllers.guiController;
import common.rmi_interfaces.RemoteLoginController;
import common.rmi_interfaces.RemotePlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;

public class LaunchGui extends Application {
    
    private RemoteLoginController loginController;
    private RemotePlayer player;
    private static final boolean COMPLETE = true;
    
    public static void main(String[] args) {
        launch( args );
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException, NotBoundException {
        AbstractClientController clientController = new GuiController();
        if(COMPLETE) {
            loginController = clientController.getLoginController();
            player = clientController.loginRegister( loginController );
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
}
