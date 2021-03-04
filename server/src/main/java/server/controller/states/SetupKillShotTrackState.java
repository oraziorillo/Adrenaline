package server.controller.states;

import common.enums.ControllerMethodsEnum;
import common.enums.PcColourEnum;
import server.controller.Controller;
import server.controller.Player;

import java.util.Random;

import static common.Constants.MAX_KILL_SHOT_TRACK_SIZE;
import static common.Constants.MIN_KILL_SHOT_TRACK_SIZE;

/**
 * State to Set up the KillshotTrack
 */
public class SetupKillShotTrackState extends State{


    private int killShotTrackIndex;


    SetupKillShotTrackState(Controller controller) {
        super(controller);
        this.killShotTrackIndex = -1;
        controller.ackCurrent("\nI'll let you choose even the number of skulls to place on the killshot track (5 to 8)");
        controller.ackCurrent("(Use the command " + ControllerMethodsEnum.CHOOSE_NUMBER_OF_SKULLS.getUsage() + ". Type \"h\" for details on all available commands)");
    }

    
    /**
     * Prepares the number of skulls desired
     * @param n the number of skulls
     */
    @Override
    public void selectNumberOfSkulls(Player p, int n) {
        if (n >= MIN_KILL_SHOT_TRACK_SIZE && n <= MAX_KILL_SHOT_TRACK_SIZE) {
            this.killShotTrackIndex = n;
            if (n == MIN_KILL_SHOT_TRACK_SIZE)
                controller.ackCurrent("\nJust " + MIN_KILL_SHOT_TRACK_SIZE + " kills?! That's for cowards! (\"ok\" to confirm your choice)");
            else if (n < MAX_KILL_SHOT_TRACK_SIZE)
                controller.ackCurrent("\nThat sounds good. Fair enough. (\"ok\" to confirm your choice)");
            else
                controller.ackCurrent("\nYour testosterone level is nearly as high as DOZER's one!! (\"ok\" to confirm your choice)");
        } else {
            controller.ackCurrent("\nThe number of skulls should be between 5 and 8");
        }
    }


    /**
     * Inits the kill shot track with the pre-selected number of skulls, if this number is non-negative
     * @return false if a negative number of skulls was set, true else
     */
    @Override
    public boolean ok(Player p) {
        if (killShotTrackIndex >= MIN_KILL_SHOT_TRACK_SIZE && killShotTrackIndex <= MAX_KILL_SHOT_TRACK_SIZE){
            controller.getGame().initKillShotTrack(killShotTrackIndex);
            return true;
        } else {
            controller.ackCurrent("\nChoose the skulls first");
        }
        return false;
    }


    @Override
    public State forcePass(Player p) {
        Random random = new Random();
        controller.getGame().initKillShotTrack(random.nextInt(3) + 5);
        return new PcSelectionState(controller);
    }


    /**
     * Transition
     * @return PcSelectionState
     */
    @Override
    public State nextState(){
        StringBuilder colourChoice = new StringBuilder("\nAnd finally choose your character, each of them is particular in its own way:");
        for (PcColourEnum c : PcColourEnum.values()) {
            colourChoice.append("\n> ").append(c.toString()).append(c.getTabs()).append("(").append(c.getName()).append(")");
        }
        controller.ackCurrent(colourChoice.toString());
        controller.ackCurrent("(Use the command " + ControllerMethodsEnum.CHOOSE_PC_COLOUR.getUsage() + ". Type \"h\" for details on all available commands)");
        return new PcSelectionState(controller);
    }
}
