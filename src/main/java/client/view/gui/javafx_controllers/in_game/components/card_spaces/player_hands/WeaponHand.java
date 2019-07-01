package client.view.gui.javafx_controllers.in_game.components.card_spaces.player_hands;

import common.Constants;
import common.dto_model.WeaponCardDTO;

public class WeaponHand extends CardHand<WeaponCardDTO> {
   
   public WeaponHand() {
      super( Constants.MAX_WEAPONS_IN_HAND );
      super.initialize();
   }
}
