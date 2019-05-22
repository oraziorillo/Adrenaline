package controller.states;

import controller.Controller;
import enums.CardinalDirectionEnum;
import model.Pc;
import model.WeaponEffect;
import model.actions.Action;
import model.squares.Square;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TargetSelectionState extends State {

    private boolean directionSelected;
    private int effectIndex, actionIndex;
    private LinkedList<WeaponEffect> effectsToApply;
    private WeaponEffect currEffect;
    private Action currAction;
    private Set<Pc> mainEffectTargets;
    private ArrayList<Pc> targetSelected;
    private Set<Square> targettableSquares;

    TargetSelectionState(Controller controller) {
        super(controller);
        this.mainEffectTargets = new HashSet<>();
        this.effectsToApply = controller.getCurrWeapon().getCurrEffect();
        this.currEffect = effectsToApply.get(effectIndex);
        this.currAction = currEffect.getActionAtIndex(actionIndex);
        this.targetSelected = new ArrayList<>();
        targettableSquares = currAction.validSquares(controller.getCurrPc().getCurrSquare());
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
         currAction = currEffect.getActionAtIndex(actionIndex);
    }



    @Override
    public void selectSquare(Square targetSquare) {
        if((!currEffect.isOriented() || directionSelected) && targettableSquares.contains(targetSquare)){
            currAction.selectSquare(targetSquare);
        }
    }


    @Override
    public void selectTarget(Pc targetPc) {
        if((!currEffect.isOriented() || directionSelected) && !currAction.isExplosive()){
            if(currEffect.hasSameTarget()){
                currEffect.getActions().forEach(a -> a.selectPc(targetPc));
            } else
                currAction.selectPc(targetPc);
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
        if (currAction.isComplete())
            controller.getGame().resetTargetableSquares(//TODO);
            if (hasNext()) {
                next();
                return false;
            } else {
                currEffect.execute(controller.getCurrPc());
                controller.getCurrWeapon().clear();
                return true;
            }
            return false;
    }

    @Override
    public State nextState() {
        return null;
    }
}
