package client.view.gui.javafx_controllers.components.pc_board;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vita {
   @FXML private ImageView background;
   
   public void setBackground(Image image){
      background.setImage( image );
   }
   
   public Image getBackground(){
      return background.getImage();
   }
}
