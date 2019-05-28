package controller.states;

import controller.Controller;
import model.Pc;
import model.actions.Action;
import model.powerUps.PowerUpCard;
import model.squares.Square;

import java.util.HashSet;

public class UsePowerUpState extends State {

    final int actionIndex;
    boolean undo = false;
    private PowerUpCard currPowerUp;
    private Action currAction;
    private HashSet<Pc> targetablePcs;
    private HashSet<Square> targetableSquares;

    UsePowerUpState(Controller controller, HashSet<Pc> targetables) {
        super(controller);
        actionIndex = 0;
        this.targetablePcs = targetables;   //deve essere passato per copia??
    }


    @Override
    public void selectPowerUp(int index) {
        if (index >= 0 && index < 3) {
            PowerUpCard powerUp;
            Action powerUpAction;
            powerUp = controller.getCurrPc().getPowerUpCard(index);
            try {
                powerUpAction = powerUp.getEffect().getActionAtIndex(actionIndex);
                if (!powerUp.getEffect().isAsynchronous()) {
                    currPowerUp = powerUp;
                    currAction = powerUpAction;
                    if (!currAction.isAdditionalDamage())
                        setTargetableToValidSquares(controller.getCurrPc());
                    else {
                        //TODO definire il funzionamento di add.dam
                        //targetablePcs.forEach(pc -> currAction.);
                    }
                } else {
                    //TODO stampa a video il messaggio non può usare quel power up in quel momento
                }
            }
            catch (IllegalArgumentException){
                //TODO stampa a video un errore poichè ha selezionato un powerUp in una casella vuota
            }
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
        currAction.resetAction();
        undo = true;
        return true;
    }

    @Override
    public boolean ok() {
        if (currAction.isComplete())
            currAction.apply(controller.getCurrPc());
    }


    @Override
    public State nextState() {
        if(undo)
            return new StartTurnState(controller);
        //TODO
    }
}
