package controller.states;

import controller.Controller;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.Pc;
import model.squares.Square;
import java.util.Set;

public class GrabStuffState extends State{

    private boolean undo;
    private Square targetSquare;
    private Set<Square> targetableSquares;

    GrabStuffState(Controller controller) {
        super(controller);
        setTargetableToValidSquares(controller.getCurrPc());
    }


    @Override
    public void selectWeaponOnBoard(int index) {
        if (targetSquare != null) {
            try {
                targetSquare.setWeaponToGrabIndex(index);
            } catch (NullPointerException e) {
                //TODO stampa a video messaggio di errore
            }
        }
    }


    @Override
    public void selectWeaponOfMine(int index) {
        if (targetSquare != null)
            targetSquare.setWeaponToDropIndex(index);
    }


    @Override
    public void selectSquare(Square targetSquare){
        if (!targetSquare.isEmpty()) {
            this.targetSquare = targetSquare;
            targetSquare.resetWeaponIndexes();
        }
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
    public boolean undo() {
        controller.getGame().setTargetableSquares(targetableSquares, false);
        targetSquare.resetWeaponIndexes();
        undo = true;
        return true;
    }

    @Override
    public boolean ok() {
        Pc currPc = controller.getCurrPc();
        try {
            currPc.collect();
            currPc.moveTo(targetSquare);
        } catch (EmptySquareException e) {
            //TODO print error
            return false;
        } catch (IllegalStateException e) {
            //TODO print what the player should select
            return false;
        } catch (NotEnoughAmmoException e) {
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
