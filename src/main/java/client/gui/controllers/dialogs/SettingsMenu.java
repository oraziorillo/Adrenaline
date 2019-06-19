package client.gui.controllers.dialogs;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsMenu {
   @FXML
   GridPane mainPane;
   private HostServices hostServices;
   
   @FXML
   public void resumeClicked(){
      Stage stage = ( Stage ) mainPane.getScene().getWindow();
      stage.close();
   }
   
   @FXML
   public void showGameManual() {
      hostServices.showDocument( "docs/adrenaline_rules_en.pdf" );
   }
   
   @FXML
   public void showWeaponManual() {
      hostServices.showDocument("docs/adrenaline_rules_weapons_en.pdf" );
   }
   
   public void setHostServices(HostServices hostServices){
      this.hostServices = hostServices;
   }
}
