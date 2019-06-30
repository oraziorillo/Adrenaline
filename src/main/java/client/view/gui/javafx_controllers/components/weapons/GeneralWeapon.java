package client.view.gui.javafx_controllers.components.weapons;

import common.dto_model.PowerUpCardDTO;
import common.dto_model.WeaponCardDTO;
import common.remote_interfaces.RemotePlayer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GeneralWeapon {
   @FXML public ImageView background;
   @FXML public GridPane contentPane;
   @FXML public StackPane mainPane;
   private RemotePlayer player;
   private boolean enabled = false;
   
   public void initialize(){
      background.imageProperty().addListener( (obs,oldV,newV)-> {
         mainPane.setMaxHeight( newV.getHeight() );
         mainPane.setMaxWidth( newV.getHeight() );
      });
   }
   
   @FXML
   public void setWeapon(WeaponCardDTO weapon){
      background.setImage( new Image( weapon.getImagePath(),true ) );
      int effect;
      for (effect = 0; effect < weapon.getBasicEffects(); effect++) {
         int e = effect;
         Button effectButton = new Button();
         effectButton.setText( "effect " + effect );
         effectButton.setBackground( null ); //transparent
         effectButton.setMaxHeight( Double.MAX_VALUE );  //can grow without limits
         effectButton.setMaxWidth( Double.MAX_VALUE );
         effectButton.setOnAction( evt -> chooseWeapon( e ) );
         GridPane.setVgrow( effectButton, Priority.ALWAYS );   //fill cell
         GridPane.setHgrow( effectButton, Priority.ALWAYS );
         contentPane.add( effectButton, 0, effect, weapon.getUpgrades() + 1, 1 );
      }
      effect++;
      for (int upgrade = 0; upgrade < weapon.getUpgrades(); upgrade++) {
         int u = upgrade;
         Button upgradeButton = new Button();
         upgradeButton.setBackground( null );
         upgradeButton.setMaxHeight( Double.MAX_VALUE );
         upgradeButton.setMaxWidth( Double.MAX_VALUE );
         upgradeButton.setOnAction( e -> chooseUpgrade( u ) );
         GridPane.setVgrow( upgradeButton, Priority.ALWAYS );
         GridPane.setHgrow( upgradeButton, Priority.ALWAYS );
         contentPane.add( upgradeButton, upgrade, effect );
      }
   }
   
   public void setWeapon(PowerUpCardDTO powerUpCard){
      background.setImage( new Image( powerUpCard.getImagePath(),true ) );
      mainPane.setOnMouseClicked( e -> usePowerup());
   }
   
   private void chooseWeapon(int index) {
      if(enabled) {
         try {
            player.switchFireMode();
         } catch ( IOException e ) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(), e );
         }
      }
   }
   
   private void chooseUpgrade(int index){
      if(enabled) {
         try {
            player.chooseUpgrade( index );
         } catch ( IOException e ) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(), e );
         }
      }
   }
   
   private void usePowerup(){
      if(enabled){
         try {
            player.usePowerUp();
         } catch ( IOException ex ) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),ex );
         }
      }
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;
   }
   
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }
}
