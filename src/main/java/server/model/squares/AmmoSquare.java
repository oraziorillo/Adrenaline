package server.model.squares;

import common.enums.SquareColourEnum;
import common.remote_interfaces.ModelChangeListener;
import server.exceptions.EmptySquareException;
import server.model.AmmoTile;
import server.model.Deck;
import server.model.Pc;
import server.model.WeaponCard;

import java.util.List;

import static common.Constants.AMMO_DECK;

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
    public void init(Deck<WeaponCard> weaponsDeck, Deck<AmmoTile> ammoDeck, List<ModelChangeListener> listeners) {
        this.listeners = listeners;
        this.ammoDeck = ammoDeck;
        ammoTile = this.ammoDeck.draw();
    }


    @Override
    public boolean isEmpty(){
        return ammoTile == null;
    }


    @Override
    public boolean isSpawnPoint() {
        return false;
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

        //notify listeners
        listeners.parallelStream().forEach(l -> l.onAmmoCollect(currPc.getColour()));
    }

    
    /**
     * If not present, loads a card
     */
    public void refill(){
        if(ammoTile == null) {
            ammoTile = ammoDeck.draw();
        }

        //notify listeners
        listeners.parallelStream().forEach(l -> l.onRefill(AMMO_DECK, getRow(), getCol()));
    }
}

