package client.view.gui.controllers;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class PcController {
   public HBox mainPane;
   public ImageView immagine;
   @FXML
   HBox translating;
   @FXML
   ImageView azioni;
   @FXML ImageView vita;
   private TranslateTransition translate;
   public void initialize(){
      translate = new TranslateTransition( new Duration( 500 ), translating);
      translating.setTranslateX( -vita.getImage().getWidth() );
      translate.setToX( 0 );
      translate.setFromX( -vita.getImage().getWidth() );
      mainPane.maxWidthProperty().bind( Bindings.add( azioni.getImage().widthProperty(), immagine.getImage().widthProperty() ));
      mainPane.minWidthProperty().bind( Bindings.add( azioni.getImage().widthProperty(), immagine.getImage().widthProperty() ));
      mainPane.prefWidthProperty().bind( Bindings.add( azioni.getImage().widthProperty(), immagine.getImage().widthProperty() ));
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
}
