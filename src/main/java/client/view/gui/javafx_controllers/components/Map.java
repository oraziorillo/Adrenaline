package client.view.gui.javafx_controllers.components;

import common.dto_model.PcDTO;
import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.EnumMap;


public class Map {
   @FXML
   GridPane grid;
   private RemotePlayer player;
   
   
   private Pane[][] squares = new Pane[ROWS][COLS];
   private EnumMap<PcColourEnum,Circle> pcCircles = new EnumMap<>( PcColourEnum.class );
   private ImageView[][] ammos = new ImageView[ROWS][COLS];
   //TODO: bindalo con il numero di giocatori -> ricevi subito il numero di giocatori
   private DoubleProperty innerGridSize = new SimpleDoubleProperty();
   
   private static final int ROWS = 3;
   private static final int COLS = 4;
   
   public final MapChangeListener<PcColourEnum,PcDTO> playerObserver = change -> {
      if(change.wasAdded() && change.wasRemoved()){
         PcDTO newPc = change.getValueAdded();
         PcDTO oldPc = change.getValueRemoved();
         Circle circle = pcCircles.get( newPc.getColour() );
         TranslateTransition transition = new TranslateTransition(new Duration( 400 ),circle);
         Pane newSquare = squares[newPc.getCurrSquare().getRow()][newPc.getCurrSquare().getCol()];
         Pane oldSquare = squares[oldPc.getCurrSquare().getRow()][oldPc.getCurrSquare().getCol()];
         circle.setVisible( false );
         Bounds oldBound = circle.localToScene( circle.getBoundsInLocal() );
         oldSquare.getChildren().remove( circle );
         newSquare.getChildren().add( circle );
         Bounds newBounds = circle.localToScene( circle.getBoundsInLocal() );
         double deltaX = newBounds.getCenterX() - oldBound.getCenterX();
         double deltaY = newBounds.getCenterY() - oldBound.getCenterY();
         transition.setFromX(-deltaX);
         transition.setFromY(-deltaY);
         transition.setToX( 0 ); transition.setToY( 0 );
         circle.setVisible( true );
         transition.play();
      }else if(change.wasRemoved()){
         PcDTO removed = change.getValueRemoved();
         Circle circle = pcCircles.get( removed.getColour() );
         Pane square = squares[removed.getCurrSquare().getRow()][removed.getCurrSquare().getCol()];
         square.getChildren().remove( circle );
      }else if(change.wasAdded()){
         int playersNumb = change.getMap().size();
         //size of the implicit grid insie a cell
         innerGridSize.setValue( Math.ceil( Math.sqrt( playersNumb + 1)) );
         PcColourEnum colour = change.getValueAdded().getColour();   //Get changing pc's color
         Pane square = squares[change.getValueAdded().getCurrSquare().getRow()][change.getValueAdded().getCurrSquare().getCol()];   //get new square
         Circle circle = pcCircles.getOrDefault( colour,new Circle(0,Color.valueOf( colour.toString() )) ); //get pc circle
         //bind radius to payers number for every circle
         NumberBinding radius = Bindings.subtract( Bindings.divide(Bindings.min( square.widthProperty(),square.heightProperty()),Bindings.multiply( 2,innerGridSize )),1);
         circle.radiusProperty().bind( radius );
         for(Circle c:pcCircles.values()){
            c.radiusProperty().unbind();
            c.radiusProperty().bind( radius );
         }
         //add the circle to the square
         square.getChildren().add( circle );
         square.setMinSize( 0,0 );
         //update class data structures and show
         pcCircles.put( colour,circle );
         circle.setVisible( true );
      }
   };
   
   
   public final MapChangeListener<SquareDTO,SquareDTO> squareObserver = change -> {
      //TODO: automatizza la gestione degli square nella mappa
   };
   
   public void initialize() {
      //force columns to stay same sized
      for(int c=0;c<COLS;c++){
         ColumnConstraints cc = new ColumnConstraints();
         cc.setPercentWidth( 100/COLS );
         cc.setHgrow( Priority.ALWAYS );
         grid.getColumnConstraints().add( cc );
      }
      for(int r=0;r<ROWS;r++){
         //force rows to stay same sized
         RowConstraints rc = new RowConstraints();
         rc.setPercentHeight( 100/ROWS );
         rc.setVgrow( Priority.ALWAYS );
         grid.getRowConstraints().add( rc );
         //setup cells
         for(int c=0;c<COLS;c++){
            int row=r,column=c;
            
            FlowPane pcsPane = new FlowPane( Orientation.HORIZONTAL );
            pcsPane.setAlignment( Pos.CENTER );
            pcsPane.setMaxSize( Double.MAX_VALUE,Double.MAX_VALUE );
            pcsPane.setOnMouseClicked( e->{
               try {
                  player.chooseSquare( row,column );
               } catch ( IOException ex ) {
                  //TODO: chiama error in MainGui
               }
            } );
   
            StackPane stackPane = new StackPane( pcsPane );
            stackPane.setMaxSize( Double.MAX_VALUE,Double.MAX_VALUE );
            StackPane.setAlignment( pcsPane,Pos.TOP_LEFT );
            //TODO: visualizza ammo
            
            squares[r][c]=pcsPane;
            grid.add( stackPane,c,r);
         }
      }
   }
   
   public void setMap(int mapIndex){
      //load map image
      Image image = new Image( "/images/maps/map_"+mapIndex+".png" );
      BackgroundImage bi = new BackgroundImage( image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize( 1,1,false,false,true,false ) );
      //setup image and map to preserve aspect ratio
      double ratio = image.getWidth()/image.getHeight();
      grid.setBackground( new Background( bi ) );
      grid.maxWidthProperty().bind( Bindings.multiply( ratio,grid.heightProperty() ) );
      grid.minWidthProperty().bind( Bindings.multiply( ratio,grid.heightProperty() ) );
   
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
   
   
}
