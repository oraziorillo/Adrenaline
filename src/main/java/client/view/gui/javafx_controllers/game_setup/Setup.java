package client.view.gui.javafx_controllers.game_setup;

import client.view.gui.ImageCache;
import client.view.gui.javafx_controllers.in_game.components.Top;
import client.view.gui.javafx_controllers.in_game.components.pc_board.PcBoard;
import common.Constants;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

import java.io.IOException;

public class Setup {
   
   public GridPane mainPane;
   public HBox skullTrack;
   public TilePane maps;
   public HBox pcs;
   private RemotePlayer player;
   
   public void initialize(){
      GridPane.setValignment( maps,VPos.CENTER );
      GridPane.setValignment( pcs, VPos.BOTTOM );
      //init skulls track
      ImageView skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_0.png", Top.KILLSHOT_HEIGHT ));
      skullImage.setPreserveRatio( true );
      skullTrack.getChildren().add( skullImage );
      for(int i = 1; i< Constants.MAX_KILL_SHOT_TRACK_SIZE-1; i++){
         int forLambda = i;
         skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_i.png",Top.KILLSHOT_HEIGHT ) );
         skullImage.setPreserveRatio( true );
         if(i>=Constants.MIN_KILL_SHOT_TRACK_SIZE)
            skullImage.setOnMouseClicked( e -> chooseSkulls( forLambda ) );
         skullTrack.getChildren().add( skullImage );
      }
      skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_ultimo.png", Top.KILLSHOT_HEIGHT ));
      skullImage.setPreserveRatio( true );
      skullImage.setOnMouseClicked( e -> chooseSkulls( Constants.MAX_KILL_SHOT_TRACK_SIZE-1 ) );
      skullTrack.getChildren().add( skullImage );
      
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
   }
   
   private void chooseSkulls(int n){
      try {
         player.chooseNumberOfSkulls( n );
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
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
}
