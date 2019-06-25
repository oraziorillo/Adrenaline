package client.view.gui.javafx_controllers.components;

import common.dto_model.PcDTO;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.beans.binding.Bindings;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.HashMap;


public class Map implements MapChangeListener<PcColourEnum,PcDTO> {
   @FXML
   GridPane grid;
   @FXML Button button;
   private RemotePlayer player;
   
   private Pane[][] squares = new Pane[3][4];
   private HashMap<PcColourEnum,Circle> pcCircles = new HashMap<>();
   
   public void initialize() {
      for(int r=0;r<3;r++){
         for(int c=0;c<4;c++){
            int row=r,column=c;
            Button button = new Button();
            GridPane.setVgrow( button,Priority.ALWAYS ); GridPane.setHgrow( button,Priority.ALWAYS );
            button.setMaxSize( Double.MAX_VALUE,Double.MAX_VALUE );
            button.setBackground( null );
            button.setOnAction( e-> {
               try {
                  player.chooseSquare( row,column );
               } catch ( IOException ex ) {
                  //TODO: chiama error in MainGui
               }
            } );
            
            FlowPane circles = new FlowPane(  );
            circles.setAlignment( Pos.CENTER );
            circles.setMaxSize( Double.MAX_VALUE,Double.MAX_VALUE );
   
            StackPane stackPane = new StackPane( circles,button );
            stackPane.setMaxSize( Double.MAX_VALUE,Double.MAX_VALUE );
            GridPane.setVgrow( stackPane,Priority.ALWAYS ); GridPane.setHgrow( stackPane,Priority.ALWAYS );
            
            squares[r][c]=circles;
            grid.add( stackPane,c,r);
            grid.setGridLinesVisible( true );
         }
      }
   }
   
   public void setMap(int mapIndex){
      Image image = new Image( "/images/map_"+mapIndex+".png" );
      BackgroundImage bi = new BackgroundImage( image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize( 1,1,false,false,true,false ) );
      double ratio = image.getWidth()/image.getHeight();
      grid.setBackground( new Background( bi ) );
      grid.maxWidthProperty().bind( Bindings.multiply( ratio,grid.heightProperty() ) );
      grid.minWidthProperty().bind( Bindings.multiply( ratio,grid.heightProperty() ) );
   
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
   
   @Override
   public void onChanged(Change<? extends PcColourEnum, ? extends PcDTO> change) {
      if(change.wasRemoved()){
         pcCircles.get( change.getValueRemoved()
                 .getColour() ).setVisible( false );
      }
      if(change.wasAdded()){
         int playersNumb = change.getMap().size();
         double gridSide = Math.ceil( Math.sqrt( playersNumb + 1));
         PcColourEnum colour = change.getValueAdded().getColour();
         Pane square = squares[change.getValueAdded().getCurrSquare().getRow()][change.getValueAdded().getCurrSquare().getCol()];
         Circle circle = pcCircles.getOrDefault( colour,new Circle() );
         circle.setFill( Color.valueOf( colour.toString() ) );
         circle.radiusProperty().bind( Bindings.subtract( Bindings.divide(Bindings.min( square.widthProperty(),square.heightProperty()),2*gridSide),1));
         square.getChildren().add( circle );
         pcCircles.put( colour,circle );
         circle.setVisible( true );
      }
   }
   
}
