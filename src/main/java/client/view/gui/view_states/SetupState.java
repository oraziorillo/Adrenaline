package client.view.gui.view_states;

import client.view.gui.javafx_controllers.in_game.components.Map;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class SetupState extends ViewState {
   
   SetupState(Stage stage, RemotePlayer player, HostServices hostServices, List<String> previousAcks) throws RemoteException {
      super( stage, player, hostServices, previousAcks);
      try {
         FXMLLoader loader = new FXMLLoader( Map.class.getResource( "/fxml/gameSetup/setup.fxml" ) );
         Parent root = loader.load();
         javafxController = loader.getController();
         javafxController.setPlayer( player );
         javafxController.setHostServices( hostServices );
         for (String ack : previousAcks)
            javafxController.ack( ack );
         stage.setScene( new Scene( root ) );
         stage.show();
      }catch ( IOException e ){
         IllegalArgumentException e1 = new IllegalArgumentException( "Cannot load fxml file" );
         e1.setStackTrace( e.getStackTrace() );
         throw e1;
      }
   }
   
   
   @Override
   public void printMessage(String msg) {
      javafxController.printMessage( msg );
      if("game started".equalsIgnoreCase( msg.trim() ))
         javafxController.setEnabled( true );
   }
   
   @Override
   public ViewState nextState() throws RemoteException {
      return new InGameState(stage,player,hostServices, previousAcks );
   }
   
   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) throws RemoteException {
      try {
         topView.nextState();
      } catch ( RemoteException e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void notifyEvent(LobbyEvent event) throws RemoteException {

   }
}
