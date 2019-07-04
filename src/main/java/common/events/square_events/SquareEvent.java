package common.events.square_events;

import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;
import common.events.ModelEvent;

public abstract class SquareEvent implements ModelEvent {

   SquareDTO square;
   boolean isPrivate;


   SquareEvent(SquareDTO square){
      this.square = square;
   }


   SquareEvent(SquareDTO square, boolean isPrivate) {
      this.square = square;
      this.isPrivate = true;
   }


   @Override
   public SquareDTO getDTO(){
      return square;
   }


   public PcColourEnum getPublisherColour(){
      return null;
   }


   public abstract SquareEvent censor();
}
