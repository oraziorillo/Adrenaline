package common.events.game_board_events;

import common.dto_model.GameBoardDTO;
import common.events.ModelEvent;

public abstract class GameBoardEvent implements ModelEvent {

    GameBoardDTO gameBoard;


    GameBoardEvent(GameBoardDTO gameBoard){
        this.gameBoard = gameBoard;
    }


    @Override
    public GameBoardDTO getDTO() {
        return gameBoard;
    }
}
