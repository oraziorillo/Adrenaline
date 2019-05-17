package controller.states;

import controller.Controller;
import model.Pc;
import model.Square;

public class GrabStuffState extends State{

    private boolean executed;

    GrabStuffState(Controller controller) {
        super(controller);
        this.executed = false;
    }

    @Override
    public boolean selectSquare(Pc currPc, Square targetSquare){
        if (targetSquare.isEmpty())
            return false;
        move(currPc, targetSquare);
        if (!controller.getGame().getSpawnSquares().contains(targetSquare)) {
            currPc.collectAmmos();
            executed = true;
        }
        return true;
    }

    @Override
    public void setTargetables(Pc referencePc){
        int maxDistance;
        if (!controller.isFinalFrenzy())
            maxDistance = (referencePc.getAdrenaline() < 1) ? 1 : 2;
        else {
            maxDistance = (controller.beforeFirstPlayer(controller.getCurrPlayerIndex())) ? 2 : 3;
        }
        controller.getGame().setTargetables(maxDistance, referencePc.getCurrSquare());
    }


    @Override
    public State nextState() {
        if(executed) {
            executed = false;
            controller.decreaseRemainingActions();
            if (controller.getRemainingActions() == 0) {
                controller.resetRemainingActions();
                return new EndTurnState(controller);
            } else
                return new StartTurnState(controller);
        } else {
            return new GrabWeaponState(controller);
        }
    }
}
