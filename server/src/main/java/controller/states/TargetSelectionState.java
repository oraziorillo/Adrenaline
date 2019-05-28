package controller.states;

import controller.Controller;
import enums.CardinalDirectionEnum;
import model.Pc;
import model.WeaponEffect;
import model.actions.Action;
import model.powerUps.PowerUpCard;
import model.squares.Square;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TargetSelectionState extends State {

    private boolean directionSelected;
    private int effectIndex, actionIndex;
    private LinkedList<WeaponEffect> effectsToApply;
    private WeaponEffect currEffect;
    private Action currAction;
    private Set<Pc> shotTargets;
    private HashSet<Square> targetableSquares;

    TargetSelectionState(Controller controller) {
        super(controller);
        this.shotTargets = new HashSet<>();
        this.effectsToApply = controller.getCurrWeapon().getEffectsToApply();
        this.currEffect = effectsToApply.get(effectIndex);
        this.currAction = currEffect.getActionAtIndex(actionIndex);
        setTargetableToValidSquares(controller.getCurrPc());
    }


    private boolean hasNext(){
        return actionIndex != currEffect.getActions().size() - 1 ||
                effectIndex != effectsToApply.size() - 1;
    }


    private void next(){
        if(actionIndex == currEffect.getActions().size() -1){
            currEffect.execute(controller.getCurrPc());
            effectIndex++;
            actionIndex = 0;
            currEffect = effectsToApply.get(effectIndex);
        } else
            actionIndex++;
        controller.getGame().setTargetableSquares(targetableSquares, false);
        setTargetableToValidSquares(controller.getCurrPc());
        currAction = currEffect.getActionAtIndex(actionIndex);
    }


    @Override
    void setTargetableToValidSquares(Pc referencePc) {
        if (!currAction.isAdditionalDamage() && !currAction.isExclusiveForOldTargets()) {
            targetableSquares = currAction.validSquares(referencePc.getCurrSquare());
            //TODO se targetableSquares è vuota, l'effetto non può essere eseguito
            controller.getGame().setTargetableSquares(targetableSquares, true);
        } else {
            //TODO display the list of valid Targets
        }
    }

    @Override
    public void selectSquare(Square targetSquare) {
        if (targetSquare.isTargetable()) {
            if ((!currEffect.isOriented() || directionSelected) && targetSquare.isTargetable()) {
                currAction.selectSquare(targetSquare);
            }
        }
    }


    @Override
    public void selectTarget(Pc targetPc) {
        if (targetPc.getCurrSquare().isTargetable()) {
            if ((!currEffect.isOriented() || directionSelected) && !currAction.isExplosive()) {
                if (currEffect.hasOnlyOneTarget()) {
                    currEffect.getActions().forEach(a -> a.selectPc(targetPc));
                } else if (currAction.isAdditionalDamage()) {
                    if (shotTargets.contains(targetPc))
                        currAction.selectPc(targetPc);
                } else if (currAction.isExclusiveForOldTargets()) {
                    if (!shotTargets.contains(targetPc))
                        currAction.selectPc(targetPc);
                } else
                    currAction.selectPc(targetPc);
            }
        }
    }


    @Override
    public void selectDirection(CardinalDirectionEnum direction) {
        if(currEffect.isOriented()) {
            currEffect.assignDirection(direction);
            directionSelected = true;
        }
    }


    @Override
    public boolean skipAction() {
        if (currAction.isOptional()) {
            if (!hasNext()) {
                return true;
            }
            next();
        }
        return false;
    }


    @Override
    public boolean ok() {
        if (currEffect.isOriented() && !directionSelected)
            return false;
        if (currAction.isComplete()) {
            if (hasNext()) {
                next();
                return false;
            } else {
                currEffect.execute(controller.getCurrPc());
                controller.getCurrWeapon().clear();
                controller.getGame().setTargetableSquares(targetableSquares, false);
                return true;
            }
        }
        return false;
    }


    @Override
    public State nextState() {
        for (PowerUpCard p: controller.getCurrPc().getPowerUps()) {
            if (p.getEffect().getActionAtIndex(actionIndex).isAdditionalDamage())
        }

        controller.decreaseRemainingActions();
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        } else
            return new StartTurnState(controller);
    }
}
