package client.view.gui.javafx_controllers.in_game.components;

import client.view.gui.ImageCache;
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
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
   
   public final MapChangeListener<SquareDTO,SquareDTO> squareListener = c->Platform.runLater( ()->cardHolderController.onChanged( c ));
   public final MapChangeListener<PcColourEnum, PcDTO> pcListener = change -> Platform.runLater( ()-> {
      ammoController.onChanged( change );
      if (change.wasAdded() && change.getValueAdded().getColour().equals( settedColour ))
         punti.setText( Short.toString( change.getValueAdded().getPcBoard().getPoints() ) );
   });
   
   public void initialize(){
      //Init killshottrack
      ImageView skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_0.png", KILLSHOT_HEIGHT) );
      StackPane circlePane = killshotTrackPanes[0] = new StackPane( );
      StackPane skullPane = new StackPane( skullImage,circlePane );
      skullImage.setPreserveRatio( true );
      killShotTrack.getChildren().add( skullPane );
      for(int i=1; i<Constants.MAX_KILL_SHOT_TRACK_SIZE-1;i++){
         skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_i.png", KILLSHOT_HEIGHT ) );
         circlePane = killshotTrackPanes[i] = new StackPane(  );
         skullImage.setPreserveRatio( true );
         skullPane = new StackPane( skullImage, circlePane );
         killShotTrack.getChildren().add( skullPane );
      }
      skullImage = new ImageView( ImageCache.loadImage( "/images/teschio_ultimo.png", KILLSHOT_HEIGHT));
      circlePane = killshotTrackPanes[Constants.MAX_KILL_SHOT_TRACK_SIZE-1] = new StackPane(  );
      skullImage.setPreserveRatio( true );
      skullPane = new StackPane( skullImage,circlePane );
      killShotTrack.getChildren().add( skullPane );
      //Final frenzy box
      skullImage = new ImageView(ImageCache.loadImage( "/images/killshot_box.png", KILLSHOT_HEIGHT ) );
      frenzyTrackPane = new FlowPane();
      frenzyTrackPane.setMaxSize( skullImage.getImage().getWidth(),skullImage.getImage().getHeight() );
      skullImage.setPreserveRatio( true );
      skullPane = new StackPane( skullImage, frenzyTrackPane );
      killShotTrack.getChildren().add( skullPane );
      //card holders
      cardHolderController.setCorner( CardinalDirectionEnum.NORTH );
      cardHolderController.setColor( AmmoEnum.BLUE );
   }
   
   
   @FXML
   public void showSettings() throws IOException {
      FXMLLoader loader = new FXMLLoader( getClass().getResource( "/fxml/inGame/dialogs/settingsMenu.fxml" ) );
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
      ammoController.setPlayer(player);
   }
   
   public void setColour(PcColourEnum colour){
      settedColour = colour;
      ammoController.setColour(colour);
   }
   
   @Override
   public void changed(ObservableValue<? extends KillShotTrackDTO> obs, KillShotTrackDTO oldV, KillShotTrackDTO track) {
      KillShotDTO[] killShotTrack = track.getKillShotTrack();
      for(int i=0, iOnTrack=Constants.MAX_KILL_SHOT_TRACK_SIZE-killShotTrack.length;iOnTrack<Constants.MAX_KILL_SHOT_TRACK_SIZE;i++,iOnTrack++){
         StackPane circlePane = this.killshotTrackPanes[iOnTrack];
         circlePane.getChildren().clear();
         Color color = killShotTrack[i].isSkulled()?Color.RED:Color.valueOf( killShotTrack[i].getColour().toString() );
         int circleNumb = (killShotTrack[i].isOverkilled()&&!killShotTrack[i].isSkulled())?2:1;
         for(int j=0;j<circleNumb;j++){
            Circle added = new Circle( 0,color );
            added.radiusProperty().bind( Bindings.subtract( Bindings.divide( Bindings.min( circlePane.heightProperty(),circlePane.widthProperty() ),2 ), 1 ));
            added.setStroke( Color.BLACK );
            added.setOpacity( killShotTrack[i].isSkulled()?.5:1 );
            added.translateYProperty().bind( Bindings.multiply( added.radiusProperty(),-.5*j) );
            circlePane.getChildren().add( added );
            added.toBack();
         }
      }
      this.frenzyTrackPane.getChildren().clear();
      double circles= Math.ceil( Math.sqrt( track.getFinalFrenzyKillShotTrack().length ) );
      for(KillShotDTO k:track.getFinalFrenzyKillShotTrack()){
         if(k==null) break;
         Circle c;
         c = new Circle(0,Color.valueOf( k.getColour().toString()));
         c.setStroke( Color.BLACK );
         c.radiusProperty().bind( Bindings.subtract( Bindings.divide( Bindings.min( frenzyTrackPane.heightProperty(), frenzyTrackPane.widthProperty() ), 2 * circles ), 1 ) );
         this.frenzyTrackPane.getChildren().add( c );
      }
      
   }
   
}
