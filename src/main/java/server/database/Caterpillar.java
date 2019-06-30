package server.database;

import java.util.ArrayList;
import java.util.List;

public class Caterpillar<PK, SK, V> {

    /**
     * This class is a dynamically changing HashMap.
     * At the beginning it is used like an HashMap of V objects accessed by a PK key; you can add and remove values by using the primary key.
     * You can also add SK objects to the data structure, but they are unused at the moment.
     * The size of SKs' list is always less then or equals to the size of the list of values.
     * When the size of List of SK values reach the size of the List of values, Caterpillar "metamorphoses" and starts to behaves like an
     * HashMap in which keys are SK type objects and values are V type objects and the binding between secondary keys and values is guaranteed
     * by the order of insertions of secondary keys and values.
     */

    private ArrayList<PK> primaryKeys;
    private ArrayList<SK> secondaryKeys;
    private ArrayList<V> values;


    public Caterpillar(){
        this.primaryKeys = new ArrayList<>();
        this.secondaryKeys = new ArrayList<>();
        this.values = new ArrayList<>();
    }


    private boolean metamorphosed(){
        return secondaryKeys.size() == values.size();
    }


    public void put(PK k, V v) {
        primaryKeys.add(primaryKeys.size(), k);
        values.add(values.size(), v);
    }


    public V get(SK k) {
        return values.get(secondaryKeys.indexOf(k));
    }


    public SK getSecondaryKey(V v) {
        if(values.indexOf(v) < secondaryKeys.size())
            return secondaryKeys.get(values.indexOf(v));
        return null;
    }


    public void insertSK(SK k) {
        secondaryKeys.add(secondaryKeys.size(), k);
    }


    public void remove(PK k){
        int i = primaryKeys.indexOf(k);
        values.remove(i);
        primaryKeys.remove(i);
        if (i < secondaryKeys.size()){
            secondaryKeys.remove(i);
        }
    }



    public List<PK> primaryKeySet(){
        return primaryKeys;
    }


    public List<SK> secondaryKeySet() {
        return secondaryKeys;
    }


    public List<V> values(){
        return values;
    }
}
