package client.view.gui.javafx_controllers.components.pc_board;

import common.enums.PcColourEnum;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class PcBoard implements ChangeListener<Boolean> {
   public HBox mainPane;
   public ImageView immagine;
   @FXML Double HEIGHT;
   @FXML HBox translating;
   @FXML Azioni azioniController;
   @FXML Vita vitaController;
   private TranslateTransition translate;
   public void initialize(){
      setPcColour( PcColourEnum.PURPLE );
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
      azioniController.setImage( new Image( "/images/pc_board/"+color.getName().toLowerCase()+"/azioni.png",0,HEIGHT,true,false ) );
      vitaController.setBackground( new Image( "/images/pc_board/"+color.getName().toLowerCase()+"/vita.png",0,HEIGHT,true,false ) );
      immagine.setImage( new Image( "/images/pc_board/"+color.getName().toLowerCase()+"/immagine.png",0,HEIGHT,true,false ) );
   }
   
   @Override
   public void changed(ObservableValue<? extends Boolean> finalFrenzyProp, Boolean oldFrenzy, Boolean newFrenzy) {
      if(newFrenzy){
         //TODO: carica azioni frenetiche
      }else{
         //TODO: carica azioni normali
      }
   }
}
