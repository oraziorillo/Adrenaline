package common.events;

import static common.Constants.FINAL_FRENZY;

public class FinalFrenzyEvent extends ModelEvent {


    @Override
    public String toString() {
        return "FINAL FRENZY!";
    }


    @Override
    public Object getNewValue() {
        return null;
    }


    @Override
    public String getPropertyName() {
        return FINAL_FRENZY;
    }
}
