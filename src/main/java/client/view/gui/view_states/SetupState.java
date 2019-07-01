package client.view.gui.view_states;

import client.view.gui.javafx_controllers.in_game.components.Map;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SetupState extends ViewState {
   
   SetupState(Stage stage, RemotePlayer player, HostServices hostServices, List<String> previousAcks) throws IOException {
      super( stage, player, hostServices, previousAcks);
      FXMLLoader loader = new FXMLLoader( Map.class.getResource( "/fxml/gameSetup/setup.fxml" ) );
      Parent root = loader.load();
      javafxController = loader.getController();
      javafxController.setPlayer( player );
      javafxController.setHostServices( hostServices );
      for (String ack: previousAcks)
         javafxController.ack( ack );
      stage.setScene( new Scene( root ) );
      stage.show();
   }
   
   @Override
   public ViewState nextState() throws IOException {
      return new InGameState(stage,player,hostServices, previousAcks );
   }
}
