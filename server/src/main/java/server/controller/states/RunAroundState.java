package server.controller.states;

import common.enums.ControllerMethodsEnum;
import server.controller.Controller;
import server.controller.Player;
import server.model.Pc;
import server.model.squares.Square;

import java.util.HashSet;
import java.util.Set;

/**
 * When user chooses the Run around action
 */
public class RunAroundState extends State{
   
   private boolean undo;
   private Square targetSquare;
   private Set<Square> targetableSquares;
   
   RunAroundState(Controller controller) {
      super(controller);
      this.undo = false;
      targetableSquares = new HashSet<>();
      setTargetableToValidSquares(controller.getCurrPc());
      controller.ackCurrent("(Use the command " + ControllerMethodsEnum.CHOOSE_SQUARE.getUsage() + ". Type \"h\" for details on all available commands)");
   }
   
   /**
    * pre selects the target square
    * @param row of the selected square
    * @param column of the selected square
    */
   @Override
   public void selectSquare(Player p, int row, int column) {
      Square s = controller.getGame().getSquare(row, column);
      if (s != null && s.isTargetable()) {
         this.targetSquare = s;
         controller.ackCurrent("\nSo you want to move to " + s.toString() + "...");
      }
   }
   
   /**
    *Sets Game's valid targets to the squares the referencePc can run to, and enables them
    * @param referencePc a Pc
    */
   @Override
   void setTargetableToValidSquares(Pc referencePc){
      int maxDistance = controller.isFinalFrenzy() ? 4 : 3;
      targetableSquares = referencePc.getCurrSquare().atDistance(maxDistance);
      targetableSquares.remove(controller.getCurrPc().getCurrSquare());
      controller.getGame().setTargetableSquares(targetableSquares, true);
   }
   
   /**
    * Undo the run action
    * @return true
    * @param p player for which the run action is undone
    */
   @Override
   public boolean isUndoable(Player p) {
      controller.getGame().setTargetableSquares(targetableSquares, false);
      undo = true;
      return true;
   }
   
   /**
    * Applies the pre-set movement
    * @return false if no target to move to was setted before, true else
    */
   @Override
   public boolean ok(Player p) {
      if (targetSquare != null) {
         controller.getCurrPc().moveTo(targetSquare);
         controller.getGame().setTargetableSquares(targetableSquares, false);
         return true;
      }
      return false;
   }


   public State forcePass(Player p) {
         controller.getGame().setTargetableSquares(targetableSquares, false);
         controller.resetRemainingActions();
         controller.nextTurn();
         return new InactiveState(controller, InactiveState.START_TURN_STATE);
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
