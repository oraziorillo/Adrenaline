package client.view.gui.controllers;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

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
      File manual = new File( getClass().getResource( "/docs/adrenaline_rules_en.pdf" ).getPath() );
      hostServices.showDocument( manual.getAbsolutePath() );
   }
   
   @FXML
   public void showWeaponManual() {
      File manual = new File( getClass().getResource( "/docs/adrenaline_rules_weapons_en.pdf" ).getPath() );
      hostServices.showDocument( manual.getAbsolutePath() );
   }
   
   public void setHostServices(HostServices hostServices){
      this.hostServices = hostServices;
   }
}
