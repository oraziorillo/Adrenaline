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
    public void selectPowerUP(int index) {
        //qui non serve controllare l'indice poichè in questo punto del gioco non dovrebbe mai aver4 powerUP
        PowerUpCard powerUp;
        powerUp = controller.getCurrPc().getPowerUpCard(index);
        if (!powerUp.isShooting() && !powerUp.isAsynchronous()){
            currPowerUp = powerUp;
        } else {
            //magari qui stampa a video il messaggio che non può usare quel power up in quel momento
        }

        setTargetableToValidSquares(controller.getCurrPc());
    }

    @Override
    public void selectTarget(Pc targetPc) {
        if( currPowerUp != null && !currPowerUp.isSelfMovement()){
            powerUpTarget = targetPc;
        }
    }

    @Override
    public void selectSquare(Square targetSquare) {
        if(currPowerUp.isSelfMovement()){
            controller.getCurrPc();
        }
    }

    @Override
    public State nextState() {
        return nextState;
    }
}
