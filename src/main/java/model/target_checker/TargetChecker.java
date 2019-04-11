package model.target_checker;

import model.Pc;
import model.Game;

public abstract class TargetChecker {
    public Game game;
    abstract boolean isValid(Pc decorated);
    //serve inserire un costruttore??
}
