package controller.states;

import controller.Controller;
import model.Pc;
import model.squares.Square;

public class RunAroundState extends State{

    private Square targetSquare;

    RunAroundState(Controller controller) {
        super(controller);

    }

    @Override
    public void selectSquare(Square targetSquare){
        this.targetSquare = targetSquare;
    }


    @Override
    public boolean ok() {
        controller.getCurrPc().moveTo(targetSquare);
        return true;
    }

    @Override
    public void setTargetableSquares(Pc referencePc){
        int maxDistance = controller.isFinalFrenzy() ? 4 : 3;
        controller.getGame().setTargetables(maxDistance, referencePc.getCurrSquare());
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
