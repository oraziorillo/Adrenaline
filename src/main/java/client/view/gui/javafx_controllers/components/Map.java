package client.view.gui.javafx_controllers.components;

import client.view.gui.custom_components.RatioGridPane;
import common.remote_interfaces.RemotePlayer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;


public class Map {
   @FXML
   RatioGridPane grid;
   @FXML Button button;
   private RemotePlayer player;
   
   private Button[][] squares = new Button[3][4];
   public void initialize() {
      for(int r=0;r<3;r++){
         for(int c=0;c<4;c++){
            int row=r,column=c;
            Button current = new Button();
            GridPane.setVgrow( current,Priority.ALWAYS );
            GridPane.setHgrow( current,Priority.ALWAYS );
            current.setMaxWidth( Double.MAX_VALUE );
            current.setMaxHeight( Double.MAX_VALUE );
            current.setBackground( null );
            current.setOnAction( e-> {
               try {
                  player.chooseSquare( row,column );
               } catch ( IOException ex ) {
                  //TODO: chiama error in MainGui
               }
            } );
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
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
}
