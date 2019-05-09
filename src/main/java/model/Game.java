package model;

import controller.Server;
import model.enumerations.AmmoEnum;
import model.enumerations.PcColourEnum;
import model.enumerations.TileColourEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static model.Constants.WEAPONS_JSON_NAME;

public class Game {
    private int currentKillShotTrackIndex;
    private ArrayList<Pc> pcs;
    private KillShot[] killShotTrack;
    private String message;
    Deck<AmmoCard> ammosDeck;
    Deck<WeaponCard> weaponsDeck;
    Deck<PowerUpCard> powerUpsDeck;
    private ArrayList<Tile> spawnTiles;
    public Tile[][] map;

    public Game() {
        pcs = new ArrayList<>();
        spawnTiles = new ArrayList<>();
        killShotTrack = new KillShot[8];
        ammosDeck = new Deck<>(this);
        weaponsDeck = new Deck<>(this);
        powerUpsDeck = new Deck<>(this);
    }

    public  String getMessage(){
        return message;
    }

    public List<Pc> getPcs(){
        return pcs;
    }

    public void setKillShotTrack(int numberOfSkulls){
        //il controllo sulla validità del parametro è già effettuato dal controller
        for(int i = 0; i < numberOfSkulls; i++){
            killShotTrack[i] = new KillShot();
        }
        currentKillShotTrackIndex = numberOfSkulls - 1;
    }

    public void setMessage(String s){
        message = s;
    }

    /**
     *
     * @param colourOfMapTile
     * @param rows number of rows of the map to create
     * @param columns number of rows of the map to create
     * @param doorsInMap
     */

    /**
    // I paramteri che il metodo riceve sono così strutturati:
    // numero di righe, numero di colonne
    // array di TileColourEnum che definisce il colore di ogni Tile della mappa. Se un dato Tile è null, lo sarà anche nell'array
    // array di int per ogni Tile della mappa: se int vale 0 corrisponde ad un tile null, se vale 1 corrisponde ad un ammoTile, se vale 2 ad uno spawnTile
    // array di int dove, per ogni tile, per ogni porta che possiede, c'è una coppia di numeri consecutivi che indica il tile corrente e il tile a cui è collegato tramite porta
    */
    public void initMap(int rows, int columns, TileColourEnum[] colourOfMapTile, int[] typeOfTile, int[] doorsInMap){
        ArrayList<TileColourEnum> tileColourList;
        ArrayList<TileColourEnum> tempList = new ArrayList<>();
        ArrayList<Integer> doorsList, typeOfTileList;
        tileColourList = (ArrayList<TileColourEnum>) Arrays.asList(colourOfMapTile);
        doorsList = (ArrayList<Integer>) Arrays.stream(doorsInMap).boxed().collect(Collectors.toList());
        typeOfTileList = (ArrayList<Integer>) Arrays.stream(typeOfTile).boxed().collect(Collectors.toList());
        if(tileColourList.size() != rows*columns || typeOfTileList.size() != rows*columns){
            throw new IllegalArgumentException("This list doesn't have the right dimension");
        }
        map = new Tile[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
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
            for( int j = 0; j < columns; j++){
                if(map[i][j] != null) {
                    tempList.add(map[i][j].getTileColour());
                    while (doorsList.contains(i * rows + j) && doorsList.indexOf(i * rows + j) % 2 == 0) {
                        int k = doorsList.get(i * rows + j + 1);
                        tempList.add(map[k / rows][k % columns].getTileColour());
                        doorsList.remove(i * rows + j);
                        doorsList.remove(i * rows + j);
                    }
                }
                for(int m = 0; m < rows; m++){
                    for(int n = 0; n < columns; n++){
                        if(map[m][n] != null && tempList.contains(map[m][n].getTileColour())){
                            map[i][j].addVisible(map[m][n]);
                        }
                    }
                }
                tempList.clear();
            }
        }
    }

    public void initDecks() {
        initWeaponsDeck();
        initPowerUpDeck();
        initAmmosDeck();
    }

    private void initWeaponsDeck(){
        try {
            WeaponCard weaponCard;
            JSONArray jsonWeapons = (JSONArray) Server.readJson(WEAPONS_JSON_NAME);
            for (Object jsonWeapon : jsonWeapons) {
                weaponCard = new WeaponCard((JSONObject)jsonWeapon, this);
                weaponsDeck.add(weaponCard);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void initPowerUpDeck(){
        for(int i = 0; i < 3; i++){
            AmmoEnum ammoColour = AmmoEnum.values()[i];
            for(int j = 0; j < 2; j++){
                powerUpsDeck.add(new Newton(ammoColour));
                powerUpsDeck.add(new TargetingScope(ammoColour));
                powerUpsDeck.add(new TagbackGrenade(ammoColour));
                powerUpsDeck.add(new Teleporter(ammoColour));
            }
        }
    }

    private void initAmmosDeck(){
        //TODO
    }

    public KillShot[] getKillShotTrack() {
        return killShotTrack;
    }

    public void killHappened(PcColourEnum colourEnum, Boolean overkilled){
        killShotTrack[currentKillShotTrackIndex].kill(colourEnum, overkilled);
        currentKillShotTrackIndex--;
        if(currentKillShotTrackIndex < 0){
            //call observer to notify state change in finalFrenzy;
        }
    }

    public Tile getTile (int x, int y){
        return map[x][y];
    }

    public ArrayList<Tile> getSpawnTiles(){
        return spawnTiles;
    }

    public void addPc(Pc pc){
        pcs.add(pc);
    }


}
