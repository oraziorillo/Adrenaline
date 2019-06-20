package client.gui.javafx_controllers.components.pc_board;

import common.enums.PcColourEnum;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class PcController {
   public HBox mainPane;
   public ImageView immagine;
   @FXML Double HEIGHT;
   @FXML
   HBox translating;
   @FXML
   Azioni azioniController;
   @FXML
   Vita vitaController;
   private TranslateTransition translate;
   public void initialize(){
      azioniController.setImage( new Image( "/images/pc_board/banshee_azioni.png",0, HEIGHT, true,false ) );
      vitaController.setBackground( new Image( "/images/pc_board/banshee_vita.png",0,HEIGHT,true,false ) );
      translate = new TranslateTransition( new Duration( 500 ), translating);
      translating.setTranslateX( -vitaController.getBackground().getWidth() );
      translate.setToX( 0 );
      translate.setFromX( -vitaController.getBackground().getWidth() );
      mainPane.maxWidthProperty().bind( Bindings.add( azioniController.getImage().widthProperty(), immagine.getImage().widthProperty() ));
      mainPane.minWidthProperty().bind( Bindings.add( azioniController.getImage().widthProperty(), immagine.getImage().widthProperty() ));
      mainPane.prefWidthProperty().bind( Bindings.add( azioniController.getImage().widthProperty(), immagine.getImage().widthProperty() ));
   }
   @FXML
   private void appear(){
      translate.stop();
      translate.setRate( 1 );
      translate.play();
   }
   @FXML
   private void disappear(){
      translate.stop();
      translate.setRate( -1 );
      translate.play();
   }
   
   public void setPcColour(PcColourEnum color){
      azioniController.setImage( new Image( "/images/pc_board/"+color+".png",0,HEIGHT,true,false ) );
      vitaController.setBackground( new Image( "/images/pc_board"+color+".png",0,HEIGHT,true,false ) );
   }
}
