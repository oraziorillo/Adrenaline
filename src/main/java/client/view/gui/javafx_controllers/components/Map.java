package client.view.gui.javafx_controllers.components;

import client.view.gui.custom_components.RatioGridPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class Map {
   @FXML
   RatioGridPane grid;
   @FXML Button button;
   
   private Button[][] squares = new Button[3][4];
   public void initialize(){
      for(int r=0;r<3;r++){
         for(int c=0;c<4;c++){
            Button current = new Button();
            GridPane.setVgrow( current,Priority.ALWAYS );
            GridPane.setHgrow( current,Priority.ALWAYS );
            current.setMaxWidth( Double.MAX_VALUE );
            current.setMaxHeight( Double.MAX_VALUE );
            current.setBackground( null );
            squares[r][c]=current;
            grid.add( current,c,r);
         }
      }
      setMap( 0 );
   }
   
   public void setMap(int mapIndex){
      Image image = new Image( "/images/map_"+mapIndex+".png" );
      BackgroundImage bi = new BackgroundImage( image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize( 1,1,false,false,true,false ) );
      grid.setBackground( new Background( bi ) );
      grid.ratio.set( image.getHeight()/image.getWidth() );
   }
   
}
