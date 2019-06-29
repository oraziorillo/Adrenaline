package client.view.gui.javafx_controllers.components;

import client.view.gui.custom_components.RatioGridPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import common.enums.AmmoEnum;
import javafx.scene.paint.Color;


public class Ammo {
   @FXML
   RatioGridPane grid;
   private static final int MAX_AMMOS_PER_COLOR = 3;
   private static final int INITIAL_AMMOS_PER_COLOR = 1;
   private Region[][] ammos = new Region[AmmoEnum.values().length][MAX_AMMOS_PER_COLOR];
   public void initialize(){
      for(int i=0; i<AmmoEnum.values().length*MAX_AMMOS_PER_COLOR;i++){
         int color = i/MAX_AMMOS_PER_COLOR;
         int index = i%MAX_AMMOS_PER_COLOR;
         Region current = ammos[color][index] = new Region();
         //Button current = ammos[color][index] = new Button();
         current.setBackground( new Background( new BackgroundFill( Color.valueOf( AmmoEnum.values()[color].toString() ) ,null,null) ));
         current.setMaxSize( Double.MAX_VALUE,Double.MAX_VALUE );
         GridPane.setHgrow( current, Priority.ALWAYS );
         GridPane.setVgrow( current, Priority.ALWAYS );
         if(index>INITIAL_AMMOS_PER_COLOR-1){
            current.setDisable( true );
         }
         grid.add( current,index,color );
      }
   }
   
   public void setAmmos(short[] ammos) {
      for(int color=0;color<AmmoEnum.values().length;color++){
         for(int i=0;i<MAX_AMMOS_PER_COLOR;i++){
            this.ammos[color][i].setDisable( i>ammos[color] );
         }
      }
   }
}
