package controller;

import model.Enumerations.PcColourEnum;
import model.PowerUpCard;
import model.Tile;

import java.util.HashSet;

public interface temporaneo {

    void printOnView(String s);

    int receiveNumber();

    PcColourEnum receivePcColourEnum();

    PowerUpCard receivePowerUpCard();

    void showPossibleTiles(HashSet<Tile> tiles);

    Tile receiveTile();
}
