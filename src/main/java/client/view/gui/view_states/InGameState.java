package client.view.gui.view_states;

import client.controller.GuiController;
import common.events.lobby_events.LobbyEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class InGameState extends ViewState {
   public InGameState(Stage stage, RemotePlayer player, HostServices hostServices, List<String> previousAcks) throws RemoteException {
      super( stage, player, hostServices, previousAcks );
      try {
         FXMLLoader loader = new FXMLLoader( GuiController.class.getResource( "/fxml/inGame/gui.fxml" ) );
         Parent root = loader.load();
         javafxController = loader.getController();
         javafxController.setPlayer( player );
         javafxController.setHostServices( hostServices );
   
         stage.setTitle( "ADRENALINE" );
         stage.setFullScreenExitHint( "Press ESC to exit fullscreen mode" );
         stage.setFullScreenExitKeyCombination( new KeyCodeCombination( KeyCode.ESCAPE ) );
         stage.maximizedProperty().addListener( (observableValue, aBoolean, t1) -> stage.setFullScreen( t1 ) );
         stage.setScene( new Scene( root ) );
         stage.show();
      } catch ( IOException e ) {
         IllegalArgumentException e1 = new IllegalArgumentException( "Can't load FXML" );
         e1.setStackTrace( e.getStackTrace() );
         throw e1;
      }
   }
   
   @Override
   public ViewState nextState() throws RemoteException {
      return ViewState.getFirstState(hostServices, stage,previousAcks,topView);
   }

   @Override
   public void notifyEvent(LobbyEvent event) throws RemoteException {

   }
}
