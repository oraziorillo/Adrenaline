package server.controller.states;

import common.enums.PcColourEnum;
import common.events.requests.Request;
import server.controller.Controller;
import common.enums.CardinalDirectionEnum;
import server.controller.Player;
import server.model.Effect;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.actions.Action;
import server.model.squares.Square;

import java.util.*;
import java.util.stream.Collectors;

public class TargetSelectionState extends State {

    private boolean undo;
    private boolean directionSelected;
    private boolean moved;
    private int effectIndex;
    private int actionIndex;
    private List<Effect> effectsToApply;
    private Effect currEffect;
    private Action currAction;
    private Square squareToMemorize;
    private LinkedList<Pc> shotTargets;
    private LinkedList<Pc> targetsShotTwice;
    private Set<Square> targetableSquares;


    TargetSelectionState(Controller controller, boolean hasMoved) {
        super(controller);
        this.moved = hasMoved;
        this.targetableSquares = new HashSet<>();
        this.shotTargets = new LinkedList<>();
        this.targetsShotTwice = new LinkedList<>();
        this.effectsToApply = controller.getCurrWeapon().getEffectsToApply();
        this.currEffect = effectsToApply.get(effectIndex);
        this.currAction = currEffect.getActionAtIndex(actionIndex);
        setAction();
        checkIfOriented();
    }


    private boolean hasNextAction(){
        return actionIndex != currEffect.getActions().size() - 1 ||
                effectIndex != effectsToApply.size() - 1;
    }


    private boolean nextAction(){
        if(actionIndex == currEffect.getActions().size() -1){
            effectIndex++;
            actionIndex = 0;
            executeEffect();
            currEffect = effectsToApply.get(effectIndex);
        } else {
            actionIndex++;
        }
        currAction = currEffect.getActionAtIndex(actionIndex);
        setAction();
        checkIfOriented();
        if (!currAction.isParameterized() && !hasNextAction()){
            executeEffect();
            return true;
        }
        return false;
    }


    private void executeEffect() {
        controller.getCurrPc().payAmmo(currEffect.getCost());

        LinkedList<Pc> justShotPc = new LinkedList<>(currEffect.execute(controller.getCurrPc()));
        if (!justShotPc.isEmpty()) {
            for (Pc pc : justShotPc) {
                if (shotTargets.contains(pc))
                    targetsShotTwice.add(pc);
                else
                    shotTargets.add(pc);
            }
        }
    }


    private void setAction(){
        if (targetableSquares != null && currEffect.getActions().size() < 4) {
            controller.getGame().setTargetableSquares(targetableSquares, false);
            targetableSquares = new HashSet<>();
        }
        if (!currAction.isParameterized()) {
            if (currAction.needsOldSquare())
                currAction.selectSquare(squareToMemorize);
            else if (controller.getCurrWeapon().isChained()) {
                for (Square square : currAction.validSquares(controller.getCurrPc().getCurrSquare())) {
                    square.getPcs().forEach(pc -> currAction.selectPc(pc));
                }
            }
            else
                currAction.selectSquare(controller.getCurrPc().getCurrSquare());
            return;
        }
        if (controller.getCurrWeapon().isChained() && !currEffect.isAsynchronous()) {
            if (squareToMemorize != null) {
                setTargettableSquares(squareToMemorize);
                squareToMemorize = null;
            }
            else if (shotTargets != null && !shotTargets.isEmpty())
                setTargetableToValidSquares(shotTargets.getLast());
            else
                setTargetableToValidSquares(controller.getCurrPc());
        }
        else
            setTargetableToValidSquares(controller.getCurrPc());
    }


    private void setDeadPlayers(){
        controller.getPlayers().stream().forEach(player -> {
            if (player.getPc().getDamageTrack()[2] != null)         //TODO DA CAMBIARE
                controller.addDeadPlayer(player);
        });
    }


    @Override
    void setTargetableToValidSquares(Pc referencePc) {
        if (!currAction.isAdditionalDamage() && !currAction.isExclusiveForOldTargets())
            setTargettableSquares(referencePc.getCurrSquare());
    }


    private void setTargettableSquares(Square square) {
        if (!targetableSquares.isEmpty() && currEffect.getActions().size() == 4 && currEffect.getActions().indexOf(currAction) != 2){
            return;
        } else {
            controller.getGame().setTargetableSquares(targetableSquares, false);
            targetableSquares = currAction.validSquares(square);
            if (validateSquares(targetableSquares))
                controller.getGame().setTargetableSquares(targetableSquares, true);
            else if (undo())
                controller.getCurrPlayer().undo();
            else if (!controller.getCurrWeapon().isChained() && effectIndex != effectsToApply.size() - 1) {
                if (currAction.isOptional())
                    skip();
                else
                    executeEffect();
            } else
                controller.getCurrPlayer().setCurrState(nextState());
        }
    }



    private boolean validateSquares(Set<Square> targetables){
        if (targetables.isEmpty())
            return false;
        if (currAction.isMovement())
            return true;
        Set<Square> squaresWithPcs = targetableSquares.stream().
                filter(square -> !square.getPcs().isEmpty()).
                collect(Collectors.toSet());
        Set<Pc> validTargets = new HashSet<>();
        for (Square s: squaresWithPcs)
            validTargets.addAll(s.getPcs());
        return !validTargets.isEmpty() && (validTargets.size() != 1 || !validTargets.contains( controller.getCurrPc() ));
    }


