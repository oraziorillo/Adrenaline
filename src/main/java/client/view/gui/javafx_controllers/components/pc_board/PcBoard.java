package client.view.gui.javafx_controllers.components.pc_board;

import common.dto_model.KillShotTrackDTO;
import common.dto_model.PcDTO;
import common.enums.PcColourEnum;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class PcBoard implements MapChangeListener<PcColourEnum, PcDTO> {
   static final String DIRECTORY = "/images/pc_board/";
   static final String FRENZY_SUBDIR = "/frenzy/";
   public HBox mainPane;
   public ImageView immagine;
   static Double HEIGHT=200d;
   @FXML HBox translating;
   @FXML Azioni azioniController;
   @FXML Vita vitaController;
   private TranslateTransition translate;
   private PcColourEnum settedColour;
   
   public final ChangeListener<ObjectProperty<PcColourEnum>> colorListener = (observableValue, oldV, newV) -> setPcColour( newV.get() );
   public final ChangeListener<ObjectProperty<KillShotTrackDTO>> frenzyListener =(obs,oldV,newV) -> {
      azioniController.changed( obs,oldV,newV );
      vitaController.changed( obs,oldV,newV );
   };
   
   public void initialize(){
      setPcColour( PcColourEnum.GREEN ); //to avoid NPE
      translate = new TranslateTransition( new Duration( 500 ), translating);
      translate.setToX( 0 );
      translate.setFromX( -vitaController.widthProperty().doubleValue() );
      translating.setTranslateX( -vitaController.widthProperty().doubleValue() );
      NumberBinding exactWidth = Bindings.add( azioniController.widthProperty(), immagine.getImage().widthProperty() );
      mainPane.maxWidthProperty().bind( exactWidth );
      mainPane.minWidthProperty().bind( exactWidth );
      mainPane.prefWidthProperty().bind( exactWidth );
   }
   @FXML
   private void appear(){
      translate.stop();
      translate.setRate( 1 );
      translate.play();
   }
   @FXML
   private void disappear(){
      System.out.println("disappear");
      translate.stop();
      translate.setRate( -1 );
      translate.play();
   }
   
   public void setPcColour(PcColourEnum color){
      settedColour = color;
      azioniController.setColor( color );
      vitaController.setColor( color );
      immagine.setImage( new Image( DIRECTORY+color.getName().toLowerCase()+"/immagine.png",0,HEIGHT,true,false ) );
   }
   
   @Override
   public void onChanged(Change<? extends PcColourEnum, ? extends PcDTO> change) {
      if(change.wasAdded() && this.settedColour.equals( change.getKey() ))
            vitaController.onChanged( change );
   }
   
}
