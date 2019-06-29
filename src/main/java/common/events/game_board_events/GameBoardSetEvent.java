package common.events.game_board_events;

import common.dto_model.GameBoardDTO;

import static common.Constants.GAME_BOARD_SET;

public class GameBoardSetEvent extends GameBoardEvent {

    private int eventID = GAME_BOARD_SET;
    private int numberOfMap;


    public GameBoardSetEvent(GameBoardDTO gameBoard, int numberOfMap){
        super(gameBoard);
        this.numberOfMap = numberOfMap;
    }


    @Override
    public String getDynamicMessage() {
        return "Map " + numberOfMap + " has been set";
    }
}
