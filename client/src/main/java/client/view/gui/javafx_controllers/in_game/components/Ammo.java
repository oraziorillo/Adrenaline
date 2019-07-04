package client.view.gui.javafx_controllers.in_game.components;

import client.view.gui.javafx_controllers.in_game.InGameController;
import common.dto_model.PcDTO;
import common.enums.AmmoEnum;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.rmi.RemoteException;


public class Ammo implements MapChangeListener<PcColourEnum, PcDTO>, ChangeListener<ObjectProperty<PcColourEnum>> {
   @FXML GridPane grid;
   private static final int MAX_AMMOS_PER_COLOR = 3;
   private static final int INITIAL_AMMOS_PER_COLOR = 1;
   private Region[][] ammos = new Region[AmmoEnum.values().length][MAX_AMMOS_PER_COLOR];
   private PcColourEnum settedColor;
   private RemotePlayer player;
   
   public void initialize(){
      grid.maxWidthProperty().bind( grid.heightProperty() );
      grid.minWidthProperty().bind( grid.heightProperty() );
      for(int i=0; i<AmmoEnum.values().length*MAX_AMMOS_PER_COLOR;i++){
         int color = i/MAX_AMMOS_PER_COLOR;
         int index = i%MAX_AMMOS_PER_COLOR;
         Region current = ammos[color][index] = new Region();
         current.setOnMouseClicked( e-> {
            selectAmmo(AmmoEnum.values()[color]);
            current.setEffect( InGameController.selectedObjectEffect );
         });
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
   
   private void selectAmmo(AmmoEnum value) {
      try {
         player.chooseAmmo( value.toString() );
      } catch ( RemoteException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   
   public void setAmmos(short[] ammos) {
      for(AmmoEnum color: AmmoEnum.values())
         for(int i=0; i<MAX_AMMOS_PER_COLOR;i++)
            this.ammos[color.ordinal()][i].setOpacity( i<ammos[color.ordinal()]?.6:.05 );
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
   
   public void setColour(PcColourEnum colour) {
      this.settedColor=colour;
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
   
   public void deselectAll() {
      for(Region[] arr:ammos)
         for (Region r:arr)
            r.setEffect( null );
   }
}
