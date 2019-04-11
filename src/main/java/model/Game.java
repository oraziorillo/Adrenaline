package model;

import java.util.*;

public class Game {
    private short remainigActions;
    private int currentPcIndex;
    private ArrayList<Pc> pcs;
    private Killshot[] killShotTrack;
    private final Tile[][] map;
    Deck<AmmoCard> ammosDeck = new Deck<>();
    Deck<WeaponCard> weaponsDeck = new Deck<>();
    Deck<PowerUpCard> powerUpsDeck = new Deck<>();

    public Game() {
        remainigActions = 2;
        currentPcIndex = 0;
        pcs = new ArrayList<>(1);
        killShotTrack = new Killshot[8];
        initDecks();
        map = null;
    }

    /**
     *
     * @param colourOfMapTile
     * @param row
     * @param coloumn
     * @param doorsInMap
     */ //bisogna aggiungere o un altro parametro in ingresso che specifichi il tipo di tile o rendiamo tile una classe concreta
    public void initMap(List<RoomColourEnum> colourOfMapTile, int row, int coloumn, List<int> doorsInMap){
        int k;
        List<RoomColourEnum> tempList = new LinkedList<>();
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

    public short getRemainigActions() {
        return remainigActions;
    }

    public void decreaseRemainigActions() {
        remainigActions--;
    }

    public Pc getCurrentPc() {
        return pcs.get(currentPcIndex);
    }

    public Killshot[] getKillShotTrack() {
        return killShotTrack;
    }

    private void initDecks() {
        //TODO
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
        if (currentPcIndex == pcs.size() - 1)
            currentPcIndex = 0;
        currentPcIndex++;
        remainigActions=2;
    }

    public Tile[][] getMap(){
        return map.clone();
    }

    /* public GenerationTile respawnpoint(CharacterColourEnum colour) {
        //TODO: IMPLEMENTA LA RICERCA DEL GENERATION TILE DI QUEL COLORE
        return new GenerationTile(0,0,null);
    }

     */
}
