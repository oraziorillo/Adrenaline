package client.view.gui.controllers;

import client.view.gui.custom_components.RatioButton;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static client.view.gui.custom_components.RatioButton.addToLinearGrid;

public class TopController {
   @FXML
   public CardHolder cardHolderController;
   @FXML
   public GridPane killShotTrack;
   private HostServices hostServices;
   
   public void initialize(){
      List<RatioButton> buttons = new ArrayList<>();
      RatioButton current = new RatioButton();
      current.setBackgroundImage( new Image( "/images/teschio_0.png" ),true );
      buttons.add( current);
      for(int i=1; i<8;i++){
         current=new RatioButton();
         current.setBackgroundImage( new Image( "/images/teschio_i.png" ),true );
         buttons.add( current);
      }
      current = new RatioButton();
      current.setBackgroundImage( new Image( "/images/teschio_9.png" ), true);
      buttons.add( current );
      addToLinearGrid( buttons,false,killShotTrack );
   }
   
   
   @FXML
   public void showSettings() throws IOException {
      FXMLLoader loader = new FXMLLoader( getClass().getResource( "/fxml/settingsMenu.fxml" ) );
      Parent parent = loader.load();
      SettingsMenu settingsMenu = loader.getController();
      settingsMenu.setHostServices( hostServices );
      Scene scene = new Scene( parent );
      Stage stage = new Stage();
      stage.initModality( Modality.APPLICATION_MODAL );
      stage.initStyle( StageStyle.UNDECORATED );
      stage.setScene( scene );
      stage.showAndWait();
   }
   
   public void setHostServices(HostServices hostServices) {
      this.hostServices = hostServices;
   }
}
