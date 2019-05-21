package model.squares;

import enums.SquareColourEnum;
import model.AmmoTile;
import model.Deck;
import exceptions.EmptySquareException;
import model.Pc;

public class AmmoSquare extends Square {

    private AmmoTile ammoTile;
    private Deck<AmmoTile> ammoDeck;

    public AmmoSquare(int x, int y, SquareColourEnum colour, Deck<AmmoTile> deck) {
        super(x, y, colour);
        ammoDeck = deck;
        ammoTile = ammoDeck.draw();
    }


    @Override
    public boolean isEmpty(){
        return ammoTile == null;
    }


    @Override
    public void collect(Pc currPc) throws EmptySquareException {
        if (isEmpty())
            throw new EmptySquareException();
        currPc.addAmmo(ammoTile);
        ammoTile = null;
    }


    public void refill(){
        if(ammoTile == null) {
            ammoTile = ammoDeck.draw();
        }
    }


    @Override
    public boolean isSpawnPoint() {
        return false;
    }
}