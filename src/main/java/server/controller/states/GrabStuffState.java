package server.controller.states;

import server.controller.Controller;
import server.exceptions.EmptySquareException;
import server.exceptions.NotEnoughAmmoException;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.squares.Square;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static common.Constants.MAX_WEAPONS_IN_HAND;

/**
 * When the user chooses the "collect" action
 */
class GrabStuffState extends State{


    private boolean undo;
    private Square targetSquare;
    private Set<Square> targetableSquares;


    GrabStuffState(Controller controller) {
        super(controller);
        setTargetableToValidSquares(controller.getCurrPc());
    }


    /**
     * Calls the setWeaponToGrabIndec method of the pre-selected Square
     * @see Square
     * @param index the index to pass to the method
     */
    @Override
    public void selectWeaponOnBoard(int index) {
        if (targetSquare != null) {
            try {
                targetSquare.setWeaponToGrabIndex(index);
                controller.getCurrPlayer().getView().ack("You have selected the " +
                        (index == 0
                        ? index+1 + "st index"
                        : index+1 + "nd index"));
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
            try {
                controller.getCurrPlayer().getView().ack("You can't drop your weapon. You need it to harm everyone!");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            try {
                controller.getCurrPlayer().getView().ack("The square " + targetSquare.toString() + " has been selected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    /**
     * Selects a powerUp to use as an ammo when collecting a weapon
     * @param index the powerup card index
     */
    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp = controller.getCurrPc().getPowerUpCard(index);
        if (powerUp != null && powerUp.isSelectedAsAmmo())
            powerUp.setSelectedAsAmmo(true);
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
                try {
                    controller.getCurrPlayer().getView().ack("You can't use this action on this Square. It's empty!!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return false;
            } catch (NotEnoughAmmoException e) {
                try {
                    controller.getCurrPlayer().getView().ack("You don't have enough ammos. Come on, collect them first!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return false;
            } catch (Exception e){
                try {
                    controller.getCurrPlayer().getView().ack("Be careful! " + e );
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        }
        return false;
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
