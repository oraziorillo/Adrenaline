package common.events.pc_events;

import common.dto_model.PcDTO;
import common.events.ModelEvent;

public abstract class PcEvent extends ModelEvent {
   @Override
   public abstract PcDTO getNewValue();
}
