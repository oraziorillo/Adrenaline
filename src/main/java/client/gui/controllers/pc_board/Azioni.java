package client.gui.controllers.pc_board;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Azioni {
   @FXML
   ImageView azioni;
   
   @FXML public Image getImage(){
      return azioni.getImage();
   }
   
   @FXML public void setImage(Image image){
      azioni.setImage( image );
   }
}
