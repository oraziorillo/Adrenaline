package client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands;

import client.view.gui.javafx_controllers.in_game.InGameController;
import common.Constants;
import common.dto_model.PowerUpCardDTO;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;

public class PowerUpHand extends CardHand<PowerUpCardDTO> {
   
   public PowerUpHand(){
      super(Constants.MAX_POWER_UPS_IN_HAND+1); //For a short time user can have an extra powerup in his hand
      super.initialize();
   }
   
   @Override
   protected void appear(int cardIndex) {
      super.appear( cardIndex );
      try {
         for(Node n:getCardNodes())
            n.setEffect( null );
         getCardNodes().get( cardIndex ).setEffect( InGameController.selectedObjectEffect );
         player.choosePowerUp( cardIndex );
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
   
   public void setCards(PowerUpCardDTO[] cards){
      super.setCards( cards );
      for(int i=0; i<cards.length;i++){
         weaponControllers[i].setWeapon( cards[i] );
      }
   }
   
   @Override
   public PowerUpCardDTO[] getCards() {
      return ( PowerUpCardDTO[] ) super.getCards();
   }
   
   public List<Node> getCardNodes(){
      return mainPane.getChildren();
   }
   
   
}
