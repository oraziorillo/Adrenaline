package controller.states;

import controller.Controller;
import model.Pc;
import model.Tile;

public class RunAroundState extends State{

    RunAroundState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean execute(Pc currPc, Tile targetSquare){
        move(currPc, targetSquare);
        return true;
    }


    @Override
    public void setTargetables(Pc referencePc){
        int maxDistance = controller.isFinalFrenzy() ? 4 : 3;
        controller.getGame().setTargetables(maxDistance, referencePc.getCurrTile());
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
