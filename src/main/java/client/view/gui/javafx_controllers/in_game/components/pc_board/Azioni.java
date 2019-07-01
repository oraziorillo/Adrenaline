package client.view.gui.javafx_controllers.in_game.components.pc_board;

import common.Constants;
import common.dto_model.KillShotTrackDTO;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Azioni implements ChangeListener<KillShotTrackDTO> {
   @FXML private StackPane mainPane;
   @FXML private ImageView azioniImages;
   private RemotePlayer player;
   private PcColourEnum color;
   
   ReadOnlyDoubleProperty widthProperty(){
      return azioniImages.getImage().widthProperty();
   }
   
   public void setColor(PcColourEnum color){
      this.color = color;
      azioniImages.setImage( new Image( PcBoard.DIRECTORY+color.getName().toLowerCase()+"/azioni.png",0,PcBoard.HEIGHT,true,false ) );
   }
   
   @FXML
   private void moveButton() throws IOException {
      player.runAround();
   }
   
   @FXML
   private void collectButton() throws IOException {
      player.grabStuff();
   }
   
   @FXML
   private void shootButton() throws IOException {
      player.shootPeople();
   }
   
   @Override
   public void changed(ObservableValue<? extends KillShotTrackDTO> obsTrack, KillShotTrackDTO oldV, KillShotTrackDTO newV) {
      if (!newV.getKillShotTrack()[Constants.MAX_KILL_SHOT_TRACK_SIZE-1].isSkulled()) {
         try {
            mainPane = new FXMLLoader(getClass().getResource( "/fxml/inGame/pc_board/frenzy/frenzyActions.fxml")).load();
            azioniImages.setImage( new Image( PcBoard.DIRECTORY+color.getName().toLowerCase()+PcBoard.FRENZY_SUBDIR+"/azioniImages.png",0,PcBoard.HEIGHT,true,false ) );
         } catch ( IOException e ) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
         }
      }
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
}
