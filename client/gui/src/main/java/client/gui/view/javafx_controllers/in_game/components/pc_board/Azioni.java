package client.gui.view.javafx_controllers.in_game.components.pc_board;

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

/**
 * The buttons for run,grab and shoot. And their graphics
 */
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
   
   /**
    * Wraps player run method
    * @throws IOException if Remote Player is unreachable
    * @see RemotePlayer
    */
   @FXML
   private void moveButton() throws IOException {
      player.runAround();
   }
   
   /**
    * Wraps player grab method
    * @throws IOException if Remote Player is unreachable
    * @see RemotePlayer
    */
   @FXML
   private void collectButton() throws IOException {
      player.grabStuff();
   }
   
   /**
    * Wraps player shoot method
    * @throws IOException if Remote Player is unreachable
    * @see RemotePlayer
    */
   @FXML
   private void shootButton() throws IOException {
      player.shootPeople();
   }
   
   /**
    * Listens killshottrack to detect final frenzy and swap actions
    * @param obsTrack unused
    * @param oldV unused
    * @param newV the new killshottrack value. if the last skull is removed, the image changes
    */
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
