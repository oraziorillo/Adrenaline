package model;

public abstract class PowerUpCard {
    private RoomColourEnum colour;          //è giusto metterlo di tipo RoomColourEnum??

    public RoomColourEnum getColour{ return colour; }

    public abstract void useEffect();

    public abstract void useAsAmmo();

    public abstract void useToRespawn();
}
