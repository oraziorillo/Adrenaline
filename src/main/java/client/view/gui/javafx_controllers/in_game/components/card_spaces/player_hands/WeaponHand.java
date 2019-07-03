package client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands;

import common.Constants;
import common.dto_model.WeaponCardDTO;

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
   
   @Override
   protected void appear(int cardIndex) {
      super.appear( cardIndex );
      try {
         player.chooseWeaponOfMine( cardIndex );
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
}
