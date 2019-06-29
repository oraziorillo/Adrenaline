package server.controller.states;

import server.controller.Controller;
import server.model.Pc;
import server.model.squares.Square;

import java.io.IOException;
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
    * @param row of the selected square
    * @param column of the selected square
    */
   @Override
   public void selectSquare(int row, int column) {
      Square s = controller.getGame().getSquare(row, column);
      if (s != null && s.isTargetable()) {
         this.targetSquare = s;
         try {
            controller.getCurrPlayer().getView().ack("Lo square è stato selezionato");
         } catch (IOException e) {
            e.printStackTrace();
         }
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
      try {
         controller.getCurrPlayer().getView().ack(controller.getCurrPc().getCurrSquare().getRow() + " " + controller.getCurrPc().getCurrSquare().getCol());
      } catch (IOException e) {
         e.printStackTrace();
      }
      for (Square s: targetableSquares) {
         if (s.isTargetable()){
            try {
               controller.getCurrPlayer().getView().ack(s + "è targhettabile");
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }
   
   /**
    * disables targetable squares. Influences the transition
    * @return true
    */
   @Override
   public boolean undo() {
      controller.getGame().setTargetableSquares(targetableSquares, false);
      undo = true;
      try {
         controller.getCurrPlayer().getView().ack("Qua sta facendo undo");
      } catch (IOException e) {
         e.printStackTrace();
      }
      return true;
   }
   
   /**
    * Applies the pre-set movement
    * @return false if no target to move to was setted before, true else
    */
   @Override
   public boolean ok() {
      if (targetSquare != null) {
         controller.getCurrPc().moveTo(targetSquare);
         controller.getGame().setTargetableSquares(targetableSquares, false);
         try {
            controller.getCurrPlayer().getView().ack("Il pc si è spostato, fiiiiiga");
         } catch (IOException e) {
            e.printStackTrace();
         }
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
