package server.model.squares;

import com.google.gson.annotations.Expose;
import common.dto_model.SquareDTO;
import common.enums.SquareColourEnum;
import common.events.square_events.ItemCollectedEvent;
import common.events.square_events.SquareRefilledEvent;
import common.exceptions.EmptySquareException;
import server.model.AmmoTile;
import server.model.Deck;
import server.model.Pc;
import server.model.WeaponCard;

import java.util.stream.Collectors;

/**
 * A square containing an AmmoTile
 */
public class AmmoSquare extends Square {

    @Expose private AmmoTile ammoTile;
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
        this.ammoTile = ammoDeck.draw();

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

        //notify item collected
        events.fireEvent(new ItemCollectedEvent(
                currPc.getColour(), convertToDTO(), ammoTile.toString()));

        ammoTile = null;
    }

    
    /**
     * If not present, loads a card
     */
    public void refill(){
        if(ammoTile == null) {
            ammoTile = ammoDeck.draw();
        }

        //notify square refilled
        events.fireEvent(new SquareRefilledEvent(convertToDTO(), false));
    }


    @Override
    public String itemToString() {
        return ammoTile.toString();
    }


    public SquareDTO convertToDTO(){
        SquareDTO squareDTO = new SquareDTO();
        squareDTO.setRow(getRow());
        squareDTO.setCol(getCol());
        squareDTO.setColour(getColour());
        squareDTO.setTargetable(isTargetable());
        squareDTO.setPcs(getPcs().stream().map(Pc::getColour).collect(Collectors.toSet()));
        if (ammoTile != null)
            squareDTO.setAmmoTile(ammoTile.convertToDTO());
        return squareDTO;
    }
}

