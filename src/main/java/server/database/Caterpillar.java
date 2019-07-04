package server.database;

import java.util.ArrayList;
import java.util.List;

public class Caterpillar<P, S, V> {

    /**
     * This class is a dynamically changing HashMap.
     * At the beginning it is used like an HashMap of V objects accessed by a P key; you can add and remove values by using the primary key.
     * You can also add S objects to the data structure, but they are unused at the moment.
     * The size of SKs' list is always less then or equals to the size of the list of values.
     * When the size of List of S values reaches the size of the List of values, Caterpillar "metamorphoses" and starts to behaves like an
     * HashMap in which keys are S type objects and values are V type objects and the binding between secondary keys and values is guaranteed
     * by the order of insertions of secondary keys and values.
     */

    private ArrayList<P> primaryKeys;
    private ArrayList<S> secondaryKeys;
    private ArrayList<V> values;


    public Caterpillar(){
        this.primaryKeys = new ArrayList<>();
        this.secondaryKeys = new ArrayList<>();
        this.values = new ArrayList<>();
    }


    public void put(P k, V v) {
        primaryKeys.add(primaryKeys.size(), k);
        values.add(values.size(), v);
    }


    public V get(S k) {
        return values.get(secondaryKeys.indexOf(k));
    }


    public P getPrimaryKey(S s) {
        if (secondaryKeys.contains(s))
            return primaryKeys.get(secondaryKeys.indexOf(s));
        return null;
    }


    public S getSecondaryKey(V v) {
        if(values.indexOf(v) < secondaryKeys.size())
            return secondaryKeys.get(values.indexOf(v));
        return null;
    }


    public void insertSK(S k) {
        secondaryKeys.add(secondaryKeys.size(), k);
    }


    public void remove(P k){
        int i = primaryKeys.indexOf(k);
        values.remove(i);
        primaryKeys.remove(i);
        if (i < secondaryKeys.size()){
            secondaryKeys.remove(i);
        }
    }


    public void removeSecondary(S s) {
        secondaryKeys.remove(s);
    }



    public List<P> primaryKeySet(){
        return primaryKeys;
    }


    public List<S> secondaryKeySet() {
        return secondaryKeys;
    }


    public List<V> values(){
        return values;
    }
}
