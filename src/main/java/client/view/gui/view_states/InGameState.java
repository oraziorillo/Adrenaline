package client.view.gui.view_states;

import client.controller.GuiController;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class InGameState extends ViewState {
   public InGameState(Stage stage, RemotePlayer player, HostServices hostServices, ObservableList<String> previousAcks) throws IOException {
      super( stage, player, hostServices, previousAcks );
      FXMLLoader loader = new FXMLLoader( GuiController.class.getResource( "/fxml/inGame/gui.fxml" ));
     Parent root = loader.load();
      javafxController = loader.getController();
      javafxController.setPlayer( player );
      javafxController.setHostServices( hostServices );
   
      stage.setTitle( "ADRENALINE" );
      stage.setFullScreenExitHint( "Press ESC to exit fullscreen mode" );
      stage.setFullScreenExitKeyCombination(new KeyCodeCombination( KeyCode.ESCAPE ));
      stage.maximizedProperty().addListener( (observableValue, aBoolean, t1) ->  stage.setFullScreen( t1 ) );
      stage.setScene( new Scene( root ) );
      stage.show();
   }
   
   @Override
   public ViewState nextState() throws RemoteException {
      return ViewState.getFirstState(hostServices, stage,previousAcks);
   }
}
