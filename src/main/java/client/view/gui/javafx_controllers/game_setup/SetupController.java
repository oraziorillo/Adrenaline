package client.view.gui.javafx_controllers.game_setup;

import client.view.gui.ImageCache;
import client.view.gui.javafx_controllers.AbstractJavaFxController;
import client.view.gui.javafx_controllers.in_game.components.Chat;
import client.view.gui.javafx_controllers.in_game.components.Top;
import client.view.gui.javafx_controllers.in_game.components.pc_board.PcBoard;
import common.Constants;
import common.enums.PcColourEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_events.PcEvent;
import common.events.lobby_events.LobbyEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.EnumMap;
import java.util.HashSet;

import static javafx.application.Platform.runLater;

public class SetupController extends AbstractJavaFxController {
   
   @FXML private transient GridPane mainPane;
   @FXML private transient HBox skullTrack;
   @FXML private transient TilePane maps;
   @FXML private transient HBox pcs;
   @FXML private transient Chat chatController;
   private Circle[] circles = new Circle[Constants.MAX_KILL_SHOT_TRACK_SIZE];
   private int selectedSkulls = Constants.MIN_KILL_SHOT_TRACK_SIZE;
   private EnumMap<PcColourEnum,ImageView> pcViews = new EnumMap<>(PcColourEnum.class );
   private HashSet<StackPane> reactiveSkulls = new HashSet<>();
   
   
   
   public SetupController() throws RemoteException {
   }
   
   
   public void initialize(){
      GridPane.setValignment( maps,VPos.CENTER );
      GridPane.setValignment( pcs, VPos.BOTTOM );
      //init skulls track
      
      ImageView skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_0.png", Top.KILLSHOT_HEIGHT ));
      Circle circle = circles[0] = new Circle( skullImage.getImage().getWidth()/2, Color.RED );
      StackPane stackPane = new StackPane( skullImage,circle );
      stackPane.maxWidthProperty().bind( skullImage.getImage().widthProperty() );
      circle.setStroke( Color.BLACK );
      circle.setOpacity( .5 );
      skullImage.setPreserveRatio( true );
      skullTrack.getChildren().add( new StackPane( skullImage, circle) );
      reactiveSkulls.add( stackPane );
      for(int i=1; i<Constants.MAX_KILL_SHOT_TRACK_SIZE-1;i++){
         int skullIndex = i+1;
         skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_i.png", Top.KILLSHOT_HEIGHT ));
         circle = circles[i] = new Circle( skullImage.getImage().getWidth()/2,Color.RED );
         stackPane = new StackPane( skullImage,circle );
         stackPane.maxWidthProperty().bind( skullImage.getImage().widthProperty() );
         circle.setStroke( Color.BLACK );
         circle.setOpacity( .5 );
         if(i>=Constants.MIN_KILL_SHOT_TRACK_SIZE) {
            stackPane.setOnMouseClicked( e -> chooseSkulls( skullIndex ) );   //skullindex refers to array inded, choose skull refers to skull number
            stackPane.setOnMouseEntered( e->showCirclesBeforeIndex( skullIndex-1 ) );
            reactiveSkulls.add( stackPane );
            circle.setVisible( false );
         }
         skullImage.setPreserveRatio( true );
         skullTrack.getChildren().add( stackPane );
      }
      skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_ultimo.png", Top.KILLSHOT_HEIGHT ));
      skullImage.setPreserveRatio( true );
      circle = circles[Constants.MAX_KILL_SHOT_TRACK_SIZE-1] = new Circle( skullImage.getImage().getWidth()/2,Color.RED );
      circle.setStroke( Color.BLACK );
      circle.setOpacity( .5 );
      circle.setVisible( false );
      stackPane = new StackPane( skullImage,circle );
      stackPane.maxWidthProperty().bind( skullImage.getImage().widthProperty() );
      stackPane.setOnMouseClicked( e -> chooseSkulls( Constants.MAX_KILL_SHOT_TRACK_SIZE ) );
      stackPane.setOnMouseEntered( e->showCirclesBeforeIndex( Constants.MAX_KILL_SHOT_TRACK_SIZE ) );
      reactiveSkulls.add( stackPane );
   
      skullTrack.getChildren().add( new StackPane( skullImage, circle) );
      skullTrack.setOnMouseExited( e->showCirclesBeforeIndex( selectedSkulls ) );
      
      //init maps selection
      int sqrtMaps = ( int ) Math.ceil( Math.sqrt( Constants.LAST_MAP ) );
      maps.setPrefRows( sqrtMaps );
      maps.setPrefColumns( sqrtMaps );
      maps.setTileAlignment( Pos.CENTER );
      String prefix = "/images/maps/map_";
      String postfix = ".png";
      for(int i=Constants.FIRST_MAP;i<=Constants.LAST_MAP;i++){
         int forLambda = i;
         ImageView mapImage = new ImageView( ImageCache.loadImage( prefix+i+postfix, 0) );
         mapImage.fitHeightProperty().bind(Bindings.divide(mainPane.heightProperty(),2*sqrtMaps) );
         mapImage.setPreserveRatio( true );
         mapImage.setOnMouseClicked( e -> chooseMap( forLambda ) );
         maps.getChildren().add( mapImage );
      }
      
      //init Pc
      prefix  = "/images/pc_board/";
      postfix = "/immagine.png";
      for(PcColourEnum colour: PcColourEnum.values()){
         ImageView colorImage = new ImageView( ImageCache.loadImage( prefix+colour.getName().toLowerCase()+postfix, PcBoard.HEIGHT ) );
         colorImage.setOnMouseClicked( e -> choosePcColor( colour ) );
         colorImage.setPreserveRatio( true );
         pcs.getChildren().add( colorImage );
         pcViews.put( colour,colorImage );
      }
      setEnabled( false );
   }
   
