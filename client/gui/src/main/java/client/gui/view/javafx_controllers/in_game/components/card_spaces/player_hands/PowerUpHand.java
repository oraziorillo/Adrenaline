package client.gui.view.javafx_controllers.in_game.components.card_spaces.player_hands;

import client.gui.view.javafx_controllers.in_game.InGameController;
import common.Constants;
import common.dto_model.PowerUpCardDTO;
import javafx.scene.Node;

import java.io.IOException;
import java.rmi.RemoteException;

public class PowerUpHand extends CardHand<PowerUpCardDTO> {
   
   public PowerUpHand(){
      super(Constants.MAX_POWER_UPS_IN_HAND+1); //For a short time user can have an extra powerup in his hand
      super.initialize();
   }
   
   /**
    * even calls player's choosePowerup method
    * @param cardIndex the index of the card to translate
    */
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
   
   /**
    * When a powerup card is clicked call usePowerup method of player
    * @param cards
    */
   public void setCards(PowerUpCardDTO[] cards){
      super.setCards( cards );
      for(int i=0; i<cards.length;i++){
         weaponControllers[i].setWeapon( cards[i] );
         weaponControllers[i].mainPane.setOnMouseClicked( evt->{
            try {
               player.usePowerUp();
            }catch ( RemoteException e ) {
               Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(), e );
            }
         } );
      }
   }
   
   @Override
   public PowerUpCardDTO[] getCards() {
      return ( PowerUpCardDTO[] ) super.getCards();
   }
   
   
}
