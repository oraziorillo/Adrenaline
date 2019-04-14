package model;

public abstract class TargetChecker {

    //TODO togliere attributo game e metterlo nel costruttore
    Game game;

    abstract boolean isValid(Tile t);
}

class EmptyChecker extends TargetChecker {

    public boolean isValid(Tile t){
        return true;
    }
}
