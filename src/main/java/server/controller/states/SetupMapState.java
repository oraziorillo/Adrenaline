package server.controller.states;

import server.controller.Controller;

import java.io.FileNotFoundException;

import static server.controller.Controller.FIRST_MAP;
import static server.controller.Controller.LAST_MAP;

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
     * @param n the index of the map
     */
    @Override
    public void selectMap(int n) {
        if (mapIndex >= FIRST_MAP && mapIndex <= LAST_MAP)
            this.mapIndex = n;
    }


    /**
     * Initialise the game map with the specified index
     * @return true if the index was non-negative (and then the map is selected), false else
     */
    @Override
    public boolean ok() {
        if (mapIndex >= FIRST_MAP && mapIndex <= LAST_MAP) {
            try {
                controller.getGame().initMap(mapIndex);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
        return new SetupKillShotTrackState(controller);
    }


}
