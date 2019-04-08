package model.target_checker;

import model.Tile;
import model.Character;

import java.util.ArrayList;

public interface TargetCondition {
    boolean isValid(ArrayList<Character> characters, Tile startingTile);
}
