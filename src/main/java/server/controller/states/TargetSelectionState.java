package server.controller.states;

import server.controller.Controller;
import server.enums.CardinalDirectionEnum;
import server.model.Effect;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.actions.Action;
import server.model.squares.Square;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TargetSelectionState extends State {

    private boolean directionSelected;
    private int effectIndex;
    private int actionIndex;
    private List<Effect> effectsToApply;
    private Effect currEffect;
    private Action currAction;
    private List<Pc> shotTargets;
    private Set<Square> targetableSquares;

    TargetSelectionState(Controller controller) {
        super(controller);
        this.shotTargets = new LinkedList<>();
        this.effectsToApply = controller.getCurrWeapon().getEffectsToApply();
        this.currEffect = effectsToApply.get(effectIndex);
        this.currAction = currEffect.getActionAtIndex(actionIndex);
        setTargetableToValidSquares(controller.getCurrPc());
    }


    private boolean hasNextAction(){
        return actionIndex != currEffect.getActions().size() - 1 ||
                effectIndex != effectsToApply.size() - 1;
    }


    private void nextAction(){
        if(actionIndex == currEffect.getActions().size() -1){
            controller.getCurrPc().payAmmo(currEffect.getCost());
            currEffect.execute(controller.getCurrPc());
            effectIndex++;
            actionIndex = 0;
            currEffect = effectsToApply.get(effectIndex);
        } else
            actionIndex++;
        controller.getGame().setTargetableSquares(targetableSquares, false);
        if(!currAction.isParameterized()) {
            ok();
            return;
        }
        if(controller.getCurrWeapon().isChained())
            setTargetableToValidSquares(shotTargets.get(shotTargets.size() - 1));
        else
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
    public void selectSquare(int row, int column) {
        Square s = controller.getGame().getSquare(row, column);
        if (s != null && s.isTargetable()) {
            if ((!currEffect.isOriented() || directionSelected) && s.isTargetable()) {
                currAction.selectSquare(s);
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
            if (!hasNextAction()) {
                return true;
            }
            nextAction();
        }
        return false;
    }


    @Override
    public boolean ok() {
        if (currEffect.isOriented() && !directionSelected)
            return false;
        if (currAction.isComplete()) {
            if (hasNextAction()) {
                nextAction();
                return false;
            } else {
                controller.getCurrPc().payAmmo(currEffect.getCost());
                currEffect.execute(controller.getCurrPc());
                controller.getCurrWeapon().reset();
                controller.getGame().setTargetableSquares(targetableSquares, false);
                return true;
            }
        }
        return false;
    }


    @Override
    public State nextState() {
        controller.getCurrWeapon().setLoaded(false);
        controller.decreaseRemainingActions();
        controller.setCurrWeapon(null);
        for (PowerUpCard p: controller.getCurrPc().getPowerUps()) {
            if (p.getEffect().getActionAtIndex(actionIndex).isAdditionalDamage() &&
                    controller.getCurrPc().hasAtLeastOneAvailableAmmo())
                return new UsePowerUpState(controller, shotTargets);
        }
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        } else
            return new StartTurnState(controller);
    }
}
