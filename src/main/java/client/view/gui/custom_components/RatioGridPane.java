package client.view.gui.custom_components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.GridPane;

public class RatioGridPane extends GridPane {
   public DoubleProperty ratio = new SimpleDoubleProperty( 1 );
   
   public RatioGridPane(){
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
}
