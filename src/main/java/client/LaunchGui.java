package client;

import client.controller.AbstractClientController;
import client.controller.GuiController;
import client.view.gui.controllers.weapons.GeneralWeapon;
import common.model_dtos.WeaponCardDTO;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.rmi.NotBoundException;

public class LaunchGui extends Application {
    
    private RemoteLoginController loginController;
    private RemotePlayer player;
    private static final boolean COMPLETE = false;
    
    public static void main(String[] args) {
        launch( args );
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException, NotBoundException {
        AbstractClientController clientController = new GuiController();
        if(COMPLETE) {
            loginController = clientController.getLoginController();
           try {
              player = clientController.loginRegister( loginController );
           } catch (PlayerAlreadyLoggedInException e) {
              e.printStackTrace();
           }
        }
        FXMLLoader loader = new FXMLLoader( LaunchGui.class.getResource( "/fxml/weapons/general_weapon.fxml" ));
        Parent root = loader.load();
        GeneralWeapon controller = loader.getController();
        controller.setWeapon( new WeaponCardDTO("mitragliatrice",1,2) );
        /*GuiController controller = loader.getController();
        System.out.println(getHostServices());
        controller.setHostServices(getHostServices());
        controller.setPlayer(player);*/
        primaryStage.setTitle( "TITOLO, Orazio pensalo tu" );
        primaryStage.setFullScreenExitHint( "Press ESC to exit fullscreen mode" );
        primaryStage.setFullScreenExitKeyCombination(new KeyCodeCombination( KeyCode.ESCAPE ));
        primaryStage.maximizedProperty().addListener( (observableValue, aBoolean, t1) ->  primaryStage.setFullScreen( t1 ) );
        primaryStage.setScene( new Scene( root ) );
        primaryStage.show();
        
    }
}
