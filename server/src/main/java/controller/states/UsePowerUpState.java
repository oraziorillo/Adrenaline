package controller.states;

import controller.Controller;
import model.Pc;
import model.actions.Action;
import model.PowerUpCard;
import model.squares.Square;
import java.util.Set;

public class UsePowerUpState extends State {

    private final int actionIndex;
    private boolean undo = false;
    private PowerUpCard currPowerUp;
    private Action currAction;
    private Set<Pc> targetablePcs;
    private Set<Square> targetableSquares;

    UsePowerUpState(Controller controller, Set<Pc> targetables) {
        super(controller);
        actionIndex = 0;
        this.targetablePcs = targetables;
    }


    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp;
        Action powerUpAction;
        try {
            powerUp = controller.getCurrPc().getPowerUpCard(index);
            powerUpAction = powerUp.getEffect().getActionAtIndex(actionIndex);
            if (!powerUp.getEffect().isAsynchronous()) {
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
    public void selectSquare(Square targetSquare) {
        if (targetSquare.isTargetable())
            currAction.selectSquare(targetSquare);
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
            currPowerUp.useEffect(controller.getCurrPc());
            controller.getCurrPc().removePowerUp(currPowerUp);
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
