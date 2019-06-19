package client.gui;

import client.AbstractClientController;
import client.gui.controllers.MainGui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

public class GuiController extends AbstractClientController {
    
    public GuiController() {
        super( new PopUpGuiView() );
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader( GuiController.class.getResource( "/fxml/gui.fxml" ));
        Parent root = loader.load();
        MainGui inGameView = loader.getController();
        
        this.view = inGameView;
    
        inGameView.setHostServices(getHostServices());
        inGameView.setPlayer(player);
    
        stage.setTitle( "TITOLO, Orazio pensalo tu" );
        stage.setFullScreenExitHint( "Press ESC to exit fullscreen mode" );
        stage.setFullScreenExitKeyCombination(new KeyCodeCombination( KeyCode.ESCAPE ));
        stage.maximizedProperty().addListener( (observableValue, aBoolean, t1) ->  stage.setFullScreen( t1 ) );
        stage.setScene( new Scene( root ) );
        stage.show();
    
    }
}
