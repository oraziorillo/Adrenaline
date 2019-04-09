package model;

import java.util.ArrayList;

public class Game {
    private short remainigActions;
    private int currentCharacterIndex;
    private ArrayList<Character> characters;
    private Killshot[] killShotTrack;
    Deck<AmmoCard> ammosDeck = new Deck<>();
    Deck<Weapon> weaponsDeck = new Deck<>();
    Deck<Powerup> powerUpsDeck = new Deck<>();

    public Game() {
        remainigActions = 2;
        currentCharacterIndex = 0;
        characters = new ArrayList<>(1);
        killShotTrack = new Killshot[8];
        initDecks();
    }

    public Game(int n) {
        remainigActions = 2;
        currentCharacterIndex = 0;
        characters = new ArrayList<>(n);
        killShotTrack = new Killshot[8];
        initDecks();
    }

    public void nextTurn() {
        if (currentCharacterIndex == characters.size() - 1)
            currentCharacterIndex = 0;
        currentCharacterIndex++;
    }

    public short getRemainigActions() {
        return remainigActions;
    }

    public void setRemainigActions(short n) throws IllegalArgumentException {
        if (n < 0 || n > 2)
            throw new IllegalArgumentException();
        remainigActions = n;
    }

    public Character getCurrentCharacter() {
        return characters.get(currentCharacterIndex);
    }

    public Killshot[] getKillShotTrack() {
        return killShotTrack;
    }

    private void initDecks() {
    }

    public Deck getAmmosDeck() {
        return ammosDeck;
    }

    public Deck getPowerUpsDeck() {
        return powerUpsDeck;
    }

    public Deck getWeaponsDeck() {
        return weaponsDeck;
    }

    /* public GenerationTile respawnpoint(CharColourEnum colour) {
        //TODO: IMPLEMENTA LA RICERCA DEL GENERATION TILE DI QUEL COLORE
        return new GenerationTile(0,0,null);
    }

     */
}