   private void chooseSkulls(int n){
      try {
         selectedSkulls = n;
         player.chooseNumberOfSkulls( n );
         player.ok();
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   private void chooseMap(int n){
      try {
         player.chooseMap( n );
         player.ok();
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   private void choosePcColor(PcColourEnum colour){
      try {
         player.choosePcColour( colour.toString() );
         player.ok();
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   private void showCirclesBeforeIndex(int index){
      for(int i=0;i<circles.length;i++){
         circles[i].setVisible( i<index );
      }
   }
   
   @Override
   public void printMessage(String msg) {
      chatController.showServerMessage( msg );
   }
   
   @Override
   public void ack(String message) {
      chatController.showServerMessage( message );
   }
   
   @Override
   public void chatMessage(String message) {
      chatController.showUserMessage( message );
   }
   
   @Override
   public ModelEventListener getListener() {
      return this;
   }
   
   @Override
   public void setPlayer(RemotePlayer player) {
      super.setPlayer( player );
      chatController.setPlayer( player );
   }
   
   private void setEnabled(boolean enabled){
      mainPane.setDisable( !enabled );
      mainPane.setOpacity( enabled?1:.5 );
   }
   
   private void disableSkullsSelection(){
      skullTrack.setEffect( null );
      for(StackPane pane: reactiveSkulls){
         pane.setOnMouseEntered( null );
         pane.setOnMouseClicked( null );
      }
      skullTrack.setOnMouseExited( null );
   }
   
   private void disableMapSelection(){
      maps.setEffect( null );
      maps.setOnMouseClicked( null );
      for(Node n:maps.getChildren())
         n.setOnMouseClicked( null );
   }
   
   @Override
   public void onPcUpdate(PcEvent event) {
      ImageView pcView = pcViews.get( event.getDTO().getColour() );
      pcView.setDisable( true );
      pcView.setOpacity( .4 );
      pcView.setEffect( null );
   }
   
   @Override
   public void onKillShotTrackUpdate(KillShotTrackEvent event) {
      int l=event.getDTO().getKillShotTrack().length;
      disableSkullsSelection(  );
      showCirclesBeforeIndex( l );
   }
   
   @Override
   public void onGameBoardUpdate(GameBoardEvent event) {
      disableMapSelection();
      runLater(()-> {
         maps.setTileAlignment( Pos.CENTER );
         maps.setHgap( 0 );
         maps.setVgap( 0 );
         int mapIndex = event.getDTO().getNumberOfMap()-1;
         ImageView toKeep = ( ImageView ) maps.getChildren().remove( mapIndex );
         ScaleTransition scale = new ScaleTransition( new Duration( 500 ), toKeep );
         scale.setByX( 1 );
         scale.setByY( 1 );
         scale.setOnFinished( e -> {
            maps.getChildren().clear();
            maps.getChildren().add( toKeep );
            toKeep.setTranslateX( 0 );
            toKeep.setTranslateY( 0 );
         } );
         toKeep.setTranslateX( toKeep.getImage().getWidth() / 2 );
         toKeep.setTranslateY( toKeep.getImage().getHeight() / 2 );
         scale.play();
         //TODO: da testare
      });
   }

   @Override
   public void notifyEvent(LobbyEvent event) {

   }
   
   /**
    * Listens the number of players before my turn
    * @param obs the property
    * @param oldV initially -inf
    * @param newV new number of players before my turn (after set can only decrement)
    */
   @Override
   public void changed(ObservableValue<? extends Number> obs, Number oldV, Number newV) {
      if(oldV.equals( Double.NEGATIVE_INFINITY )) {
         if(!(newV.doubleValue()==0)){
            disableSkullsSelection();
            disableMapSelection();
         }
      }else if(newV.doubleValue() >= 3 /*MIN_PLAYERS*/){
         setEnabled( true );
      }
   }
}
