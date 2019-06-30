package client.view.gui.javafx_controllers.components.pc_board;

import common.dto_model.PcDTO;
import common.enums.PcColourEnum;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

//TODO: osserva anche killshottrack
public class PcBoard implements MapChangeListener<PcColourEnum, PcDTO>, ChangeListener<ObjectProperty<PcColourEnum>> {
   public HBox mainPane;
   public ImageView immagine;
   @FXML Double HEIGHT;
   @FXML HBox translating;
   @FXML Azioni azioniController;
   @FXML Vita vitaController;
   private TranslateTransition translate;
   private PcColourEnum settedColour;
   
   public void initialize(){
      translate = new TranslateTransition( new Duration( 500 ), translating);
      translating.setTranslateX( -vitaController.getBackground().getImage().getWidth() );
      translate.setToX( 0 );
      translate.setFromX( -vitaController.getBackground().getImage().getWidth() );
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
      settedColour = color;
      String dirPath = "/images/pc_board/";
      azioniController.setImage( new Image( dirPath+color.getName().toLowerCase()+"/azioni.png",0,HEIGHT,true,false ) );
      vitaController.setBackground( new Image( dirPath+color.getName().toLowerCase()+"/vita.png",0,HEIGHT,true,false ) );
      immagine.setImage( new Image( dirPath+color.getName().toLowerCase()+"/immagine.png",0,HEIGHT,true,false ) );
   }
   
   @Override
   public void onChanged(Change<? extends PcColourEnum, ? extends PcDTO> change) {
      if(change.wasAdded()) {
         if (this.settedColour.equals( change.getKey() )) {
            //TODO: aggiorna la vita
         }
      }
   }
   
   @Override
   public void changed(ObservableValue<? extends ObjectProperty<PcColourEnum>> obs, ObjectProperty<PcColourEnum> oldProp, ObjectProperty<PcColourEnum> newProp) {
      setPcColour( newProp.get() );
   }
}
