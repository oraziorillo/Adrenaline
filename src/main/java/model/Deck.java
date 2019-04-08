package model;


import java.util.*;

public class Deck<E> {
    /**
     * The concrete container for cards
     */
    private ArrayList<E> cards;

    /**
     * used for random insertion
     */
    private Random random;

    /**
     * Default constructor
     */
    public Deck<E> {
        cards = new ArrayList<>();
        random = new Random();
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

    /**
     * usual equals method
     *
     * @param obj an object
     * @return false if obj is not a deck or if the decks differs for some cards. true otherwise
     */
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
