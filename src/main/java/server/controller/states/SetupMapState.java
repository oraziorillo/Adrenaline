package server.controller.states;

import server.controller.Controller;

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
        this.mapIndex = n;
    }
    
    /**
     * Initialise the game map with the specified index
     * @return true if the index was non-negative (and then the map is selected), false else
     */
    @Override
    public boolean ok() {
        if (mapIndex >= 0) {
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
        return new SetupKillShotTrackState(controller);
    }


}
