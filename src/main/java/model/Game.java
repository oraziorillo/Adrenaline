package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game {
    private short remainigActions;
    private int currentCharacterIndex;
    private ArrayList<Character> characters;
    private Killshot[] killShotTrack;
    private final Tile[][] map;
    Deck<AmmoCard> ammosDeck = new Deck<>();
    Deck<Weapon> weaponsDeck = new Deck<>();
    Deck<Powerup> powerUpsDeck = new Deck<>();

    public Game() {             //perchè i deck non vengono istanziati nel costruttore ma fuori?
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

    public void initMap(List<RoomColourEnum> colourOfMapTile, int row, int coloumn, List<int> doorsInMap){
        int k;
        List<RoomColourEnum> tempList = new List<>;
        if(colourOfMapTile.size() != row*coloumn){
            throw new IllegalArgumentException("This list doesn't have the right dimension");
        }
        if(doorsInMap.contains(k) && (k>row*coloumn || k<0)){
            throw new IllegalArgumentException("These values are out of range");
        }
        for(int i = 0; i < row; i++){
            for(int j = 0; j < coloumn; j++){
                map[i][j] = new Tile(i, j, colourOfMapTile.get(i*row + j));
            }
        }
        for(int i = 0; i < row; i++){
            for( int j = 0; j < coloumn; j++){
                tempList.add(map[i][j].getRoomColour());
                while(doorsInMap.contains(i*row+j) && doorsInMap.indexOf(i*row+j)%2==0){
                    k = doorsInMap.get(i*row+j+1);
                    tempList.add(map[k%row][k/coloumn].getRoomColour());
                    doorsInMap.remove(i*row+j);
                    doorsInMap.remove(i*row+j+1);
                }
                for(int m = 0; m < row; m++){
                    for(int n = 0; n < coloumn; n++){
                        if(tempList.contains(map[m][n].getRoomColour())){
                            map[i][j].addVisible(map[m][n]);
                        }
                    }
                }
                tempList.clear();
            }
        }

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
    }

     */
}
