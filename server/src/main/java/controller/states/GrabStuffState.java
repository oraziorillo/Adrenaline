package controller.states;

import controller.Controller;
import exceptions.EmptySquareException;
import model.Pc;
import model.squares.Square;

public class GrabStuffState extends State{

    private Square targetSquare;

    GrabStuffState(Controller controller) {
        super(controller);
    }


    @Override
    public void setWeaponToGrab(int index) {
        if (targetSquare != null)
            targetSquare.setWeaponToGrabIndex(index);
    }


    @Override
    public void setWeaponToDrop(int index) {
        if (targetSquare != null)
            targetSquare.setWeaponToDropIndex(index);
    }


    @Override
    public void selectSquare(Square targetSquare){
        if (!targetSquare.isEmpty())
            this.targetSquare = targetSquare;
    }

    @Override
    public boolean ok() {
        if (targetSquare.isEmpty())
            return false;
        Pc currPc = controller.getCurrPc();
        Square oldPosition = currPc.getCurrSquare();
        currPc.moveTo(targetSquare);
        try {
            currPc.collect();
        } catch (EmptySquareException e) {
            currPc.moveTo(oldPosition);
            //TODO print error
            return false;
        } catch (IllegalStateException e) {
            //TODO print what the player should select
            return false;
        }
        return true;
    }

    @Override
    public void setTargetableSquares(Pc referencePc){
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
        controller.decreaseRemainingActions();
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        } else
            return new StartTurnState(controller);
    }
}
