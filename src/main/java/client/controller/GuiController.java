package client.controller;

import client.view.AbstractView;
import client.view.gui.GuiExceptionHandler;
import client.view.gui.GuiView;
import client.view.gui.javafx_controllers.view_states.InGameState;
import client.view.gui.javafx_controllers.view_states.ViewState;
import common.enums.PcColourEnum;
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
import java.rmi.RemoteException;
import java.util.UUID;

public class GuiController extends Application {
   
   RemoteLoginController loginController;
   protected AbstractView view = new GuiView();
   protected RemotePlayer player;
   
   public GuiController() throws RemoteException {
    
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler( new GuiExceptionHandler(player) );
        authUser( stage );
        configGame( stage );
        startGame( stage );
    }
    
    private void authUser(Stage stage){
        try {
            this.loginController = view.acquireConnection(view.acquireConnectionMethod());
            UUID token;
            if (view.wantsToRegister()) {
                String username = view.acquireUsername();
                token = loginController.register( username, view );
            } else {
                token = view.acquireToken();
            }
            player = loginController.login( token, view );
        }catch ( IOException e ){
            try {
                view.error( "Server unreachable" );
            }catch ( RemoteException ignored ){}
        }catch ( PlayerAlreadyLoggedInException alreadyLogged ){
           try {
              view.error( "This player is already connected on a different machine" );
           } catch ( RemoteException ignored ) {}
        }
    }
    
    private void configGame(Stage stage){
      
        //TODO: scegli mappa, teschi, colore
    }
    
    private void startGame(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader( GuiController.class.getResource( "/fxml/inGame/gui.fxml" ));
        Parent root = loader.load();

        ((GuiView)view).setHostServices(getHostServices());
        ((GuiView)view).setPlayer(player);
    
        stage.setTitle( "ADRENALINE" );
        stage.setFullScreenExitHint( "Press ESC to exit fullscreen mode" );
        stage.setFullScreenExitKeyCombination(new KeyCodeCombination( KeyCode.ESCAPE ));
        stage.maximizedProperty().addListener( (observableValue, aBoolean, t1) ->  stage.setFullScreen( t1 ) );
        stage.setScene( new Scene( root ) );
        stage.show();
    }
}
