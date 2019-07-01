package client.view.gui.javafx_controllers.components.pc_board;

import common.dto_model.KillShotTrackDTO;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemotePlayer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Azioni implements ChangeListener<ObjectProperty<KillShotTrackDTO>> {
   @FXML private StackPane mainPane;
   @FXML private ImageView azioni;
   private RemotePlayer player;
   private PcColourEnum color;
   
   ReadOnlyDoubleProperty widthProperty(){
      return azioni.getImage().widthProperty();
   }
   
   public void setColor(PcColourEnum color){
      this.color = color;
      azioni.setImage( new Image( PcBoard.DIRECTORY+color.getName().toLowerCase()+"/azioni.png",0,PcBoard.HEIGHT,true,false ) );
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
   public void changed(ObservableValue<? extends ObjectProperty<KillShotTrackDTO>> obsTrack, ObjectProperty<KillShotTrackDTO> oldV, ObjectProperty<KillShotTrackDTO> newV) {
      if (true) { //TODO: manca il valore della frenzy
         try {
            mainPane = new FXMLLoader(getClass().getResource( "/fxml/inGame/pc_board/frenzy/frenzyActions.fxml")).load();
            azioni.setImage( new Image( PcBoard.DIRECTORY+color.getName().toLowerCase()+PcBoard.FRENZY_SUBDIR+"/azioni.png",0,PcBoard.HEIGHT,true,false ) );
         } catch ( IOException e ) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
         }
      }
   }
}