    @Override
    public void selectSquare(int row, int column) {
        Square s = controller.getGame().getSquare(row, column);
        if (s != null && s.isTargetable()) {
            if (controller.getCurrWeapon().isChained() && effectIndex == 0 && !currAction.isMovement()){            //per il vortex
                for (Effect effect: effectsToApply) {
                    for (Action action: effect.getActions()) {
                        if (!action.isSelfMovement() )
                            action.selectSquare(s);
                    }
                }
                if (hasNextAction() && !currEffect.memorizeTargetSquare() && currEffect.hasSameTarget()) {
                    squareToMemorize = s;
                    nextAction();
                }
            }
            else if ((!currEffect.isOriented() || directionSelected) && s.isTargetable()) {
                currAction.selectSquare(s);
            }
        }
    }


    public void selectTarget(PcColourEnum targetPcColour) {
        Pc targetPc = controller.getPlayers().stream()
                .filter(player -> player.getPc().getColour() == targetPcColour)
                .findFirst().map(Player::getPc).orElse(null);
        if (targetPc != null && controller.getCurrPc() != targetPc && (targetPc.getCurrSquare().isTargetable() || currAction.isAdditionalDamage() || currAction.isExclusiveForOldTargets())) {    // da rivedere
            if ((!currEffect.isOriented() || directionSelected) && !currAction.isExplosive()) {
                if (currEffect.memorizeTargetSquare() && squareToMemorize == null) {
                    squareToMemorize = targetPc.getCurrSquare();
                    if (controller.getCurrWeapon().isChained()) {
                        for (Effect effect : effectsToApply) {
                            for (Action action : effect.getActions()) {
                                if (action.isExplosive())
                                    action.selectSquare(targetPc.getCurrSquare());
                            }
                        }
                    }
                }
                if (currEffect.hasSameTarget()) {
                    currEffect.getActions().forEach(a -> a.selectPc(targetPc));
                } else if (currAction.isAdditionalDamage()) {
                    if (currAction.isExclusiveForOldTargets()) {
                        if (!targetsShotTwice.contains(targetPc)) {
                            currAction.selectPc(targetPc);
                        }
                    } else if (shotTargets.contains(targetPc)) {
                        currAction.selectPc(targetPc);
                    }
                } else if (currAction.isExclusiveForOldTargets()) {
                    if (!shotTargets.contains(targetPc)) {
                        currAction.selectPc(targetPc);
                    }
                } else {

                    currAction.selectPc(targetPc);
                }
            }
        }
    }


    private void checkIfOriented() {
        if(currEffect.isOriented() && !directionSelected) {
            List<String> possibilities = new ArrayList<>();
            possibilities.add("N");
            possibilities.add("S");
            possibilities.add("E");
            possibilities.add("W");
            controller.sendNonBlockingRequest(new Request("\nChoose a cardinal direction: (N/S/E/W)", possibilities));
        }
    }


    @Override
    public void selectDirection(CardinalDirectionEnum direction) {
        if(currEffect.isOriented() && !directionSelected) {
            currEffect.assignDirection(direction);
            controller.getGame().setTargetableSquares(targetableSquares, false);
            setTargetableToValidSquares(controller.getCurrPc());
            directionSelected = true;
        }
    }


    @Override
    public boolean skip() {
        if (currAction.isOptional()) {
            if (currAction.isNecessaryForNextAction()){
                if (effectIndex == effectsToApply.size() - 1)
                    return true;
                else
                    executeEffect();
                return false;
            }
            if (!hasNextAction()) {
                executeEffect();
                return true;
            }
            nextAction();
        }
        return false;
    }


    @Override
    public boolean undo(){
        if (controller.getCurrWeapon().getEffectsToApply().get(0) == currEffect && !moved){
            controller.getCurrWeapon().reset();
            undo = true;
            return true;
        }
        return false;
    }


    @Override
    public boolean ok() {
        if (currEffect.isOriented() && !directionSelected)
            return false;
        if (currAction.isComplete()) {
            if (hasNextAction()) {
                return nextAction();
            } else {
                executeEffect();
                controller.getCurrWeapon().reset();
                controller.getGame().setTargetableSquares(targetableSquares, false);
                setDeadPlayers();
                return true;
            }
        }
        return false;
    }


    @Override
    public State forcePass() {
        controller.getSquaresToRefill().forEach(Square::refill);
        controller.resetSquaresToRefill();
        controller.resetRemainingActions();
        controller.getGame().setTargetableSquares(targetableSquares, false);
        controller.nextTurn();
        if (effectIndex > 0)
            controller.getCurrWeapon().setLoaded(false);
        controller.setCurrWeapon(null);
        return new InactiveState(controller, InactiveState.START_TURN_STATE);
    }


    @Override
    public State nextState() {
        if (undo) {
            controller.setCurrWeapon(null);
            return new StartTurnState(controller);
        }
        controller.getCurrWeapon().setLoaded(false);
        controller.setCurrWeapon(null);
        controller.decreaseRemainingActions();
        for (PowerUpCard p: controller.getCurrPc().getPowerUps()) {
            if (p.getAction().isAdditionalDamage() &&
                    controller.getCurrPc().hasAtLeastOneAvailableAmmo())
                return new UseTargetingScopeState(controller, shotTargets);
        }
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        } else
            return new StartTurnState(controller);
    }
}
