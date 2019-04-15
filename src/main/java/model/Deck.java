package model;


import java.util.*;

public class Deck<E> {

    private Game currGame;
    /**
     * used for random insertion
     */
    private Random random;
    private ArrayList<E> cards;

    public Deck(Game currGame) {
        this.currGame = currGame;
        cards = new ArrayList<>();
        random = new Random();
    }

    public Game getCurrGame(){
        return currGame;
    }

    /**
     * draw a card
     *
     * @return the first card of the deck
     */
    public E draw() {
        E card = cards.get(0);
        cards.remove(0);
        return card;
    }

    /**
     * Adds the given card to a random position
     *
     * @param e a generic card
     */
    public void add(E e) {
        int index;
        if (size() == 0) {
            index = 0;
        } else {
            index = random.nextInt(size());
        }
        cards.add(index, e);
    }

    /**
     * like one from collection
     *
     * @return the number of cards in the deck
     */
    public int size() {
        return cards.size();
    }

    /**
     * Checks if a card is in the deck
     *
     * @param o the card to check for
     * @return true if and only if o is in the deck
     */
    public boolean contains(Object o) {
        return cards.contains(o);
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = true;
        if (!(obj instanceof Deck)) {
            equals = false;
        } else {
            Deck d = (Deck) obj;
            for (Object o : d.cards) {
                if (!this.contains(o)) {
                    equals = false;
                }
            }
            for (Object o : this.cards) {
                if (!(d.contains(o))) {
                    equals = false;
                }
            }
        }
        return equals;
    }

    /**
     * Usual hashcode method
     *
     * @return hashcode of ArrayList "cards"
     */
    @Override
    public int hashCode() {
        return cards.hashCode();
    }
}
