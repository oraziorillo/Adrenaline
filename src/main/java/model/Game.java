package model;

import java.util.*;

public class Game {
    private short remainigActions;
    private int currentPcIndex;
    private ArrayList<Pc> pcs;
    private Killshot[] killShotTrack;
    Deck<AmmoCard> ammosDeck;
    Deck<WeaponCard> weaponsDeck;
    Deck<PowerUpCard> powerUpsDeck;
    private final /*scegli tu il tipo che ti fa comodo*/ spawnTiles;
    private final Tile[][] map;

    public Game(String jsonName) {
        remainigActions = 2;
        currentPcIndex = 0;
        pcs = new ArrayList<>(1);
        killShotTrack = new Killshot[8];
        initDecks();
        initMap();
    }

    /**
     *
     * @param colourOfMapTile
     * @param row
     * @param coloumn
     * @param doorsInMap
     */ //bisogna aggiungere o un altro parametro in ingresso che specifichi il tipo di tile o rendiamo tile una classe concreta
    public void initMap(TileColourEnum[] colourOfMapTile, int row, int coloumn, int[] doorsInMap){
        int k;
        List<TileColourEnum> tempList = new LinkedList<>();
        if(colourOfMapTile.length != row*coloumn){
            throw new IllegalArgumentException("This list doesn't have the right dimension");
        }
        spawnTiles = new /*tipo che hai scelto*/;
        //TODO inizializzazione di spawnTiles tramite file json
        for(int i = 0; i < row; i++){
            for(int j = 0; j < coloumn; j++){
                if (/* qui va inserita la condizione per cui il punto (i,j) sia nell'array spawnTiles*/)) {
                    map[i][j] = new SpawnTile(i, j, colourOfMapTile[i*row + j], weaponsDeck);
                } else {
                    map[i][j] = new AmmoTile(i, j, colourOfMapTile[i*row + j], ammosDeck);
                }
            }
        }
        /*vanno definiti i metodi in rosso se vuoi continuare ad usarli*/
        for(int i = 0; i < row; i++){
            for( int j = 0; j < coloumn; j++){
                tempList.add(map[i][j].getTileColour());
                while(doorsInMap.contains(i*row+j) && doorsInMap.indexOf(i*row+j)%2==0){
                    k = doorsInMap[i*row+j+1];
                    tempList.add(map[k%row][k/coloumn].getTileColour());
                    doorsInMap.remove(i*row+j);
                    doorsInMap.remove(i*row+j+1);
                }
                for(int m = 0; m < row; m++){
                    for(int n = 0; n < coloumn; n++){
                        if(tempList.contains(map[m][n].getTileColour())){
                            map[i][j].addVisible(map[m][n]);
                        }
                    }
                }
                tempList.clear();
            }
        }

    }

    private void initDecks() {
        //TODO
    }

    public Tile getTile (int x, int y){
        return map[x][y];
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

    public List<Pc> getPcs(){
        return pcs;
    }

    public Killshot[] getKillShotTrack() {
        return killShotTrack;
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

    /* public SpawnTile respawnpoint(TileColourEnum colour) {
        //TODO: IMPLEMENTA LA RICERCA DEL GENERATION TILE DI QUEL COLORE
        return new GenerationTile(0,0,null);
    }

     */
}
