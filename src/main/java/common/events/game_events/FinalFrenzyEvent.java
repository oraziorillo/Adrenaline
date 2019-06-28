package common.events.game_events;

import common.dto_model.GameBoardDTO;

import static common.Constants.FINAL_FRENZY;

public class FinalFrenzyEvent extends GameEvent {

    @Override
    public String toString() {
        return "FINAL FRENZY!";
    }


    @Override
    public GameBoardDTO getNewValue() {
        return null;
    }


    @Override
    public String getPropertyName() {
        return FINAL_FRENZY;
    }
}
