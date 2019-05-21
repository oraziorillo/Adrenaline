package model.targetCheckers;

import model.Game;
import model.squares.Square;

import java.util.HashSet;

public abstract class TargetChecker {
    //TODO togliere attributo currGame e metterlo nel costruttore
    public Game game;

    public void Game(Game game) {
        this.game = game;
    }

    public abstract HashSet<Square> validSquares(Square referenceSquare);

}
