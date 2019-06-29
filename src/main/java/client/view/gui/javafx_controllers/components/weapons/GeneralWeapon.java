package client.view.gui.javafx_controllers.components.weapons;

import common.dto_model.WeaponCardDTOFirstVersion;
import common.remote_interfaces.RemotePlayer;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GeneralWeapon {
   @FXML public ImageView background;
   @FXML public GridPane contentPane;
   @FXML public StackPane mainPane;
   private RemotePlayer player;
   
   public void initialize(){
      background.imageProperty().addListener( (obs,oldV,newV)-> {
         mainPane.setMaxHeight( newV.getHeight() );
         mainPane.setMaxWidth( newV.getHeight() );
      });
   }
   
   @FXML
   public void setWeapon(WeaponCardDTOFirstVersion weapon){
      //TODO usi un tipo deprecato
      /*
      background.setImage( new Image( weapon.getImagePath(),true ) );
      int effect;
      for(effect=0;effect<weapon.getBasicEffects();effect++){
         int e = effect;
         Button effectButton = new Button();
         effectButton.setText("effect "+effect);
         effectButton.setBackground( null ); //transparent
         effectButton.setMaxHeight( Double.MAX_VALUE );  //can grow without limits
         effectButton.setMaxWidth( Double.MAX_VALUE );
         effectButton.setOnAction( evt-> {
            try {
               player.chooseWeaponOfMine( e );
            } catch ( IOException ex ) {
               try {
                  player.quit();
               } catch ( IOException ignored ) {}
            }
         } );
         GridPane.setVgrow( effectButton, Priority.ALWAYS );   //fill cell
         GridPane.setHgrow( effectButton,Priority.ALWAYS );
         contentPane.add( effectButton,0,effect,weapon.getUpgrades()+1,1);
      }
      effect++;
      for (int upgrade = 0; upgrade<weapon.getUpgrades();upgrade++){
         int u=upgrade;
         Button upgradeButton = new Button();
         upgradeButton.setBackground( null );
         upgradeButton.setMaxHeight( Double.MAX_VALUE );
         upgradeButton.setMaxWidth( Double.MAX_VALUE );
         upgradeButton.setOnAction( e-> {
            try {
               player.chooseUpgrade( u );
            } catch ( IOException ex ) {
               try {
                  player.quit();
               } catch ( IOException ignored ) {}
            }
         } );
         GridPane.setVgrow( upgradeButton,Priority.ALWAYS );
         GridPane.setHgrow( upgradeButton,Priority.ALWAYS );
         contentPane.add( upgradeButton,upgrade,effect );
      }
   }
   
   public void setPlayer(RemotePlayer player) {
      this.player = player;

       */
   }
}
