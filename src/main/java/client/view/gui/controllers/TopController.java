package client.view.gui.controllers;

import client.view.gui.custom_components.RatioButton;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

import static client.view.gui.custom_components.RatioButton.addToLinearGrid;

public class TopController {
   @FXML
   public CardHolder cardHolderController;
   @FXML
   public GridPane killShotTrack;
   
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
}
