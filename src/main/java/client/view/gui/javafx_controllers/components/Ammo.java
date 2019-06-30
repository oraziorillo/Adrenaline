package client.view.gui.javafx_controllers.components;

import common.dto_model.PcDTO;
import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Ammo implements MapChangeListener<PcColourEnum, PcDTO>, ChangeListener<ObjectProperty<PcColourEnum>> {
   @FXML
   GridPane grid;
   private static final int MAX_AMMOS_PER_COLOR = 3;
   private static final int INITIAL_AMMOS_PER_COLOR = 1;
   private Region[][] ammos = new Region[AmmoEnum.values().length][MAX_AMMOS_PER_COLOR];
   private PcColourEnum settedColor;
   
   public void initialize(){
      grid.maxWidthProperty().bind( grid.heightProperty() );
      grid.minWidthProperty().bind( grid.heightProperty() );
      for(int i=0; i<AmmoEnum.values().length*MAX_AMMOS_PER_COLOR;i++){
         int color = i/MAX_AMMOS_PER_COLOR;
         int index = i%MAX_AMMOS_PER_COLOR;
         Region current = ammos[color][index] = new Region();
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
      for(int color=0;color<AmmoEnum.values().length;color++)
         for(int i=0;i<MAX_AMMOS_PER_COLOR;i++)
            this.ammos[color][i].setDisable( i>ammos[color] );
         
      
   }
   
   @Override
   public void onChanged(Change<? extends PcColourEnum, ? extends PcDTO> change) {
      if(change.wasAdded() && change.getKey().equals( settedColor ))
         setAmmos( change.getValueAdded().getPcBoard().getAmmo() );
   }
   
   @Override
   public void changed(ObservableValue<? extends ObjectProperty<PcColourEnum>> observableValue, ObjectProperty<PcColourEnum> pcColourEnumObjectProperty, ObjectProperty<PcColourEnum> t1) {
      this.settedColor = t1.get();
   }
}
