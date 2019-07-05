package server.controller.states;

import common.events.requests.Request;
import server.ServerPropertyLoader;
import server.controller.Controller;
import server.controller.Player;
import server.model.Pc;
import server.model.PowerUpCard;
import server.model.actions.Action;
import server.model.target_checkers.EmptyChecker;
import server.model.target_checkers.VisibilityDecorator;

import javax.swing.*;
import java.util.ArrayList;

public class InactiveState extends State {

    public static final int PC_SELECTION_STATE = 0;
    static final int FIRST_TURN_STATE = 1;
    static final int START_TURN_STATE = 2;

    private int nextState;
    private boolean hasToRespawn;
    private boolean wantToUseTagbackGrenade;
    private Pc damagedPc;
    //private Timer powerUpTimer;
    private int numberOfTagbackGrenades;



    public InactiveState(Controller controller, int nextState) {
        super(controller);
        this.nextState = nextState;
        //this.powerUpTimer = new Timer( ServerPropertyLoader.getInstance().getRequestTimer(), actionEvent -> useFirstTagbackGrenade());
        //this.powerUpTimer.stop();
    }


    @Override
    public boolean isInactive(){
        return true;
    }


    @Override
    public void setHasToRespawn(boolean hasToRespawn) {
        this.hasToRespawn = hasToRespawn;
    }


    @Override
    public void response(String response) {
        switch (response) {
            case "yes":
                this.wantToUseTagbackGrenade = true;
                //controller.stopRequestTimer();
                if (numberOfTagbackGrenades > 1) {
                    //powerUpTimer.start();
                    controller.ackRequestRecipient("\nNow choose a Tagback Grenade to use");
                }
                else
                    useFirstTagbackGrenade();
                break;
            case "no":
                this.wantToUseTagbackGrenade = false;
                //controller.stopRequestTimer();
                controller.unlock();
                break;
            default:
                break;
        }
    }


    @Override
    public void selectPowerUp(int index) {
        if (wantToUseTagbackGrenade){
            PowerUpCard powerUpCard = damagedPc.getPowerUpCard(index);
            if (!powerUpCard.getAction().isParameterized()){
                Action action = powerUpCard.getAction();
                action.selectPc(controller.getCurrPc());
                action.apply(damagedPc);
                damagedPc.discardPowerUp(powerUpCard);
                wantToUseTagbackGrenade = false;
                numberOfTagbackGrenades = 0;
                damagedPc = null;
                //powerUpTimer.stop();
                controller.unlock();
            }
        }
    }


    public void useFirstTagbackGrenade() {
        //this.powerUpTimer.stop();
        for (PowerUpCard p : damagedPc.getPowerUps())
            if (!p.getAction().isParameterized()) {
                p.getAction().selectPc(controller.getCurrPc());
                p.getAction().apply(damagedPc);
                damagedPc.discardPowerUp(p);
                wantToUseTagbackGrenade = false;
                numberOfTagbackGrenades = 0;
                damagedPc = null;
                //powerUpTimer.stop();
                controller.unlock();
                break;
            }
    }


    @Override
    public void checkTagbackGrenadeConditions(Player damagedPlayer) {
        boolean usable;
        damagedPc = damagedPlayer.getPc();

        for (PowerUpCard p : damagedPc.getPowerUps())
            if (p.getName().equals("Tagback Grenade")) {
                numberOfTagbackGrenades++;
            }

        usable = (numberOfTagbackGrenades > 0);

        usable &= new VisibilityDecorator(new EmptyChecker())
                .validSquares(damagedPc.getCurrSquare())
                .contains(controller.getCurrPc().getCurrSquare());

        if (usable) {
            ArrayList<String> possibilities = new ArrayList<>();
            possibilities.add("yes");
            possibilities.add("no");
            Request request = new Request("Do you wanna use your Tagback Grenade? (yes/no)", possibilities);
            controller.sendRequest(request, damagedPlayer);
            damagedPlayer.setActiveRequest(request);
        }
    }


    @Override
    public State forcePass() {
        return this;
    }


    @Override
    public State nextState() {
        if (hasToRespawn)
            return new RespawnState(controller);
        switch (nextState){
            case PC_SELECTION_STATE:
                return new PcSelectionState(controller);
            case FIRST_TURN_STATE:
                return new FirstTurnState(controller);
            case START_TURN_STATE:
                return new StartTurnState(controller);
            default:
                return this;
        }
    }
}
