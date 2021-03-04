package server.controller.states;

import common.enums.ControllerMethodsEnum;
import server.controller.Controller;
import common.exceptions.EmptySquareException;
import common.exceptions.NotEnoughAmmoException;
import server.controller.Player;
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
        targetableSquares = new HashSet<>();
        setTargetableToValidSquares(controller.getCurrPc());
        controller.ackCurrent("(Use the command " + ControllerMethodsEnum.CHOOSE_SQUARE.getUsage() + ". Type \"h\" for details on all available commands)");
    }


    /**
     * Calls the setWeaponToGrabIndex method of the pre-selected Square
     * @see Square
     * @param index the index to pass to the method
     */
    @Override
    public void selectWeaponOnBoard(Player p, int index) {
        if (targetSquare != null) {
            try {
                targetSquare.setWeaponToGrabIndex(index);
                controller.ackCurrent(System.lineSeparator() + "Mmh.. good choice! (\"ok\" to confirm your choice)");
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
    public void selectWeaponOfMine(Player p, int index) {
        if (targetSquare != null && Arrays.stream(controller
                .getCurrPlayer()
                .getPc()
                .getWeapons())
                .filter(Objects::nonNull)
                .count() == 3 )
            targetSquare.setWeaponToDropIndex(index);
        else {
            controller.ackCurrent(System.lineSeparator() + "You'd better use that weapon to hurt people");
        }
    }


    /**
     * Select a square
     * @param row of the selected square
     * @param column of the selected square
     */
    @Override
    public void selectSquare(Player p, int row, int column){
        Square s = controller.getGame().getSquare(row, column);
        if (s != null && !s.isEmpty() && targetableSquares.contains(s)) {
            this.targetSquare = s;
            targetSquare.resetWeaponIndexes();
            controller.ackCurrent(targetSquare.isSpawnPoint()
                    ? System.lineSeparator() + "Ohw! A spawn point, choose a weapon and use it to hurt people:" + targetSquare.itemToString() + System.lineSeparator() + "(Use the command " + ControllerMethodsEnum.CHOOSE_WEAPON_ON_SPAWN_POINT.getUsage() + ". Type \"h\" for details on all available commands)"
                    : System.lineSeparator() + "I see you a strategist. There is an ammo tile on that square. Grab it to earn:" + targetSquare.itemToString() + System.lineSeparator() + "(\"ok\" to confirm your choice)");
        }
    }

    
    /**
     * Select a powerUp to use as an ammo when collecting a weapon
     * @param index the powerup card index
     */
    @Override
    public void selectPowerUp(Player p, int index) {
        PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
        if (powerUp != null && !powerUp.isSelectedAsAmmo()) {
            powerUp.setSelectedAsAmmo(true);
            controller.ackCurrent(System.lineSeparator() + "You will lose a " + powerUp.toString() + " instead of paying one " + powerUp.getColour() + " ammo (\"ok\" to confirm your choice)");
        }
    }

    
    /**
     * Prepare an arrayList containing the Squares reachable using the collect action.
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
     * Checks whether the action just done is undoable
     * @return true if the action is undoable
     * @param p the player for which we need to check the undoability of the action
     */
    @Override
    public boolean isUndoable(Player p) {
        controller.getGame().setTargetableSquares(targetableSquares, false);
        controller.getCurrPc().resetPowerUpAsAmmo();
        if (targetSquare != null) {
            targetSquare.resetWeaponIndexes();
        }
        undo = true;
        return true;
    }

    
    /**
     * Confirm the choice
     * @return true if everything is fine or false if there is any problem with the selected action
     */
    @Override
    public boolean ok(Player p) {
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
                controller.ackCurrent(System.lineSeparator() + "There's nothing to grab here!");
                return false;
            } catch (NotEnoughAmmoException e) {
                controller.ackCurrent(System.lineSeparator() + "Not enough ammo. You had to grab them first! Do I have to teach you how to play?");
                return false;
            } catch (Exception e){
                controller.ackCurrent(e.getMessage());
                return false;
            }
        }
        return false;
    }


    /**
     * Forces a player to pass
     * @param p the player forced to pass
     * @return the new state of the player
     */
    @Override
    public State forcePass(Player p) {
        controller.getGame().setTargetableSquares(targetableSquares, false);
        controller.getCurrPc().resetPowerUpAsAmmo();
        if (targetSquare != null) {
            targetSquare.resetWeaponIndexes();
        }
        controller.resetRemainingActions();
        controller.getCurrPc().resetPowerUpAsAmmo();
        controller.ackCurrent(System.lineSeparator() + "Too slow. You will skip the turn");
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.FIRST_TURN_STATE);
    }


    /**
     * Complete the transition to the next state
     * @return EndTurnState if this was the 2nd action of the player, StartTurnState otherwise
     * (2 action are allowed per turn)
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
