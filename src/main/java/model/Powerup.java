package model;

public abstract class Powerup {
    private AmmoEnum colour;          //è giusto metterlo di tipo RoomColourEnum??

    public AmmoEnum getColour(){
        return colour;
    }

    public abstract void useEffect();

    public abstract void useAsAmmo();

    public abstract void useToRespawn();
}
