package client.gui.controllers.components.weapons;

import common.model_dtos.WeaponCardDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

public class GeneralWeapon {
   @FXML public ImageView background;
   @FXML public GridPane contentPane;
   @FXML public StackPane mainPane;
   
   public void initialize(){
      background.imageProperty().addListener( (obs,oldV,newV)-> {
         mainPane.setMaxHeight( newV.getHeight() );
         mainPane.setMaxWidth( newV.getHeight() );
      });
   }
   
   @FXML
   public void setWeapon(WeaponCardDTO weapon){
      System.out.println(weapon.getImagePath());
      background.setImage( new Image( weapon.getImagePath(),true ) );
      int effect;
      for(effect=0;effect<weapon.getBasicEffects();effect++){
         Button effectButton = new Button();
         effectButton.setText("effect "+effect);
         effectButton.setBackground( null ); //transparent
         effectButton.setMaxHeight( Double.MAX_VALUE );  //can grow without limits
         effectButton.setMaxWidth( Double.MAX_VALUE );
         GridPane.setVgrow( effectButton, Priority.ALWAYS );   //fill cell
         GridPane.setHgrow( effectButton,Priority.ALWAYS );
         contentPane.add( effectButton,0,effect,weapon.getUpgrades()+1,1);
      }
      effect++;
      for (int upgrade = 0; upgrade<weapon.getUpgrades();upgrade++){
         Button upgradeButton = new Button();
         //upgradeButton.setText( "selectUpgrade "+selectUpgrade);
         upgradeButton.setBackground( null );
         upgradeButton.setMaxHeight( Double.MAX_VALUE );
         upgradeButton.setMaxWidth( Double.MAX_VALUE );
         GridPane.setVgrow( upgradeButton,Priority.ALWAYS );
         GridPane.setHgrow( upgradeButton,Priority.ALWAYS );
         contentPane.add( upgradeButton,upgrade,effect );
      }
   }
}
