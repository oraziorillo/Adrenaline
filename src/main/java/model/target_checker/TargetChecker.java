package model.target_checker;

import model.Character;
import model.Game;


public abstract class TargetChecker {

    public Game game;
    abstract boolean isValid(Character decorated);
    //serve inserire un costruttore??
}
