package model.target_checker;

import model.Pc;
import model.Game;

import java.util.Collection;

public abstract class TargetChecker {
    public Game game;
    abstract boolean isValid(Collection<Pc> targets);
}
