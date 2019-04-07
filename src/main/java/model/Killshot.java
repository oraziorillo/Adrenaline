package model;

public class Killshot {
    private CharColour colour;
    private boolean skulled=true;
    private boolean overkilled;
    public void kill(CharColour killer, boolean overkill){
        if(skulled) {
            this.colour = killer;
            this.overkilled = overkill;
            this.skulled = false;
        }else{
            throw new IllegalStateException("Can't mark two times the same killshot tab");
        }
    }

    public boolean isSkulled() {
        return skulled;
    }

    public boolean isOverkilled() {
        return overkilled;
    }

    public CharColour getColour() {
        return colour;
    }
}
