package controller.states;

import controller.Controller;

/**Turn beginning state*/
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
    
    /**
     * Transition
     * @return the state with the same name of the last method called on this state
     */
    @Override
    public State nextState() {
        return nextState;
    }
}
