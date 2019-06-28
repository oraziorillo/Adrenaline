package server.model.squares;

import common.dto_model.PcDTO;
import common.dto_model.SquareDTO;
import common.enums.SquareColourEnum;
import server.exceptions.EmptySquareException;
import server.model.AmmoTile;
import server.model.Deck;
import server.model.Pc;
import server.model.WeaponCard;

import static common.Constants.COLLECT;
import static common.Constants.REFILL;

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
    public void init(Deck<WeaponCard> weaponsDeck, Deck<AmmoTile> ammoDeck) {
        this.ammoDeck = ammoDeck;
        refill();
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

        //PcDTO old = modelMapper.map(this, PcDTO.class);

        if (isEmpty())
            throw new EmptySquareException();
        currPc.addAmmo(ammoTile);
        ammoTile = null;

        //notify listeners
        //changes.firePropertyChange(COLLECT, old, modelMapper.map(this, SquareDTO.class));
    }

    
    /**
     * If not present, loads a card
     */
    public void refill(){

        //PcDTO old = modelMapper.map(this, PcDTO.class);

        if(ammoTile == null) {
            ammoTile = ammoDeck.draw();
        }

        //notify listeners
        //changes.firePropertyChange(REFILL, old, modelMapper.map(this, SquareDTO.class));
    }
}

