package model;

import java.util.ArrayList;

public class Game {
    private short remainigActions;
    private int currentCharacterIndex;
    private ArrayList<Character> characters;
    private Killshot[] killShotTrack;
    Deck<AmmoCard> ammosDeck = new Deck<>();
    Deck<WeaponCard> weaponsDeck = new Deck<>();
    Deck<PowerUpCard> powerUpsDeck = new Deck<>();

    public Game(String gameID) {
        this.remainigActions = 2;
        this.currentCharacterIndex = 0;
        this.characters = new ArrayList<>(1);
        this.killShotTrack = new Killshot[8];
        initDecks();
    }

    public short getRemainigActions() {
        return remainigActions;
    }

    public void decreaseRemainigActions() {
        remainigActions--;
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

    public void nextTurn() {
        if (currentCharacterIndex == characters.size() - 1)
            currentCharacterIndex = 0;
        currentCharacterIndex++;
    }

    /* public GenerationTile respawnpoint(CharacterColourEnum colour) {
        //TODO: IMPLEMENTA LA RICERCA DEL GENERATION TILE DI QUEL COLORE
    }

     */
}
