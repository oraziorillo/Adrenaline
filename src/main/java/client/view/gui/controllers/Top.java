package client.view.gui.controllers;

import javafx.application.HostServices;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Top {
   @FXML
   public CardHolder cardHolderController;
   @FXML
   public HBox killShotTrack;
   public AnchorPane cardHolder;
   private HostServices hostServices;
   
   public void initialize(){
      double height =100;
      ImageView skull = new ImageView( new Image( "/images/teschio_0.png",0,height,true, false) );
      skull.setPreserveRatio( true );
      killShotTrack.getChildren().add( skull );
      for(int i=1; i<8;i++){
         skull = new ImageView( new Image( "/images/teschio_i.png",0,height,true, false ) );
         skull.setPreserveRatio( true );
         killShotTrack.getChildren().add( skull );
      }
      skull = new ImageView( new Image( "/images/teschio_9.png",0,height,true,false ));
      skull.setPreserveRatio( true );
      killShotTrack.getChildren().add( skull );
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
