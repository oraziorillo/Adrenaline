package model.target_checker;

import model.Pc;
import model.Game;


public abstract static class TargetChecker {

    Game game;
    public abstract boolean isValid(Pc pc);

}
