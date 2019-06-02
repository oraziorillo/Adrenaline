package server.controller.states;

import server.controller.Controller;
import server.model.Pc;
import server.model.squares.Square;
import java.util.Set;

/**
 * When user chooses the Runaround action
 */
public class RunAroundState extends State{
   
   private boolean undo;
   private Square targetSquare;
   private Set<Square> targetableSquares;
   
   RunAroundState(Controller controller) {
      super(controller);
      this.undo = false;
      setTargetableToValidSquares(controller.getCurrPc());
   }
   
   /**
    * pre selects the target square
    * @param targetSquare the square the user wants to run to
    */
   @Override
   public void selectSquare(Square targetSquare){
      if (targetSquare.isTargetable())
         this.targetSquare = targetSquare;
   }
   
   /**
    *Sets Game's valid targets to the squares the referencePc can run to, and enables them
    * @param referencePc a Pc
    */
   @Override
   void setTargetableToValidSquares(Pc referencePc){
      int maxDistance = controller.isFinalFrenzy() ? 4 : 3;
      targetableSquares = referencePc.getCurrSquare().atDistance(maxDistance);
      controller.getGame().setTargetableSquares(targetableSquares, true);
   }
   
   /**
    * disables targetable squares. Influences the transition
    * @return true
    */
   @Override
   public boolean undo() {
      controller.getGame().setTargetableSquares(targetableSquares, false);
      undo = true;
      return true;
   }
   
   /**
    * Applies the pre-setted movement
    * @return false if no target to move to was setted before, true else
    */
   @Override
   public boolean ok() {
      if (targetSquare != null) {
         controller.getCurrPc().moveTo(targetSquare);
         controller.getGame().setTargetableSquares(targetableSquares, false);
         return true;
      }
      return false;
   }
   
   /**
    * Transition
    * @return StartTurnState if undo was called or the current player has some actions to perform still, EndTurnState else
    */
   @Override
   public State nextState() {
      if (undo)
         return new StartTurnState(controller);
      controller.decreaseRemainingActions();
      if(controller.getRemainingActions() == 0){
         controller.resetRemainingActions();
         return new EndTurnState(controller);
      }
      else
         return new StartTurnState(controller);
   }
}
