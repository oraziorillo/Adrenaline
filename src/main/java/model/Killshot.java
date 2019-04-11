package model;

public class Killshot {
    private CharacterColourEnum colour;
    private boolean skulled = true;
    private boolean overkilled;

    public void kill(CharacterColourEnum killer, boolean overkill) {
        if (skulled) {
            this.colour = killer;
            this.overkilled = overkill;
            this.skulled = false;
        } else {
            throw new IllegalStateException("Can't mark two times the same killshot tab");
        }
    }

    public boolean isSkulled() {
        return skulled;
    }

    public boolean isOverkilled() {
        return overkilled;
    }

    public CharacterColourEnum getColour() {
        return colour;
    }
}
