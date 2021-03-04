package server.controller.states;

import server.controller.Controller;
import server.controller.Player;

import java.util.Random;

import static common.Constants.FIRST_MAP;
import static common.Constants.LAST_MAP;

/**
 * State to init the map*/
public class SetupMapState extends State{

    private int mapIndex;


    public SetupMapState(Controller controller) {
        super(controller);
        this.mapIndex = -1;
        //cli.controller.startTimer();
    }


    /**
     * Sets a map index
     * @param mapIndex the index of the map
     */
    @Override
    public void selectMap(Player p, int mapIndex) {
        if (mapIndex >= FIRST_MAP && mapIndex <= LAST_MAP) {
            this.mapIndex = mapIndex;
            controller.ackCurrent(System.lineSeparator() + "This is the Game Board you chose, do you like it?" +
                    controller.getGame().preLoadedGameBoardToString(mapIndex) +
                    System.lineSeparator() + "(\"ok\" to confirm your choice)");
        } else {
            controller.ackCurrent(System.lineSeparator() + "Maps are enumerated from 1 to 4");
        }
    }


    /**
     * Initialise the game map with the specified index
     * @return true if the index was non-negative (and then the map is selected), false else
     */
    @Override
    public boolean ok(Player p) {
        if (mapIndex >= FIRST_MAP && mapIndex <= LAST_MAP) {
            controller.getGame().initMap(mapIndex);
            return true;
        } else {
            controller.ackCurrent(System.lineSeparator() + "Choose the map first");
        }
        return false;
    }


    @Override
    public State forcePass(Player p) {
        Random random = new Random();
        controller.getGame().initMap(random.nextInt(4) + 1);
        return nextState();
    }


    /**
     * Transition
     * @return SetupKillshotTrackState
     */
    @Override
    public State nextState() {
        return new SetupKillShotTrackState(controller);
    }
}
