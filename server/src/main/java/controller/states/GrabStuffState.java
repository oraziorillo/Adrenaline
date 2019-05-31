package controller.states;

import controller.Controller;
import exceptions.EmptySquareException;
import exceptions.NotEnoughAmmoException;
import model.Pc;
import model.PowerUpCard;
import model.squares.Square;
import java.util.Set;

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
     * @param targetSquare the square wich will do something
     */
    @Override
    public void selectSquare(Square targetSquare){
        if (!targetSquare.isEmpty()) {
            this.targetSquare = targetSquare;
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
     * @see model.Game
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
        targetableSquares = referencePc.getCurrSquare().atDistance(maxDistance);
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
        Pc currPc = controller.getCurrPc();
        try {
            currPc.collect();
            currPc.moveTo(targetSquare);
        } catch (EmptySquareException e) {
            //TODO print error
            return false;
        } catch (IllegalStateException e) {
            //TODO print what the player should select
            return false;
        } catch (NotEnoughAmmoException e) {
            //TODO the pc has not enough ammo
            return false;
        }
        controller.getGame().setTargetableSquares(targetableSquares, false);
        return true;
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
