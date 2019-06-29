package common.events.game_events;

import common.dto_model.GameBoardDTO;

import static common.Constants.GAME_BOARD_SET;

public class GameBoardSetEvent extends GameEvent {

    private GameBoardDTO gameBoard;
    private int numberOfMap;


    public GameBoardSetEvent(GameBoardDTO map, int numberOfMap){
        this.gameBoard = map;
        this.numberOfMap = numberOfMap;
    }


    @Override
    public String toString() {
        return "Map " + numberOfMap + " has been set";
    }


    @Override
    public GameBoardDTO getNewValue() {
        return gameBoard;
    }


    @Override
    public String getPropertyName() {
        return GAME_BOARD_SET;
    }
}
