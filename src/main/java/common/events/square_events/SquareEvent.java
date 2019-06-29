package common.events.square_events;

import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;
import common.events.ModelEvent;

public abstract class SquareEvent implements ModelEvent {

   SquareDTO square;
   boolean isUncensored;


   SquareEvent(SquareDTO square){
      this.square = square;
   }


   SquareEvent(SquareDTO square, boolean isUncensored) {
      this.square = square;
      this.isUncensored = true;
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
