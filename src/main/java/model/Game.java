package model;

import java.util.ArrayList;

public class Game {
    private short remainigActions;
    private int currentCharacterIndex;
    private ArrayList<Character> characters;
    private Killshot[] killShotTrack;
    private Deck AmmosDeck;
    private Deck WeaponsDeck;
    private Deck PowerUpsDeck;

    public Game(){
        remainigActions = 2;
        currentCharacterIndex = 0;
        characters = new ArrayList<>(1);
        killShotTrack = new Killshot[8];
    }

    public Game(int n){
        remainigActions = 2;
        currentCharacterIndex = 0;
        characters = new ArrayList<>(n);
        killShotTrack = new Killshot[8];
    }

    public void nextTurn(){
        if(currentCharacterIndex == characters.size() - 1)
            currentCharacterIndex = 0;
        currentCharacterIndex++;
    }

    public short getRemainigActions(){
        return  remainigActions;
    }

    public void setRemainigActions(short n) throws IllegalArgumentException{
        if(n < 0 || n > 2)
            throw new IllegalArgumentException();
        remainigActions = n;
    }

    public Character getCurrentCharacter(){
        return characters.get(currentCharacterIndex);
    }

    public Killshot[] getKillShotTrack() {
        return killShotTrack;
    }

    public void initDecks(){
        //TODO
    }

    public Deck getAmmosDeck() {
        return AmmosDeck;
    }

    public Deck getPowerUpsDeck() {
        return PowerUpsDeck;
    }

    public Deck getWeaponsDeck() {
        return WeaponsDeck;
    }
}
