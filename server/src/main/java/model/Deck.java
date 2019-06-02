package model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class models a deck of objects by decorating an arraylist
 * @param <E> the Type of the cards
 * @see ArrayList
 */
public class Deck<E> {

    private ArrayList<E> cards;

    /**
     * used for random insertion
     */
    private Random random;


    Deck() {
        cards = new ArrayList<>();
        random = new Random();
    }


    List<E> getCards() {
        return cards;
    }

    /**
     * draw a card
     * @return the first card of the deck
     */
    public E draw() {
        if (cards.isEmpty())
            return null;
        E card = cards.get(0);
        cards.remove(0);
        return card;
    }


    /**
     * shuffles the deck
     * @implNote The deck is pre-shuffled, see add
     */
    void shuffle(){
        for (int i = size() - 1; i > 0; i--){
            int index = random.nextInt(i + 1);
            //Simple swap
            E card = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, card);
        }
    }


    /**
     * Adds the given card to a random position     *
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
     * @return the number of cards in the deck
     */
    public int size() {
        return cards.size();
    }


    /**
     * Checks if a card is in the deck
     * @param o the card to check for
     * @return true if and only if o is in the deck
     */
    boolean contains(Object o) {
        return cards.contains(o);
    }


    @Override
    public boolean equals(Object obj) {
        Deck d;
        try {
            d = ( Deck ) obj;
        }catch ( ClassCastException e ){
            return false;
        }
        return cards.equals( d.cards );
    }


    /**
     * @return hashcode of the collection containing the cards
     */
    @Override
    public int hashCode() {
        return cards.hashCode();
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        Deck<E> clone = new Deck<>();
        try {
            clone.cards = ( ArrayList<E> ) this.cards.clone();
            clone.random = this.random;
        }catch ( ClassCastException e ){
            e.printStackTrace();
        }
        return clone;
    }


    public void print() {

        Gson gson = new Gson();

        cards.forEach( c -> System.out.println(gson.toJson(c)));
    }
}
