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
    public boolean executeOnTile(Pc currPc, Tile targetSquare){
        //qui dobbiamo fare un controllo sul tile di destinazione, se è un spawnTile vuoto la mossa non può essere eseguita
        //se è un AmmoTile che ancora non è stato refillato, la mossa non può essere eseguita
        move(currPc, targetSquare);
        if (!controller.getGame().getSpawnTiles().contains(targetSquare)) {
            currPc.collectAmmos();
            executed = true;
            //sarebbe opportuno tener traccia di questo tile così che possa essere refillato a fine turno. Aggiungiamo array di tile nel controller?
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
            decreaseRemainingActions();
            if (getRemainingActions() == 0) {
                resetRemainingActions();
                controller.setCurrState(controller.endTurn);
            } else
                controller.setCurrState(controller.startTurnState);
        } else {
            controller.setCurrState(controller.collectWeaponState);
        }
    }
}
