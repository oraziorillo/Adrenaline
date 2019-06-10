package client.view.gui.controllers;

import client.view.gui.custom_components.RatioGridPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import common.enums.AmmoEnum;


public class AmmoController {
   @FXML
   RatioGridPane grid;
   private static final int MAX_AMMOS_PER_COLOR = 3;
   private static final int INITIAL_AMMOS_PER_COLOR = 1;
   private Button[][] ammos = new Button[AmmoEnum.values().length][MAX_AMMOS_PER_COLOR];
   public void initialize(){
      for(int i=0; i<AmmoEnum.values().length*MAX_AMMOS_PER_COLOR;i++){
         int color = i/MAX_AMMOS_PER_COLOR;
         int index = i%MAX_AMMOS_PER_COLOR;
         Button current = ammos[color][index] = new Button();
         current.setStyle( "-fx-background-color: "+AmmoEnum.values()[color] );
         current.setMaxHeight( Double.MAX_VALUE );
         current.setMaxWidth( Double.MAX_VALUE );
         GridPane.setHgrow( current, Priority.ALWAYS );
         GridPane.setVgrow( current, Priority.ALWAYS );
         if(index>INITIAL_AMMOS_PER_COLOR-1){
            current.setVisible( false );
         }
         grid.add( current,index,color );
      }
   }
}
