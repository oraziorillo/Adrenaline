package model.target_checker;

import model.Character;
import model.Tile;

import java.util.ArrayList;

public class EmptyChecker extends TargetChecker {
    @Override
    public boolean isValid(Character characters) {
        return true;
    }
}

