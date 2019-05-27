package controller.states;

import controller.Controller;
import model.Pc;
import model.powerUps.PowerUpCard;
import model.squares.Square;

import java.util.HashSet;

public class StartTurnState extends State {

    private PowerUpCard currPowerUp;
    private Pc powerUpTarget;
    private State nextState;
    private HashSet<Square> targetableSquares;

    StartTurnState(Controller controller) {
        super(controller);
        this.currPowerUp = null;
    }

    @Override
    public boolean runAround(){
        nextState = new RunAroundState(controller);
        return true;
    }

    @Override
    public boolean grabStuff(){
        nextState = new GrabStuffState(controller);
        return true;
    }

    @Override
    public boolean shootPeople(){
        nextState = new ShootPeopleState(controller);
        return true;
    }

    @Override
    public boolean usePowerUp() {
        nextState = new UsePowerUpState(controller);
        return true;
    }

    @Override
    public State nextState() {
        return nextState;
    }
}
