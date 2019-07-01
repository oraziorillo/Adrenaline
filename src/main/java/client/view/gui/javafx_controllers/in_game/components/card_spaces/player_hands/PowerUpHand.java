package client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands;

import common.Constants;
import common.dto_model.PowerUpCardDTO;

import java.io.IOException;

public class PowerUpHand extends CardHand<PowerUpCardDTO> {
   
   public PowerUpHand(){
      super(Constants.MAX_POWER_UPS_IN_HAND);
      super.initialize();
   }
   
   @Override
   protected void appear(int cardIndex) {
      super.appear( cardIndex );
      try {
         player.choosePowerUp( cardIndex );
      } catch ( IOException e ) {
         Thread.getDefaultUncaughtExceptionHandler().uncaughtException( Thread.currentThread(),e );
      }
   }
}
