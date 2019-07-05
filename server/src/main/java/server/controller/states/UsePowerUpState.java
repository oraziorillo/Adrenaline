package server.controller.states;

import common.enums.PcColourEnum;
import server.controller.Controller;
import server.controller.Player;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.actions.Action;
import server.model.squares.Square;

import java.util.HashSet;
import java.util.Set;

public class UsePowerUpState extends State {

    private PowerUpCard currPowerUp;
    private Action currAction;
    private Set<Square> targetableSquares;

    UsePowerUpState(Controller controller) {
        super(controller);
        this.targetableSquares = new HashSet<>();
    }


    @Override
    public void selectPowerUp(int index) {
        PowerUpCard powerUp;
        Action powerUpAction;
        try {
            powerUp = controller.getCurrPc().getPowerUpCard(index);
            powerUpAction = powerUp.getAction();
            if (powerUpAction.isParameterized() && !powerUpAction.isAdditionalDamage()) {
                currPowerUp = powerUp;
                currAction = powerUpAction;
                if(currAction.isSelfMovement()) {
                    setTargetableToValidSquares(controller.getCurrPc());
                    controller.ackCurrent("\nYou can do like Goku now, teleport everywhere!");
                } else {
                    controller.ackCurrent("\nLet's make some moves");
                }
            }
        } catch (IllegalArgumentException e) {
            controller.ackCurrent(e.getMessage());
        }
    }


    @Override
    void setTargetableToValidSquares(Pc referencePc) {
        if(targetableSquares != null && !targetableSquares.isEmpty())
            controller.getGame().setTargetableSquares(targetableSquares, false);
        targetableSquares = currAction.validSquares(referencePc.getCurrSquare());
        controller.getGame().setTargetableSquares(targetableSquares, true);
    }


    @Override
    public void selectTarget(PcColourEnum targetPcColour) {
        if (currAction != null && !currAction.isSelfMovement() && !targetPcColour.equals((controller.getCurrPc().getColour()))) {
            Pc targetPc = controller.getPlayers().stream().map(Player::getPc).filter(pc -> pc.getColour() == targetPcColour).findFirst().orElse(null);
            if (targetPc != null) {
                currAction.selectPc(targetPc);
                setTargetableToValidSquares(targetPc);
                controller.ackCurrent("Is it that you want to move?");
            }
        }
    }


    @Override
    public void selectSquare(int row, int column) {
        if (currAction != null) {
            Square s = controller.getGame().getSquare(row, column);
            if (s != null && s.isTargetable() && currAction != null)
                currAction.selectSquare(s);
        }
    }


    @Override
    public boolean undo() {
        controller.getGame().setTargetableSquares(targetableSquares, false);
        if (currAction != null)
            currAction.resetAction();
        return true;
    }


    @Override
    public boolean ok() {
        if (currAction != null && currAction.isComplete()) {
            controller.getGame().setTargetableSquares(targetableSquares, false);
            currPowerUp.useAction(controller.getCurrPc());
            currAction.resetAction();
            controller.getCurrPc().discardPowerUp(currPowerUp);
            return true;
        }
        return false;
    }

    @Override
    public State forcePass() {
        controller.getGame().setTargetableSquares(targetableSquares, false);
        if (currAction != null)
            currAction.resetAction();
        controller.resetRemainingActions();
        controller.nextTurn();
        return new InactiveState(controller, InactiveState.FIRST_TURN_STATE);
    }

    @Override
    public State nextState() {
        if (controller.getRemainingActions() == 0) {
            controller.resetRemainingActions();
            return new EndTurnState(controller);
        } else
            return new StartTurnState(controller);
    }
}
