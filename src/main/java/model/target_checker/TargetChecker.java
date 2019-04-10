package model.target_checker;

import model.Character;
import model.Game;
import model.Tile;

import java.util.ArrayList;


public abstract static class TargetChecker {

    Game game;
    boolean isValid(Character character);

}
