package controller.states;

import controller.Controller;

public class StartTurnState extends State {

    private State nextState;

    StartTurnState(Controller controller) {
        super(controller);
    }

    public boolean runAround(){
        nextState = new RunAroundState(controller);
        return true;
    }

    public boolean grabStuff(){
        nextState = new GrabStuffState(controller);
        return true;
    }

    public boolean shootPeople(){
        nextState = new ShootPeopleState(controller);
        return true;
    }

    @Override
    public State nextState() {
        return nextState;
    }
}
