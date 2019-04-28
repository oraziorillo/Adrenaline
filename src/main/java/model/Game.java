package model;

import model.Enumerations.TileColourEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private short remainigActions;
    private int currentPcIndex;
    private ArrayList<Pc> pcs;
    private Killshot[] killShotTrack;
    Deck<AmmoCard> ammosDeck = new Deck<>(this);
    Deck<WeaponCard> weaponsDeck = new Deck<>(this);
    Deck<PowerUpCard> powerUpsDeck = new Deck<>(this);
    private ArrayList<Tile> spawnTiles;
    /**
     * @author matteo
     * @implNote Forse fare davvero una classe Map potrebbe snellire molto il codice generale, togliendo ad esempio molti compiti a Tile
     */
    public Tile[][] map;

    public Game(String jsonName) {
        remainigActions = 2;
        currentPcIndex = 0;
        pcs = new ArrayList<>(0);
        spawnTiles = new ArrayList<>();
        killShotTrack = new Killshot[8];
        try {
            WeaponCard weaponCard;
            JSONObject jsonObject = (JSONObject) Server.readJson(jsonName);
            JSONObject[] jsonWeapons = (JSONObject[]) jsonObject.get("weapons");
            for (JSONObject jsonWeapon : jsonWeapons) {
                weaponCard = new WeaponCard(jsonWeapon, weaponsDeck);
                weaponsDeck.add(weaponCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param colourOfMapTile
     * @param rows number of rows of the map to create
     * @param coloumns number of rows of the map to create
     * @param doorsInMap
     */
    // I paramteri che il metodo riceve sono così strutturati:
    // numero di righe, numero di colonne
    // array di TileColourEnum che definisce il colore di ogni Tile della mappa. Se un dato Tile è null, lo sarà anche nell'array
    // array di int per ogni Tile della mappa: se int vale 0 corrisponde ad un tile null, se vale 1 corrisponde ad un ammoTile, se vale 2 ad uno spawnTile
    // array di int dove, per ogni tile, per ogni porta che possiede, c'è una coppia di numeri consecutivi che indica il tile corrente e il tile a cui è collegato tramite porta
    public void initMap(int rows, int coloumns, TileColourEnum[] colourOfMapTile, int[] typeOfTile, int[] doorsInMap){
        ArrayList<TileColourEnum> tileColourList;
        ArrayList<TileColourEnum> tempList = new ArrayList<>();
        ArrayList<Integer> doorsList, typeOfTileList;
        tileColourList = (ArrayList<TileColourEnum>) Arrays.asList(colourOfMapTile);
        doorsList = (ArrayList<Integer>) Arrays.stream(doorsInMap).boxed().collect(Collectors.toList());
        typeOfTileList = (ArrayList<Integer>) Arrays.stream(typeOfTile).boxed().collect(Collectors.toList());
        if(tileColourList.size() != rows*coloumns || typeOfTileList.size() != rows*coloumns){
            throw new IllegalArgumentException("This list doesn't have the right dimension");
        }
        map = new Tile[rows][coloumns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < coloumns; j++){
                if (typeOfTileList.get(i*rows+j) == 1) {
                    map[i][j] = new AmmoTile(i, j, tileColourList.get(i*rows+j), ammosDeck);
                }
                else if(typeOfTileList.get(i*rows+j) == 2){
                    map[i][j] = new SpawnTile(i, j, tileColourList.get(i*rows+j), weaponsDeck);
                    spawnTiles.add(map[i][j]);
                }
                else {
                    map[i][j] = null;   //istruzione inserita per chiarezza, ma omissibile
                }
            }
        }
        for(int i = 0; i < rows; i++){
            for( int j = 0; j < coloumns; j++){
                if(map[i][j] != null) {
                    tempList.add(map[i][j].getTileColour());
                    while (doorsList.contains(i * rows + j) && doorsList.indexOf(i * rows + j) % 2 == 0) {
                        int k = doorsList.get(i * rows + j + 1);
                        tempList.add(map[k / rows][k % coloumns].getTileColour());
                        doorsList.remove(i * rows + j);
                        doorsList.remove(i * rows + j);
                    }
                }
                for(int m = 0; m < rows; m++){
                    for(int n = 0; n < coloumns; n++){
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


     public void respawnCurrentPc(TileColourEnum colour) {
        Optional<Tile> t;
        Pc currentPc;
        t = spawnTiles.stream().filter(elem -> elem.getTileColour() == colour).findFirst();
        currentPc = pcs.get(currentPcIndex);
        t.get().addPc(currentPc);
        currentPc.respawn(t.get());
    }


}
