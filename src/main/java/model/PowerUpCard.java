package model;

public abstract class PowerUpCard {
    private AmmoEnum colour;

    public AmmoEnum getColour(){ return this.colour; }

    public abstract void useEffect();

    public abstract void useAsAmmo();

    public abstract void useToRespawn();
}
