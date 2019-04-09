package model;

public class AmmoTile extends Tile {
    private AmmoCard card;
    private /*static*/ Deck<AmmoCard> ammoCardDeck;         //Dubbi su questo attributo

    public AmmoTile(int x, int y, RoomColourEnum colour, Deck<AmmoCard> deck) {
        super(x, y, colour);
        ammoCardDeck = deck;
        card = ammoCardDeck.draw();
    }

    public AmmoCard drawCard() {
        //TODO: qui dovrà essere inserito un observer che notifica che la carta è stata pescata e deve essere sostituita
        AmmoCard oldCard = card;
        card = null;
        return oldCard;
    }

    public void drawCardFromDeck(){
        if (card == null){
            //TODO: pesca una nuova carta dal deck
        }
    }


}
