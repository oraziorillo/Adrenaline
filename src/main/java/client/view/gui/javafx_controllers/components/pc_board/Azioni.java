package client.view.gui.javafx_controllers.components.pc_board;

import common.remote_interfaces.RemotePlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Azioni {
   @FXML
   ImageView azioni;
   private RemotePlayer player;
   
   @FXML public Image getImage(){
      return azioni.getImage();
   }
   
   @FXML public void setImage(Image image){
      azioni.setImage( image );
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
}
