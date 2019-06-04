package server.controller.states;

import server.controller.Controller;
import server.exceptions.EmptySquareException;
import server.exceptions.NotEnoughAmmoException;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.squares.Square;
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
            } catch (NullPointerException e) {
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
        if (targetSquare != null)
            targetSquare.setWeaponToDropIndex(index);
    }


    /**
     * Prepares a square for doing something
     * @param row of the selected square
     * @param column of the selected square
     */
    @Override
    public void selectSquare(int row, int column){
        Square s = controller.getGame().getSquare(row, column);
        if (s != null && !s.isEmpty()) {
            this.targetSquare = s;
            targetSquare.resetWeaponIndexes();
        }
    }

    
    /**
     * Selects a powerUp to use as a weapon when collecting a weapon
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
    void setTargetableToValidSquares(Pc referencePc){
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
        targetSquare.resetWeaponIndexes();
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
                currPc.moveTo(targetSquare);
                controller.getGame().setTargetableSquares(targetableSquares, false);
                controller.addSquareToRefill(targetSquare);
                return true;
            } catch (EmptySquareException e) {
                //TODO print error
                return false;
            } catch (NotEnoughAmmoException e) {
                //TODO the pc has not enough ammo
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
