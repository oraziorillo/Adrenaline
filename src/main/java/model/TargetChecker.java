package model;

public abstract class TargetChecker {
    public Game game;
    abstract boolean isValid(Pc decorated);
}
