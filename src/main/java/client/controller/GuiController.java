package client.controller;

import client.view.AbstractView;
import client.view.gui.PopUpGuiView;
import client.view.gui.javafx_controllers.MainGui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class GuiController extends AbstractClientController {
    
    public GuiController() throws RemoteException {
        super( new PopUpGuiView() );
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader( GuiController.class.getResource( "/fxml/gui.fxml" ));
        Parent root = loader.load();
        MainGui inGameView = loader.getController();
    
        loginController.setRemoteView( inGameView, player.getToken() );
        AbstractView oldView = this.view;
        this.view = inGameView;
        for(String s: oldView.getPendingAcks()){
            this.view.ack( s );
        }
    
        /*TODO: swap views
        
        oldView = null; //Don't know if necessary, is to make oldView garbage-collected
         */
    
        inGameView.setHostServices(getHostServices());
        inGameView.setPlayer(player);
    
        stage.setTitle( "ADRENALINE" );
        stage.setFullScreenExitHint( "Press ESC to exit fullscreen mode" );
        stage.setFullScreenExitKeyCombination(new KeyCodeCombination( KeyCode.ESCAPE ));
        stage.maximizedProperty().addListener( (observableValue, aBoolean, t1) ->  stage.setFullScreen( t1 ) );
        stage.setScene( new Scene( root ) );
        stage.show();
    
    }
}
