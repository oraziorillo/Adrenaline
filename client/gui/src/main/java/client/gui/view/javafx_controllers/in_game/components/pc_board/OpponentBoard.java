package client.gui.view.javafx_controllers.in_game.components.pc_board;

import client.gui.view.javafx_controllers.in_game.components.Ammo;
import common.dto_model.PcDTO;
import common.dto_model.PowerUpCardDTO;
import common.dto_model.WeaponCardDTO;
import common.enums.PcColourEnum;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * this is like a read-only version of a pc board. it can even display cards and ammos
 * @see PcBoard
 */
public class OpponentBoard implements MapChangeListener<PcColourEnum,PcDTO> {
   @FXML private VBox mainPane;
   @FXML private ImageView immagine;
   @FXML private DoubleProperty HEIGHT = new SimpleDoubleProperty( 200 );
   @FXML private Vita vitaController;
   @FXML private HBox weapons;
   @FXML private HBox powerups;
   @FXML private Ammo ammosController;
   private PcColourEnum settedColor;
   
   public void initialize(){
      mainPane.maxHeightProperty().bind( Bindings.multiply( 2,HEIGHT ) );
   }
   
   public void setColor(PcColourEnum color){
      settedColor=color;
      vitaController.setColor( color );
      immagine.setImage( new Image( "/images/pc_board/"+color.getName().toLowerCase()+"/immagine.png",0,0,true,false ) );
      vitaController.fitHeightProperty().bind( HEIGHT );
      immagine.fitHeightProperty().bind( HEIGHT );
   }
   
   /**
    *To listen to Pcs changes, and display always updated values
    * @param change cha change of the pc
    */
   @Override
   public void onChanged(Change<? extends PcColourEnum, ? extends PcDTO> change) {
      if(change.getKey().equals( this.settedColor ) && change.wasAdded()) {
         
         //clean up
            weapons.getChildren().clear();
            powerups.getChildren().clear();
            
            //update life
         vitaController.onChanged( change );
         
         //update weapons
         if(change.getValueAdded().getWeapons()!=null)
            for (WeaponCardDTO weapon : change.getValueAdded().getWeapons()) {
               if (weapon != null) {
                  ImageView img = new ImageView( new Image( weapon.getImagePath(), 0, 0, true, false ) );
                  img.setPreserveRatio( true );
                  img.fitHeightProperty().bind( HEIGHT );
                  weapons.getChildren().add( img );
               }
            }
         
         //update powerups
         if(change.getValueAdded().getPowerUps()!=null)
            for (PowerUpCardDTO powerUp : change.getValueAdded().getPowerUps()) {
               if (powerUp != null) {
                  ImageView img = new ImageView( new Image( powerUp.getImagePath(), 0, 0, true, false ) );
                  img.fitHeightProperty().bind( Bindings.divide( HEIGHT, 2 ) );
                  img.setPreserveRatio( true );
                  powerups.getChildren().add( img );
               }
            }
         
         //update ammos
         if(change.getValueAdded().getPcBoard()!=null&&change.getValueAdded().getPcBoard().getAmmo()!=null)
            ammosController.setAmmos( change.getValueAdded().getPcBoard().getAmmo() );
         
      }
   }
   
   public DoubleProperty heightProperty(){
      return HEIGHT;
   }
}
