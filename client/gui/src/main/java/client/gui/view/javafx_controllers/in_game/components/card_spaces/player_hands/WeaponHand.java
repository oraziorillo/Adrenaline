package client.gui.view.javafx_controllers.in_game.components.card_spaces.player_hands;

import client.gui.view.javafx_controllers.in_game.InGameController;
import common.Constants;
import common.dto_model.WeaponCardDTO;
import javafx.scene.Node;

import java.io.IOException;

public class WeaponHand extends CardHand<WeaponCardDTO> {
   
   public WeaponHand() {
      super( Constants.MAX_WEAPONS_IN_HAND );
      super.initialize();
   }
   
   public void setCards(WeaponCardDTO[] cards){
      super.setCards( cards );
      for (int i=0;i<cards.length;i++){
         weaponControllers[i].setWeapon( cards[i] );
      }
   }
   
   /**
    * even calls player's choose weapon of mine method
    * @param cardIndex the index of the card to translate
    */
   @Override
   protected void appear(int cardIndex) {
      super.appear( cardIndex );
      try {
         player.chooseWeaponOfMine( cardIndex );
         for(Node n: getCardNodes())
            n.setEffect( null );
         getCardNodes().get( cardIndex ).setEffect( InGameController.selectedObjectEffect );
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
}
