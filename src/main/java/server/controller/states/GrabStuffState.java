package server.controller.states;

import server.controller.Controller;
import common.exceptions.EmptySquareException;
import common.exceptions.NotEnoughAmmoException;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.squares.Square;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * When the user chooses the "collect" action
 */
class GrabStuffState extends State{


    private boolean undo;
    private Square targetSquare;
    private Set<Square> targetableSquares;


    GrabStuffState(Controller controller) {
        super(controller);
        //controller.startTimer();
        targetableSquares = new HashSet<>();
        setTargetableToValidSquares(controller.getCurrPc());
    }


    /**
     * Calls the setWeaponToGrabIndex method of the pre-selected Square
     * @see Square
     * @param index the index to pass to the method
     */
    @Override
    public void selectWeaponOnBoard(int index) {
        if (targetSquare != null) {
            try {
                targetSquare.setWeaponToGrabIndex(index);
                controller.ackCurrent("\nMmh.. good choice!");
            } catch (NullPointerException e) {
                controller.ackCurrent(e.getMessage());
            }
        }
    }


    /**
     * Calls the setWeaponToDropIndex on the pre-selected Square
     * @param index the index to pass to the method
     * @see Square
     */
    @Override
    public void selectWeaponOfMine(int index) {
        if (targetSquare != null && Arrays.stream(controller
                .getCurrPlayer()
                .getPc()
                .getWeapons())
                .filter(Objects::nonNull)
                .count() == 3 )
            targetSquare.setWeaponToDropIndex(index);
        else {
            controller.ackCurrent("\nYou'd better use that weapon to hurt people");
        }
    }


    /**
     * Prepares a square for doing something
     * @param row of the selected square
     * @param column of the selected square
     */
    @Override
    public void selectSquare(int row, int column){
        Square s = controller.getGame().getSquare(row, column);
        if (s != null && !s.isEmpty() && targetableSquares.contains(s)) {
            this.targetSquare = s;
            targetSquare.resetWeaponIndexes();
            controller.ackCurrent(targetSquare.isSpawnPoint()
                    ? "\nOhw! A spawn point, choose a weapon and use it to hurt people:" + targetSquare.itemToString()
                    : "\nI see you a strategist. There is an ammo tile on that square. Grab it to earn:" + targetSquare.itemToString());
        }
    }

    
    /**
     * Selects a powerUp to use as an ammo when collecting a weapon
     * @param index the powerup card index
     */
    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
        if (powerUp != null && !powerUp.isSelectedAsAmmo()) {
            powerUp.setSelectedAsAmmo(true);
            controller.ackCurrent("\nYou will lose a " + powerUp.toString() + " instead of paying one " + powerUp.getColour() + " ammo");
        }
    }

    
    /**
     * Prepares an arrayList containing the Squares reachable with the collect action.
     * Sets it up in Game,
     * @see server.model.Game
     * @param referencePc the pc using the action
     */
    @Override
    void setTargetableToValidSquares(Pc referencePc) {
        int maxDistance;
        if (!controller.isFinalFrenzy())
            maxDistance = (referencePc.getAdrenaline() < 1) ? 1 : 2;
        else {
            maxDistance = (controller.beforeFirstPlayer(controller.getCurrPlayerIndex())) ? 2 : 3;
        }
        targetableSquares = referencePc
                .getCurrSquare()
                .atDistance(maxDistance)
                .stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
        controller.getGame().setTargetableSquares(targetableSquares, true);
    }

    
    /**
     * undos the setup
     * @return true
     */
    @Override
    public boolean undo() {
        controller.getGame().setTargetableSquares(targetableSquares, false);
        controller.getCurrPc().resetPowerUpAsAmmo();
        if (targetSquare != null) {
            targetSquare.resetWeaponIndexes();
        }
        undo = true;
        return true;
    }

    
    /**
     * executes the setted up collect action
     * @return true
     */
    @Override
    public boolean ok() {
        if (targetSquare != null) {
            try {
                Pc currPc = controller.getCurrPc();
                currPc.collect(targetSquare);
                if (currPc.getCurrSquare() != targetSquare) {
                    currPc.moveTo(targetSquare);
                }
                controller.getGame().setTargetableSquares(targetableSquares, false);
                controller.addSquareToRefill(targetSquare);
                return true;
            } catch (EmptySquareException e) {
                controller.ackCurrent("\nThere's nothing to grab here!");
                return false;
            } catch (NotEnoughAmmoException e) {
                controller.ackCurrent("\nNot enough ammo. You had to grab them first! Do I have to teach you how to play?");
                return false;
            } catch (Exception e){
                controller.ackCurrent(e.getMessage());
                return false;
            }
        }
        return false;
    }


    @Override
    public State forcePass() {
        controller.getGame().setTargetableSquares(targetableSquares, false);
        controller.getCurrPc().resetPowerUpAsAmmo();
        if (targetSquare != null) {
            targetSquare.resetWeaponIndexes();
        }
        controller.resetRemainingActions();
        controller.getCurrPc().resetPowerUpAsAmmo();
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.FIRST_TURN_STATE);
    }


    /**
     * Transition
     * @return EndTurnState if this was the 2nd action of the player, StartTurnState else
     */
    @Override
    public State nextState() {
        if (undo)
            return new StartTurnState(controller);
        controller.decreaseRemainingActions();
        controller.getCurrPc().resetPowerUpAsAmmo();
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        } else
            return new StartTurnState(controller);
    }
}
