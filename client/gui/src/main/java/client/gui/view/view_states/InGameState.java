package client.gui.view.view_states;

import client.gui.controller.GuiController;
import common.dto_model.GameDTO;
import common.events.requests.Request;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import static javafx.application.Platform.runLater;

public class InGameState extends ViewState {
   InGameState() {
      super();
      try {
         FXMLLoader loader = new FXMLLoader( getClass().getResource( "/fxml/inGame/gui.fxml" ) );
         Parent root = loader.load();
         setJavafxController( loader.getController() );
   
         runLater(()->{
            stage.setTitle( "ADRENALINE" );
            stage.setFullScreenExitHint( "Press ESC to exit fullscreen mode" );
            stage.setFullScreenExitKeyCombination( new KeyCodeCombination( KeyCode.ESCAPE ) );
            stage.maximizedProperty().addListener( (observableValue, aBoolean, t1) -> stage.setFullScreen( t1 ) );
            stage.setScene( new Scene( root ) );
            stage.show();
         });
         
      } catch ( IOException e ) {
         IllegalArgumentException e1 = new IllegalArgumentException( "Can't load FXML" );
         e1.setStackTrace( e.getStackTrace() );
         e1.initCause( e );
         throw e1;
      }
   }
   
   @Override
   public void resumeGame(GameDTO game) {
      getJavafxController().resumeGame(game);
   }
   
   @Override
   public ViewState nextState() throws RemoteException {
      return ViewState.getFirstState(hostServices, stage,topView);
   }
   
   @Override
   public void request(Request request) throws RemoteException {
      getJavafxController().request( request );
   }
   
   @Override
   public void winners(List<String> winners) {
      getJavafxController().winners(winners);
      stage.close();
   }
}
