package controller;

import model.Pc;
import model.Tile;

public class RunAroundState extends State{

    RunAroundState(Controller controller) {
        super(controller);
    }

    @Override
    public boolean executeOnTile(Pc currPc, Tile targetSquare){
        move(currPc, targetSquare);
        return true;
    }


    @Override
    public void setTargetables(Pc referencePc){
        int maxDistance = controller.isFinalFrenzy() ? 4 : 3;
        controller.getGame().setTargetables(maxDistance, referencePc.getCurrTile());
    }


    @Override
    public void nextState() {
        decreaseRemainingActions();
        if(getRemainingActions() == 0){
            resetRemainingActions();
            controller.setCurrState(controller.endTurn);
        }
        else
            controller.setCurrState(controller.startTurnState);
    }
}
