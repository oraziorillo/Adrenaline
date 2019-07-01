package server.controller.states;

import common.enums.PcColourEnum;
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
    private int effectIndex;
    private int actionIndex;
    private List<Effect> effectsToApply;
    private Effect currEffect;
    private Action currAction;
    private Square squareToMemorize;
    private LinkedList<Pc> shotTargets;
    private LinkedList<Pc> targetsShotTwice;    //per la machine gun....
    private Set<Square> targetableSquares;

    TargetSelectionState(Controller controller) {
        super(controller);
        this.shotTargets = new LinkedList<>();
        this.targetsShotTwice = new LinkedList<>();
        this.effectsToApply = controller.getCurrWeapon().getEffectsToApply();
        this.currEffect = effectsToApply.get(effectIndex);
        this.currAction = currEffect.getActionAtIndex(actionIndex);
        setAction();
    }


    private boolean hasNextAction(){
        return actionIndex != currEffect.getActions().size() - 1 ||
                effectIndex != effectsToApply.size() - 1;
    }


    private void nextAction(){
        if(actionIndex == currEffect.getActions().size() -1){
            nextEffect();
        } else {
            actionIndex++;
            currAction = currEffect.getActionAtIndex(actionIndex);
            setAction();
        }
    }


    private void nextEffect() {
        controller.getCurrPc().payAmmo(currEffect.getCost());

        LinkedList<Pc> justShotPc = new LinkedList<>(currEffect.execute(controller.getCurrPc()));
        if (!justShotPc.isEmpty()) {
            List<Player> players = controller.getPlayers();
            for (Pc pc: justShotPc) {
                players.forEach(player -> {
                    if (player.getPc() == pc){
                        player.setAttacked(players.indexOf(pc));
                        //TODO qui dovremmo notificare tutti i player attaccati per il powerUp
                    }
                });
            }

            for (Pc pc : justShotPc) {
                if (shotTargets.contains(pc))
                    targetsShotTwice.add(pc);
                else
                    shotTargets.add(pc);
            }
        }
        effectIndex++;
        actionIndex = 0;
        currEffect = effectsToApply.get(effectIndex);
        currAction = currEffect.getActionAtIndex(actionIndex);

        setAction();
    }


    private void setAction(){
        if (targetableSquares != null) {
            controller.getGame().setTargetableSquares(targetableSquares, false);
        }
        if (!currAction.isParameterized()) {
            if (currAction.needsOldSquare())
                currAction.selectSquare(squareToMemorize);
            else if (controller.getCurrWeapon().isChained()) {          //per la shockWave
                for (Square square : currAction.validSquares(controller.getCurrPc().getCurrSquare())) {
                    square.getPcs().forEach(pc -> currAction.selectPc(pc));
                }
            }
            else
                currAction.selectSquare(controller.getCurrPc().getCurrSquare());    //tractor beam  e electroscyte
            ok();   //qui bisogna aggiungere un nextState??
            return;
        }
        if (controller.getCurrWeapon().isChained()) {
            if (squareToMemorize != null)
                setTargettableSquares(squareToMemorize);
            else if (shotTargets != null)
                setTargetableToValidSquares(shotTargets.getLast());
        }
        else
            setTargetableToValidSquares(controller.getCurrPc());
    }


    private void setDeadPlayers(){
        controller.getPlayers().stream().forEach(player -> {
            if (player.getPc().getDamageTrack()[10] != null)
                controller.addDeadPlayer(player);
        });
    }


    @Override
    void setTargetableToValidSquares(Pc referencePc) {
        if (!currAction.isAdditionalDamage() && !currAction.isExclusiveForOldTargets())       //questo controllo è da verificare
            setTargettableSquares(referencePc.getCurrSquare());
    }


    private void setTargettableSquares(Square square) {
        targetableSquares = currAction.validSquares(square);
        if (validateSquares(targetableSquares))
            controller.getGame().setTargetableSquares(targetableSquares, true);
        else if (undo())
            controller.getCurrPlayer().undo();
        else if (!controller.getCurrWeapon().isChained() && effectIndex != effectsToApply.size() - 1) {
            if (currAction.isOptional())
                skipAction();
            else
                nextEffect();       //da rivedere
        } else
            controller.getCurrPlayer().setCurrState(nextState());
            //TODO display the list of valid Targets
    }



    private boolean validateSquares(Set<Square> targetables){
        if (targetables.isEmpty())
            return false;
        if (currAction.isSelfMovement())
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
            if (controller.getCurrWeapon().isChained() && effectIndex == 0){            //per il vortex
                for (Effect effect: effectsToApply) {
                    for (Action action: effect.getActions()) {
                        action.selectSquare(s);
                    }
                }
                squareToMemorize = s;
                nextAction();
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
        if (targetPc != null && targetPc.getCurrSquare().isTargetable() && controller.getCurrPc() != targetPc) {    // da rivedere
            if ((!currEffect.isOriented() || directionSelected) && !currAction.isExplosive()) {
                if (currEffect.memorizeTargetSquare() && squareToMemorize == null) {
                    squareToMemorize = targetPc.getCurrSquare();
                    if (controller.getCurrWeapon().isChained()) {           //per il rocket launcher
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
                        if (targetsShotTwice.contains(targetPc)) {
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


    @Override
    public void selectDirection(CardinalDirectionEnum direction) {
        if(currEffect.isOriented()) {
            currEffect.assignDirection(direction);
            controller.getGame().setTargetableSquares(targetableSquares, false);
            setTargetableToValidSquares(controller.getCurrPc());
            directionSelected = true;
        }
    }


    @Override
    public boolean skipAction() {
        if (currAction.isOptional()) {
            if (currAction.isNecessaryForNextAction()){
                if (effectIndex == effectsToApply.size() - 1)
                    return true;
                else
                    nextEffect();
                return false;
            }
            if (!hasNextAction()) {
                return true;
            }
            nextAction();
        }
        return false;
    }


    @Override
    public boolean undo(){
        if (controller.getCurrWeapon().getEffectsToApply().get(0) == currEffect){
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
                nextAction();
                return false;
            } else {
                controller.getCurrPc().payAmmo(currEffect.getCost());
                currEffect.execute(controller.getCurrPc());
                controller.getCurrWeapon().reset();
                controller.getGame().setTargetableSquares(targetableSquares, false);
                setDeadPlayers();
                return true;
            }
        }
        return false;
    }


    @Override
    public State nextState() {
        controller.setCurrWeapon(null);
        if (undo)
            return new StartTurnState(controller);
        controller.getCurrWeapon().setLoaded(false);
        controller.decreaseRemainingActions();
        for (PowerUpCard p: controller.getCurrPc().getPowerUps()) {
            if (p.getAction().isAdditionalDamage() &&
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
