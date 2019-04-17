package model;

import model.Enumerations.TileColourEnum;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private short remainigActions;
    private int currentPcIndex;
    private ArrayList<Pc> pcs;
    private Killshot[] killShotTrack;
    Deck<AmmoCard> ammosDeck = new Deck<AmmoCard>(this);
    Deck<WeaponCard> weaponsDeck = new Deck<WeaponCard>(this);
    Deck<PowerUpCard> powerUpsDeck = new Deck<PowerUpCard>(this);
    private final /*scegli tu il tipo che ti fa comodo*/ spawnTiles;        //a che serve questo spawnTiles?? MATTEO: condivido il dubbio
    /**
     * @author matteo
     * @implNote Forse fare davvero una classe Map potrebbe snellire molto il codice generale, togliendo ad esempio molti compiti a Tile
     */
    public final Tile[][] map;

    public Game(String jsonName) {
        remainigActions = 2;
        currentPcIndex = 0;
        pcs = new ArrayList<>(0);
        killShotTrack = new Killshot[8];
        try {
            WeaponCard weaponCard;
            JSONObject jsonObject = (JSONObject) Server.readJson(jsonName);
            for (int i = 1; jsonObject.get("weapon" + i) != null; i++) {
                weaponCard = new WeaponCard((JSONObject) jsonObject.get("fireMode" + i), weaponsDeck);
                weaponsDeck.add(weaponCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param colourOfMapTile
     * @param row number of rows of the map to create
     * @param coloumn number of rows of the map to create
     * @param doorsInMap
     */
    // I paramteri che il metodo riceve sono così strutturati:
    // numero di righe, numero di colonne
    // array di TileColourEnum che definisce il colore di ogni Tile della mappa. Se un dato Tile è null, lo sarà anche nell'array
    // array di int per ogni Tile della mappa: se int vale 0 corrisponde ad un tile null, se vale 1 corrisponde ad un ammoTile, se vale 0 ad uno spawnTile
    // array di int dove, per ogni tile, per ogni porta che possiede, c'è una coppia di numeri consecutivi che indica il tile corrente e il tile a cui è collegato tramite porta
    public void initMap(int row, int coloumn, TileColourEnum[] colourOfMapTile, int[] typeOfTile, int[] doorsInMap){
        ArrayList<TileColourEnum> tileColourList;
        ArrayList<TileColourEnum> tempList = new ArrayList<>();
        ArrayList<Integer> doorsList, typeOfTileList;
        tileColourList = (ArrayList<TileColourEnum>) Arrays.asList(colourOfMapTile);
        doorsList = (ArrayList<Integer>) Arrays.stream(doorsInMap).boxed().collect(Collectors.toList());
        typeOfTileList = (ArrayList<Integer>) Arrays.stream(typeOfTile).boxed().collect(Collectors.toList());
        int k;
        if(tileColourList.size() != row*coloumn || typeOfTileList.size() != row*coloumn){
            throw new IllegalArgumentException("This list doesn't have the right dimension");
        }
        for(int i = 0; i < row; i++){
            for(int j = 0; j < coloumn; j++){
                if (typeOfTileList.get(i*row+j) == 1) {
                    map[i][j] = new SpawnTile(i, j, tileColourList.get(i*row+j), weaponsDeck);
                }
                else if(typeOfTileList.get(i*row+j) == 2){
                    map[i][j] = new AmmoTile(i, j, tileColourList.get(i*row+j), ammosDeck);
                }
                else {
                    map[i][j] = null;   //istruzione inserita per chiarezza, ma omissibile
                }
            }
        }
        for(int i = 0; i < row; i++){
            for( int j = 0; j < coloumn; j++){
                if(map[i][j] != null) {
                    tempList.add(map[i][j].getTileColour());
                    while (doorsList.contains(i * row + j) && doorsList.indexOf(i * row + j) % 2 == 0) {
                        k = doorsList.get(i * row + j + 1);
                        tempList.add(map[k / row][k % coloumn].getTileColour());
                        doorsList.remove(i * row + j);
                        doorsList.remove(i * row + j);
                    }
                }
                for(int m = 0; m < row; m++){
                    for(int n = 0; n < coloumn; n++){
                        if(map[m][n] != null && tempList.contains(map[m][n].getTileColour())){
                            map[i][j].addVisible(map[m][n]);
                        }
                    }
                }
                tempList.clear();
            }
        }

    }

    /**
     * @author matteo
     * @implNote orazio, il mazzo armi lo carichiamo da Json?
     */
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


    /* public SpawnTile respawnpoint(TileColourEnum colour) {
        //TODO: IMPLEMENTA LA RICERCA DEL GENERATION TILE DI QUEL COLORE
        //MATTEO: A cosa ci serve? non ci basta fare respawn su map[x][y]?
        return new GenerationTile(0,0,null);
    }

     */
}
