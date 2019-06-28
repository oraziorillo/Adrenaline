package common.events.square_events;

import common.dto_model.DTO;
import common.dto_model.SquareDTO;
import common.events.ModelEvent;

public abstract class SquareEvent extends ModelEvent {
   @Override
   public abstract SquareDTO getNewValue();
}
