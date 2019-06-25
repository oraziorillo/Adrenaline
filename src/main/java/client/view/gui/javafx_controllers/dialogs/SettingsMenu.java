package client.view.gui.javafx_controllers.dialogs;

import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsMenu {
   @FXML
   GridPane mainPane;
   private HostServices hostServices;
   private RemotePlayer player;
   
   @FXML
   public void resumeClicked(){
      Stage stage = ( Stage ) mainPane.getScene().getWindow();
      stage.close();
   }
   
   @FXML
   private void showGameManual() {
      hostServices.showDocument( "docs/adrenaline_rules_en.pdf" );
   }
   
   @FXML
   private void showWeaponManual() {
      hostServices.showDocument("docs/adrenaline_rules_weapons_en.pdf" );
   }
   
   @FXML
   private void disconnect() {
      try {
         player.quit();
      } catch ( IOException ignored ) { }
   }
   
   public void setHostServices(HostServices hostServices){
      this.hostServices = hostServices;
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
}
