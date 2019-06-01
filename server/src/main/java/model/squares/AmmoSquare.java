package model.squares;

import enums.SquareColourEnum;
import model.AmmoTile;
import model.Deck;
import exceptions.EmptySquareException;
import model.Pc;
import model.WeaponCard;

/**
 * A square containing an AmmoTile
 */
public class AmmoSquare extends Square {

    private AmmoTile ammoTile;
    private Deck<AmmoTile> ammoDeck;


    public AmmoSquare(){
        super();
    }

    
    public AmmoSquare(int x, int y, SquareColourEnum colour) {
        super(x, y, colour);
    }


    @Override
    public void assignDeck(Deck<WeaponCard> weaponsDeck, Deck<AmmoTile> ammoDeck) {
        this.ammoDeck = ammoDeck;
        ammoTile = this.ammoDeck.draw();
    }


    @Override
    public boolean isEmpty(){
        return ammoTile == null;
    }

    public AmmoTile getAmmoTile() {
        return ammoTile;
    }

    
    /**
     * Adds the ammos of the ammotile on this square to the given Pc, using Pc.addAmmo()
     * @param currPc the pc to add the ammos to
     * @throws EmptySquareException if the Square on which the pc is is empty
     */
    @Override
    public void collect(Pc currPc) throws EmptySquareException {
        if (isEmpty())
            throw new EmptySquareException();
        currPc.addAmmo(ammoTile);
        ammoTile = null;
    }

    
    /**
     * If not present, loads a card
     */
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
