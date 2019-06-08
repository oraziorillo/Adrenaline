package client.view.gui.controllers;

import client.view.gui.model_dtos.PowerUpCardDTO;
import client.view.gui.model_dtos.WeaponCardDTO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import server.enums.CardinalDirectionEnum;

public class guiController {
   @FXML private GridPane killShotTrack;
   @FXML MapController mappaController;
   @FXML CardHolder cardHolderLeftController;
   @FXML CardHolder cardHolderRightController;
   @FXML CardHand<WeaponCardDTO> weaponHandController;
   @FXML CardHand<PowerUpCardDTO> powerUpHandController;
   @FXML HBox underMapButtons;
   
   public void initialize(){
      mappaController.setMap( 0 );
      cardHolderLeftController.setCorner( CardinalDirectionEnum.WEST );
      cardHolderRightController.setCorner( CardinalDirectionEnum.EAST );
      for(int i=0, size = underMapButtons.getChildren().size();i<size;i++){
         Node n = underMapButtons.getChildren().get( i );
         n.setTranslateX( 2*(size-i) );
         n.setViewOrder( i );
      }
      test();
   }
   
   private void test(){
      for(int i=0;i<3;i++) {
         weaponHandController.setCard( new WeaponCardDTO( "martello_ionico" ), i );
         powerUpHandController.setCard( new PowerUpCardDTO(), i );
      }
   }
   
}
