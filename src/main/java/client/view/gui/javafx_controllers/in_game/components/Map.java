package client.view.gui.javafx_controllers.in_game.components;

import client.view.gui.javafx_controllers.in_game.components.pc_board.OpponentBoard;
import common.dto_model.AmmoTileDTO;
import common.dto_model.PcDTO;
import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.EnumMap;


public class Map implements MapChangeListener<SquareDTO,SquareDTO>{
   @FXML
   GridPane grid;
   private RemotePlayer player;
   
   private Pane[][] squares = new Pane[ROWS][COLS];
   private StackPane[][] stackPanes = new StackPane[ROWS][COLS];
   
   private EnumMap<PcColourEnum,Circle> pcCircles = new EnumMap<>( PcColourEnum.class );
   private EnumMap<PcColourEnum,VBox> opponentBoardGraphics = new EnumMap<>( PcColourEnum.class );
   private EnumMap<PcColourEnum,OpponentBoard> opponentBoardControllers = new EnumMap<>(PcColourEnum.class);
   private ImageView[][] ammos = new ImageView[ROWS][COLS];
   private DoubleProperty innerGridSize = new SimpleDoubleProperty();
   
   private static final int ROWS = 3;
   private static final int COLS = 4;
   
   public final MapChangeListener<PcColourEnum,PcDTO> playerObserver = change -> {
      if(change.wasAdded() && change.wasRemoved()){
         PcDTO newPc = change.getValueAdded();
         PcDTO oldPc = change.getValueRemoved();
         Circle circle = pcCircles.get( newPc.getColour() );
         TranslateTransition transition = new TranslateTransition(new Duration( 400 ),circle);
         Pane newSquare = squares[newPc.getSquareRow()][newPc.getSquareCol()];
         Pane oldSquare = squares[oldPc.getSquareRow()][oldPc.getSquareCol()];
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
      }else if(change.wasAdded()){
         int playersNumb = change.getMap().size();
         //size of the implicit grid insie a cell
         innerGridSize.setValue( Math.ceil( Math.sqrt( playersNumb + 1)) );
         PcColourEnum colour = change.getValueAdded().getColour();   //Get changing pc's color
         Pane square = squares[change.getValueAdded().getSquareRow()][change.getValueAdded().getSquareCol()];   //get new square
         Circle circle = pcCircles.getOrDefault( colour,new Circle(0,Color.valueOf( colour.toString() )) ); //get pc circle
         pcCircles.put( colour,circle );
         //bind radius to payers number for every circle
         NumberBinding radius = Bindings.subtract( Bindings.divide(Bindings.min( square.widthProperty(),square.heightProperty()),Bindings.multiply( 2,innerGridSize )),1);
         for(ImageView[] array:ammos){
            for(ImageView ammo: array){
               ammo.fitHeightProperty().unbind();
               ammo.fitHeightProperty().bind( radius );
            }
         }
   
         OpponentBoard created = loadNewBoard(colour);
         change.getMap().addListener( created );
         for(OpponentBoard c:opponentBoardControllers.values()){
            c.onChanged( change );
         }
         created.heightProperty().bind( Bindings.multiply( circle.radiusProperty(),2) );
   
         //add the circle to the square
         square.getChildren().add( circle );
         square.setMinSize( 0,0 );
   
         //update class data structures and show
         for(Circle c:pcCircles.values()){
            c.radiusProperty().unbind();
            c.radiusProperty().bind( radius );
         }
         circle.setVisible( true );
      }else{
         throw new IllegalArgumentException( "Squares shouldn't be removed" );
      }
   };
   
   public final MapChangeListener<SquareDTO,SquareDTO> squareObserver = change -> {
      if(change.wasAdded()&&!change.wasRemoved()){
         throw new IllegalStateException( "Squares shouldn't be just removed, they should be modified or added" );
      }
      if(change.wasAdded()){
         SquareDTO newSquare = change.getValueAdded();
         ImageView currAmmo = ammos[newSquare.getRow()][newSquare.getCol()];
         if(change.getValueAdded().getAmmoTile()!=null) {
            AmmoTileDTO ammoTileDTO = change.getValueAdded().getAmmoTile();
            currAmmo.setImage( new Image( ammoTileDTO.getImagePath() ));
         }else{
            currAmmo.setImage( new Image( AmmoTileDTO.getEmptyPath() ) );
         }
      }
   };
   
   @Override
   public void onChanged(Change<? extends SquareDTO, ? extends SquareDTO> change) {
      if(change.wasRemoved()&&!change.wasAdded()){
         throw new IllegalStateException( "Squares shouldn't be just removed, they should be modified or added" );
      }
      if(change.wasAdded()){
         updateAmmos( change.getValueAdded() );
         
      }
   }
   
