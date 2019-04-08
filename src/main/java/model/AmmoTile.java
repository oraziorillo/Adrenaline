package model;

public class AmmoTile extends Tile {
    private AmmoCard card;
    private /*static*/ Deck<AmmoCard> ammoCardDeck;

    AmmoTile(int x, int y, RoomColourEnum colour, Deck<AmmoCard> deck) {
        super(x, y, colour);
        ammoCardDeck = deck;
        card = ammoCardDeck.draw();
    }

    public AmmoCard drawCard() {
        //TODO: se la carta viene ripristinata a fine turno questo metodo va riscritto
        AmmoCard oldCard = card;
        card = ammoCardDeck.draw();
        return oldCard;
    }


}
