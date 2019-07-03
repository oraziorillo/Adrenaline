package server.controller.states;

import server.controller.Controller;

import static common.Constants.FIRST_MAP;
import static common.Constants.LAST_MAP;

/**
 * State to init the map
 */
public class SetupMapState extends State{


    private int mapIndex;


    public SetupMapState(Controller controller) {
        super(controller);
        this.mapIndex = -1;
    }


    /**
     * Sets a map index
     * @param mapIndex the index of the map
     */
    @Override
    public void selectMap(int mapIndex) {
        if (mapIndex >= FIRST_MAP && mapIndex <= LAST_MAP) {
            this.mapIndex = mapIndex;
            controller.ackCurrent("\nThis is the Game Board you chose, do you like it?");
            controller.ackCurrent(controller.getGame().preLoadedGameBoardToString(mapIndex));
        }
    }


    /**
     * Initialise the game map with the specified index
     * @return true if the index was non-negative (and then the map is selected), false else
     */
    @Override
    public boolean ok() {
        if (mapIndex >= FIRST_MAP && mapIndex <= LAST_MAP) {
            controller.getGame().initMap(mapIndex);
            return true;
        }
        return false;
    }


    /**
     * Transition
     * @return SetupKillshotTrackState
     */
    @Override
    public State nextState() {
        controller.ackCurrent("\nI'll let you choose even the number of skulls to place on the killshot track (5 to 8)");
        return new SetupKillShotTrackState(controller);
    }


}
