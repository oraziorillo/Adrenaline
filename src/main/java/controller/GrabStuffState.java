package controller;

import model.Pc;
import model.Tile;

public class GrabStuffState extends State{

    private boolean executed;

    GrabStuffState(Controller controller) {
        super(controller);
        this.executed = false;
    }

    @Override
    public boolean execute(Pc currPc, Tile targetSquare){
        if (targetSquare.isEmpty())
            return false;
        move(currPc, targetSquare);
        if (!controller.getGame().getSpawnTiles().contains(targetSquare)) {
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
        controller.getGame().setTargetables(maxDistance, referencePc.getCurrTile());
    }


    @Override
    public void nextState() {
        if(executed) {
            executed = false;
            controller.decreaseRemainingActions();
            if (controller.getRemainingActions() == 0) {
                controller.resetRemainingActions();
                controller.setCurrState(controller.endTurn);
            } else
                controller.setCurrState(controller.startTurnState);
        } else {
            controller.setCurrState(controller.grabWeaponState);
        }
    }
}