   private void updateAmmos(SquareDTO newSquare){
      ImageView currAmmo = ammos[newSquare.getRow()][newSquare.getCol()];
      if (newSquare.getAmmoTile() == null) {
         currAmmo.setImage( new Image( AmmoTileDTO.getEmptyPath() ) );
      } else {
         AmmoTileDTO ammoTileDTO = newSquare.getAmmoTile();
         currAmmo.setImage( new Image( ammoTileDTO.getImagePath() ));
      }
   }
   
   
   public void initialize() {
      //force columns to stay same sized
      for(int c=0;c<COLS;c++){
         ColumnConstraints cc = new ColumnConstraints();
         cc.setPercentWidth( 100/COLS );
         cc.setHalignment( HPos.CENTER );
         cc.setHgrow( Priority.ALWAYS );
         grid.getColumnConstraints().add( cc );
      }
      for(int r=0;r<ROWS;r++){
         //force rows to stay same sized
         RowConstraints rc = new RowConstraints();
         rc.setPercentHeight( 100/ROWS );
         rc.setVgrow( Priority.ALWAYS );
         rc.setValignment( VPos.CENTER );
         grid.getRowConstraints().add( rc );
         //setup cells
         for(int c=0;c<COLS;c++){
            int row=r,column=c;
            FlowPane pcsPane = new FlowPane( Orientation.HORIZONTAL );
            pcsPane.setAlignment( Pos.CENTER );
            pcsPane.setMaxSize( Double.MAX_VALUE,Double.MAX_VALUE );
            pcsPane.setOnMouseClicked( e-> squareClicked( row,column ));
            
            ImageView ammoTile = new ImageView();
            ammoTile.setPreserveRatio( true );
            ammos[r][c] = ammoTile;
   
            StackPane stackPane = new StackPane( pcsPane,ammoTile );
            stackPane.maxWidthProperty().bind( Bindings.divide( grid.widthProperty(),COLS ) );
            stackPane.maxHeightProperty().bind( Bindings.divide( grid.heightProperty(),ROWS ) );
            StackPane.setAlignment( pcsPane,Pos.TOP_LEFT );
            StackPane.setAlignment( ammoTile,Pos.BOTTOM_RIGHT );
            
            squares[r][c]=pcsPane;
            stackPanes[r][c]=stackPane;
            
            grid.add( stackPane,c,r);
         }
      }
   }
   
   private void squareClicked(int row,int col){
      try {
         player.chooseSquare( row,col );
      } catch ( IOException ex ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),ex );
      }
   }
   
   private OpponentBoard loadNewBoard(PcColourEnum color){
      FXMLLoader boardLoader = new FXMLLoader(Map.class.getResource( "/fxml/inGame/pc_board/opponentBoard.fxml" ));
      VBox board;
      OpponentBoard boardController;
      ScaleTransition scale;
      try {
         board = boardLoader.load();
      } catch ( IOException e ) {
         IllegalArgumentException iae = new IllegalArgumentException( "Can't load graphic resources" );
         iae.setStackTrace( e.getStackTrace() );
         throw iae;
      }
      scale = new ScaleTransition( new Duration( 200 ),board );
      boardController = boardLoader.getController();
      boardController.setColor( color );
      pcCircles.get( color ).setOnMouseEntered( e->showBoard( color,scale ) );
      board.setOnMouseExited( e->hideBoard( color,scale ) );
      board.toFront();
      scale.setFromX( 0 ); scale.setFromY( 0 );
      scale.setToX( 1 ); scale.setToY( 1 );
      board.setScaleX( 0 ); board.setScaleY( 0 );
      opponentBoardGraphics.put( color,board );
      opponentBoardControllers.put( color,boardController );
      return boardController;
   }
   
   private synchronized void showBoard(PcColourEnum color,ScaleTransition scale){
      if(!scale.getStatus().equals( Animation.Status.RUNNING )) {
         VBox board;
         Circle circle = pcCircles.get( color );
         board = opponentBoardGraphics.get( color );
         grid.add( board, GridPane.getColumnIndex( circle.getParent().getParent() ), GridPane.getRowIndex( circle.getParent().getParent() ) );
         scale.stop();
         scale.setRate( 1 );
         scale.play();
      }
   }
   
   private synchronized void hideBoard(PcColourEnum color,ScaleTransition scale){
      scale.stop();
      scale.setRate( -1 );
      scale.setOnFinished( e -> {
         scale.setOnFinished( null );
         grid.getChildren().remove( opponentBoardGraphics.get( color ) );
      } );
      scale.play();
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