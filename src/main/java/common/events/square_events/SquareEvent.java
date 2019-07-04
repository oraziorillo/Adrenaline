package common.events.square_events;

import common.dto_model.SquareDTO;
import common.enums.PcColourEnum;
import common.events.ModelEvent;

/**
 * An event representing a square change
 */
public abstract class SquareEvent implements ModelEvent {
   
   /**
    * The updated square
    */
   SquareDTO square;
   /**
    * Used to hide sensible data
    */
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
   
   /**
    * Used to generate a copy of the this square, with sensible data hidden
    * @return a new SquareEvent with some data hidden
    */
   public abstract SquareEvent censor();
}
