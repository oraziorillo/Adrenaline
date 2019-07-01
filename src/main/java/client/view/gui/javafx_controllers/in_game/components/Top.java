package client.view.gui.javafx_controllers.in_game.components;

import client.view.gui.javafx_controllers.in_game.components.card_spaces.CardHolder;
import client.view.gui.javafx_controllers.in_game.dialogs.SettingsMenu;
import common.Constants;
import common.dto_model.KillShotDTO;
import common.dto_model.KillShotTrackDTO;
import common.dto_model.PcDTO;
import common.dto_model.SquareDTO;
import common.enums.AmmoEnum;
import common.enums.CardinalDirectionEnum;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Top implements ChangeListener<KillShotTrackDTO> {
   @FXML private Ammo ammoController;
   @FXML private CardHolder cardHolderController;
   @FXML private HBox killShotTrack;
   @FXML private AnchorPane cardHolder;
   @FXML private Label punti;
   private HostServices hostServices;
   private RemotePlayer player;
   private PcColourEnum settedColour;
   private StackPane[] killshotTrackPanes = new StackPane[Constants.MAX_KILL_SHOT_TRACK_SIZE];
   private FlowPane frenzyTrackPane = new FlowPane();
   public static final double KILLSHOT_HEIGHT = 100d;
   
   public final MapChangeListener<SquareDTO,SquareDTO> squareListener = c->cardHolderController.onChanged( c );
   public final MapChangeListener<PcColourEnum, PcDTO> pcListener = change -> {
      ammoController.onChanged( change );
      if(change.wasAdded() && change.getValueAdded().getColour().equals( settedColour )){
         punti.setText( Short.toString( change.getValueAdded().getPcBoard().getPoints()) );
      }
   };
   
   public void initialize(){
      ImageView skullImage = new ImageView( new Image( "/images/teschio_0.png",0, KILLSHOT_HEIGHT,true, false) );
      StackPane circlePane = killshotTrackPanes[0] = new StackPane( );
      StackPane skullPane = new StackPane( skullImage,circlePane );
      skullImage.setPreserveRatio( true );
      killShotTrack.getChildren().add( skullPane );
      for(int i=1; i<Constants.MAX_KILL_SHOT_TRACK_SIZE-1;i++){
         skullImage = new ImageView( new Image( "/images/teschio_i.png",0, KILLSHOT_HEIGHT,true, false ) );
         circlePane = killshotTrackPanes[i] = new StackPane(  );
         skullImage.setPreserveRatio( true );
         skullPane = new StackPane( skullImage, circlePane );
         killShotTrack.getChildren().add( skullPane );
      }
      skullImage = new ImageView( new Image( "/images/teschio_ultimo.png",0, KILLSHOT_HEIGHT,true,false ));
      circlePane = killshotTrackPanes[Constants.MAX_KILL_SHOT_TRACK_SIZE-1] = new StackPane(  );
      skullImage.setPreserveRatio( true );
      skullPane = new StackPane( skullImage,circlePane );
      killShotTrack.getChildren().add( skullPane );
      //Final frenzy box
      skullImage = new ImageView(new Image( "/images/killshot_box.png",0, KILLSHOT_HEIGHT,true,false ) );
      frenzyTrackPane = new FlowPane();
      skullImage.setPreserveRatio( true );
      skullPane = new StackPane( skullImage, frenzyTrackPane );
      killShotTrack.getChildren().add( skullPane );
      cardHolderController.setCorner( CardinalDirectionEnum.NORTH );
      cardHolderController.setColor( AmmoEnum.BLUE );
   }
   
   
   @FXML
   public void showSettings() throws IOException {
      FXMLLoader loader = new FXMLLoader( getClass().getResource( "/fxml/inGame/settingsMenu.fxml" ) );
      Parent parent = loader.load();
      SettingsMenu settingsMenu = loader.getController();
      settingsMenu.setHostServices( hostServices );
      settingsMenu.setPlayer( player );
      Scene scene = new Scene( parent );
      Stage stage = new Stage();
      stage.initModality( Modality.APPLICATION_MODAL );
      stage.initStyle( StageStyle.UNDECORATED );
      stage.setScene( scene );
      stage.showAndWait();
   }
   
   public void setHostServices(HostServices hostServices) {
      this.hostServices = hostServices;
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
      cardHolderController.setPlayer( player );
   }
   
   public void setColour(PcColourEnum colour){
      settedColour = colour;
      ammoController.setColour(colour);
   }
   
   @Override
   public void changed(ObservableValue<? extends KillShotTrackDTO> obs, KillShotTrackDTO oldV, KillShotTrackDTO track) {
      for(int i=0;i<Constants.MAX_KILL_SHOT_TRACK_SIZE;i++){
         StackPane circlePane = this.killshotTrackPanes[i];
         circlePane.getChildren().clear();
         Color color = track.getKillShotTrack()[i].isSkulled()?Color.RED:Color.valueOf( track.getKillShotTrack()[i].getColour().toString() );
         int circleNumb = track.getKillShotTrack()[i].isOverkilled()?2:1;
         for(int j=0;j<circleNumb;j++){
            Circle added = new Circle( circlePane.getWidth()/2,color );
            added.setTranslateY( circlePane.getWidth()/4 );
         }
      }
      this.frenzyTrackPane.getChildren().clear();
      double circles= Math.ceil( Math.sqrt( track.getFinalFrenzyKillShotTrack().length ) );
      for(KillShotDTO k:track.getFinalFrenzyKillShotTrack()){
         Circle c = new Circle((Math.min( frenzyTrackPane.getHeight(),frenzyTrackPane.getWidth() )/circles)-1,Color.valueOf( k.getColour().toString() ));
         this.frenzyTrackPane.getChildren().add( c );
      }
      
   }
   
}
