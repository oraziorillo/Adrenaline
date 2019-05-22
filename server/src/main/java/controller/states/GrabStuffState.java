package controller.states;

import controller.Controller;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.Pc;
import model.squares.Square;

import java.util.HashSet;

public class GrabStuffState extends State{

    private Square targetSquare;
    private HashSet<Square> targetableSquares;

    GrabStuffState(Controller controller) {
        super(controller);
        setTargetableToValidSquares(controller.getCurrPc());
    }


    @Override
    public void selectWeaponOnBoard(int index) {
        if (targetSquare != null)
            targetSquare.setWeaponToGrabIndex(index);
    }


    @Override
    public void selectWeaponOfMine(int index) {
        if (targetSquare != null)
            targetSquare.setWeaponToDropIndex(index);
    }


    @Override
    public void selectSquare(Square targetSquare){
        if (!targetSquare.isEmpty())
            this.targetSquare = targetSquare;
    }

    @Override
    void setTargetableToValidSquares(Pc referencePc){
        int maxDistance;
        if (!controller.isFinalFrenzy())
            maxDistance = (referencePc.getAdrenaline() < 1) ? 1 : 2;
        else {
            maxDistance = (controller.beforeFirstPlayer(controller.getCurrPlayerIndex())) ? 2 : 3;
        }
        targetableSquares = referencePc.getCurrSquare().atDistance(maxDistance);
        controller.getGame().setTargetableSquares(targetableSquares, true);
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
            currPc.moveTo(oldPosition);
            //TODO print what the player should select
            return false;
        } catch (NotEnoughAmmoException e) {
            currPc.moveTo(oldPosition);
            //TODO the pc has not enough ammo
            return false;
        }
        controller.getGame().setTargetableSquares(targetableSquares, false);
        return true;
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
