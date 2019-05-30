package controller.states;

import controller.Controller;

public class StartTurnState extends State {

    private State nextState;

    StartTurnState(Controller controller) {
        super(controller);
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
        nextState = new ShootPeopleState(controller, false, false);
        return true;
    }

    @Override
    public boolean usePowerUp() {
        nextState = new UsePowerUpState(controller, null);
        return true;
    }

    @Override
    public State nextState() {
        return nextState;
    }
}
