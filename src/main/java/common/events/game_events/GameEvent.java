package common.events.game_events;

import common.dto_model.GameBoardDTO;
import common.events.ModelEvent;

public abstract class GameEvent extends ModelEvent {
   @Override
   public abstract GameBoardDTO getNewValue();
   
}
