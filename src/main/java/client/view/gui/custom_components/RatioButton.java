package client.view.gui.custom_components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.List;

public class RatioButton extends Button {
   public DoubleProperty ratio = new SimpleDoubleProperty( 1 );
   
   public RatioButton(){
      setMaxSize( Double.MAX_VALUE,Double.MAX_VALUE );
   }
   
   public RatioButton(double ratio){
      setRatio( ratio );
   }
   
   @Override
   public void resize(double width, double height) {
      double ratio = this.ratio.get();
      if(calcHeight( width ) > height){
         super.resize( calcWidth( height ),height );
      }else {
         super.resize( width, calcHeight( width ) );
      }
   }
   
   @Override
   public void resizeRelocate(double x, double y, double width, double height) {
      double ratio = this.ratio.get();
      if(calcHeight( width ) > height){
         super.resizeRelocate(x,y, calcWidth( height ),height );
      }else {
         super.resizeRelocate(x,y, width, calcHeight( width ) );
      }
   }
   
   public void setBackgroundImage(Image image, boolean copyRatio){
      BackgroundImage bi = new BackgroundImage( image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize( 1,1,false,false,true,false ) );
      setBackground( new Background( bi ) );
      if(copyRatio) setRatio( image.getWidth(),image.getHeight() );
   }
   
   public void setRatio(double ratio) {
      this.ratio.set( ratio );
   }
   
   public void setRatio(double width, double height){
      this.ratio.set( height/width );
   }
   
   public double getRatio() {
      return ratio.get();
   }
   
   private double calcWidth(double height){
      return height/ratio.get();
   }
   
   private double calcHeight(double width){
      return width*ratio.get();
   }
   
   public static void addToLinearGrid(List<? extends Button> buttonList, boolean vertical, GridPane pane){
      for(int i=0; i<buttonList.size();i++){
         Button current = buttonList.get( i );
         GridPane.setHgrow( current,Priority.ALWAYS );
         GridPane.setVgrow( current,Priority.ALWAYS );
         if(vertical){
            pane.add( current,0,i );
         }else {
            pane.add( current,i,0 );
         }
      }
   }
}
