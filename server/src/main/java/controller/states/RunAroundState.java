package controller.states;

import controller.Controller;
import model.Pc;
import model.squares.Square;

import java.util.HashSet;

public class RunAroundState extends State{


    private Square targetSquare;


    RunAroundState(Controller controller) {
        super(controller);
        setTargetableToValidSquares(controller.getCurrPc());
    }


    @Override
    public void selectSquare(Square targetSquare){
        if (targetSquare.isTargetable())
            this.targetSquare = targetSquare;
    }


    @Override
    void setTargetableToValidSquares(Pc referencePc){
        int maxDistance = controller.isFinalFrenzy() ? 4 : 3;
        HashSet<Square> targetableSquares = referencePc.getCurrSquare().atDistance(maxDistance);
        controller.getGame().setTargetableSquares(targetableSquares);
    }


    @Override
    public boolean ok() {
        if (targetSquare != null) {
            controller.getCurrPc().moveTo(targetSquare);
            controller.getGame().resetTargetableSquares();
            return true;
        }
        return false;
    }


    @Override
    public State nextState() {
        controller.decreaseRemainingActions();
        if(controller.getRemainingActions() == 0){
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        }
        else
            return new StartTurnState(controller);
    }
}
