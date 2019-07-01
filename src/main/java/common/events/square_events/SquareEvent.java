package common.events.square_events;

import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;
import common.events.ModelEvent;

public abstract class SquareEvent implements ModelEvent {

   SquareDTO square;
   boolean censored;


   SquareEvent(SquareDTO square){
      this.square = square;
   }


   SquareEvent(SquareDTO square, boolean censored) {
      this.square = square;
      this.censored = true;
   }


   @Override
   public SquareDTO getDTO(){
      return square;
   }


   public PcColourEnum getPublisherColour(){
      return null;
   }


   public abstract SquareEvent switchToPrivate();
}
