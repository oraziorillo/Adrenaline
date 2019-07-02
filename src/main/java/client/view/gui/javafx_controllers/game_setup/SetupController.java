package client.view.gui.javafx_controllers.game_setup;

import client.view.gui.ImageCache;
import client.view.gui.javafx_controllers.AbstractJavaFxController;
import client.view.gui.javafx_controllers.in_game.components.Chat;
import client.view.gui.javafx_controllers.in_game.components.Top;
import client.view.gui.javafx_controllers.in_game.components.pc_board.PcBoard;
import common.Constants;
import common.enums.PcColourEnum;
import common.events.ModelEventListener;
import common.events.lobby_events.LobbyEvent;
import common.remote_interfaces.RemotePlayer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.rmi.RemoteException;

public class SetupController extends AbstractJavaFxController {
   
   @FXML private transient GridPane mainPane;
   @FXML private transient HBox skullTrack;
   @FXML private transient TilePane maps;
   @FXML private transient HBox pcs;
   @FXML private transient Chat chatController;
   private Circle[] circles = new Circle[Constants.MAX_KILL_SHOT_TRACK_SIZE];
   private int selectedSkulls = Constants.MIN_KILL_SHOT_TRACK_SIZE;
   
   
   
   public SetupController() throws RemoteException {
   }
   
   
   public void initialize(){
      GridPane.setValignment( maps,VPos.CENTER );
      GridPane.setValignment( pcs, VPos.BOTTOM );
      //init skulls track
      ImageView skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_0.png", Top.KILLSHOT_HEIGHT ));
      Circle circle = circles[0] = new Circle( skullImage.getImage().getWidth()/2, Color.RED );
      circle.setStroke( Color.BLACK );
      circle.setOpacity( .5 );
      skullImage.setPreserveRatio( true );
      skullTrack.getChildren().add( new StackPane( skullImage, circle) );
      for(int i=1; i<Constants.MAX_KILL_SHOT_TRACK_SIZE-1;i++){
         int forLambda = i;
         skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_i.png", Top.KILLSHOT_HEIGHT ));
         circle = circles[i] = new Circle( skullImage.getImage().getWidth()/2,Color.RED );
         circle.setStroke( Color.BLACK );
         circle.setOpacity( .5 );
         if(i>=Constants.MIN_KILL_SHOT_TRACK_SIZE) {
            skullImage.setOnMouseClicked( e -> chooseSkulls( forLambda ) );
            skullImage.setOnMouseEntered( e->showCirclesBeforeIndex( forLambda ) );
            circle.setVisible( false );
         }
         skullImage.setPreserveRatio( true );
         skullTrack.getChildren().add( new StackPane( skullImage, circle) );
      }
      skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_ultimo.png", Top.KILLSHOT_HEIGHT ));
      skullImage.setOnMouseClicked( e -> chooseSkulls( Constants.MAX_KILL_SHOT_TRACK_SIZE-1 ) );
      skullImage.setOnMouseEntered( e->showCirclesBeforeIndex( Constants.MAX_KILL_SHOT_TRACK_SIZE-1 ) );
      skullImage.setPreserveRatio( true );
      circle = circles[Constants.MAX_KILL_SHOT_TRACK_SIZE-1] = new Circle( skullImage.getImage().getWidth()/2,Color.RED );
      circle.setStroke( Color.BLACK );
      circle.setOpacity( .5 );
      circle.setVisible( false );
      skullTrack.getChildren().add( new StackPane( skullImage, circle) );
      skullTrack.setOnMouseExited( e->showCirclesBeforeIndex( selectedSkulls ) );
      
      //init maps selection
      int sqrtMaps = ( int ) Math.ceil( Math.sqrt( Constants.LAST_MAP ) );
      maps.setPrefRows( sqrtMaps );
      maps.setPrefColumns( sqrtMaps );
      maps.setTileAlignment( Pos.CENTER );
      String prefix = "/images/maps/map_";
      String postfix = ".png";
      for(int i=Constants.FIRST_MAP-1;i<Constants.LAST_MAP;i++){
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
      }
      setEnabled( false );
   }
   
   private void chooseSkulls(int n){
      try {
         player.chooseNumberOfSkulls( n );
         selectedSkulls = n;
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   private void chooseMap(int n){
      try {
         player.chooseMap( n );
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
         circles[i].setVisible( i<=index );
      }
   }
   
   @Override
   public void printMessage(String msg) {
      chatController.showServerMessage( msg );
   }
   
   @Override
   public void ack(String message) {
      printMessage( message );
   }
   
   @Override
   public void chatMessage(String message) {
   
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
   
   public void setEnabled(boolean enabled){
      mainPane.setDisable( !enabled );
      mainPane.setOpacity( enabled?1:.5 );
   }

   @Override
   public void notifyEvent(LobbyEvent event) throws RemoteException {

   }
}
