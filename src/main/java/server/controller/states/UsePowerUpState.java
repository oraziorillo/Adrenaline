package server.controller.states;

import server.controller.Controller;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.actions.Action;
import server.model.squares.Square;

import java.util.List;
import java.util.Set;

public class UsePowerUpState extends State {

    private boolean undo = false;
    private PowerUpCard currPowerUp;
    private Action currAction;
    private List<Pc> targetablePcs;
    private Set<Square> targetableSquares;

    UsePowerUpState(Controller controller, List<Pc> targetables) {
        super(controller);
        this.targetablePcs = targetables;
    }


    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp;
        Action powerUpAction;
        try {
            powerUp = controller.getCurrPc().getPowerUpCard(index);
            powerUpAction = powerUp.getAction();
            if (powerUp.getAction().isParameterized()) {
                currPowerUp = powerUp;
                currAction = powerUpAction;
                if (!currAction.isAdditionalDamage())
                    setTargetableToValidSquares(controller.getCurrPc());
                else {
                    //TODO stampare a video la lista di targetablesPcs
                }
            } else {
                //TODO stampa a video il messaggio non può usare quel power up in quel momento
            }
        }
        catch (IllegalArgumentException e){
            //TODO stampa a video un errore poichè ha selezionato un powerUp in una casella vuota
        }
    }


    @Override
    void setTargetableToValidSquares(Pc referencePc) {
        if(!targetableSquares.isEmpty())
            controller.getGame().setTargetableSquares(targetableSquares, false);
        targetableSquares = currAction.validSquares(referencePc.getCurrSquare());
        controller.getGame().setTargetableSquares(targetableSquares, true);
    }


    @Override
    public void selectTarget(Pc targetPc) {
        if (targetPc.getCurrSquare().isTargetable()){
            if (currAction.isAdditionalDamage()){
                if (targetablePcs.contains(targetPc))
                    currAction.selectPc(targetPc);
            } else
                currAction.selectPc(targetPc);
        }
    }


    @Override
    public void selectSquare(int row, int column) {
        Square s = controller.getGame().getSquare(row, column);
        if (s != null && s.isTargetable())
            currAction.selectSquare(s);
    }


    @Override
    public boolean undo() {
        controller.getGame().setTargetableSquares(targetableSquares, false);
        currAction.resetAction();
        undo = true;
        return true;
    }


    @Override
    public boolean ok() {
        if (currAction.isComplete()) {
            currPowerUp.useAction(controller.getCurrPc());
            controller.getCurrPc().discardPowerUp(currPowerUp);
            //TODO bisogna aggiungere il pagamento dell'ammocard nel caso del mirino
            return true;
        }
        return false;
    }


    @Override
    public State nextState() {
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        } else
            return new StartTurnState(controller);
    }
}
