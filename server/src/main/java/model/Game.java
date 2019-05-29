package model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import controller.Server;
import enums.PcColourEnum;
import enums.SquareColourEnum;
import exceptions.HoleInMapException;
import model.squares.AmmoSquare;
import model.squares.SpawnPoint;
import model.squares.Square;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static model.Constants.WEAPONS_JSON_NAME;

public class Game {
    private ArrayList<Pc> pcs;
    private GameBoard gameBoard;
    private Deck<WeaponCard> weaponsDeck;
    private Deck<PowerUpCard> powerUpsDeck;
    private Deck<AmmoTile> ammoDeck;


    public Game() {
        this.pcs = new ArrayList<>();
        this.gameBoard = new GameBoard();
        this.weaponsDeck = new Deck<>();
        this.powerUpsDeck = new Deck<>();
        this.ammoDeck = new Deck<>();
        initDecks();
    }


    public List<Pc> getPcs(){
        return pcs;
    }


    public Square getSquare(int x, int y) throws HoleInMapException {
        return gameBoard.getSquare(x, y);
    }


    public Square getSpawnPoint(SquareColourEnum requiredColour) {
        return gameBoard.getSpawnPoint(requiredColour);
    }


    public int mapRows(){
        return gameBoard.rows();
    }


    public int mapColumns(){
        return gameBoard.columns();
    }


    public void setTargetableSquares(Set<Square> targetableSquares, boolean isTargetable){
        if (targetableSquares.isEmpty())
            return;
        targetableSquares.forEach(s -> s.setTargetable(isTargetable));
    }


    public void addPc(Pc pc){
        pcs.add(pc);
    }


    public void initMap(int numberOfMap){
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<GameBoard> deserializer = ...; // will implement in a second
        gsonBuilder.registerTypeAdapter(GameBoard.class, deserializer);
        Gson customGson = gsonBuilder.create();
        gameBoard = customGson.fromJson("gameBoard", GameBoard.class);

        Gson gson = new Gson();
        Type mapType = new TypeToken<ArrayList<Square>>(){}.getType();
        gameBoard = gson.fromJson("map", mapType);
    }


    public void initKillShotTrack(int numberOfSkulls){
        gameBoard.initKillShotTrack(numberOfSkulls);
    }


    private void initDecks() {
        initWeaponsDeck();
        initPowerUpDeck();
        initAmmosDeck();
    }


    private void initWeaponsDeck(){
        try {
            WeaponCard weaponCard;
            JSONArray jsonWeapons = (JSONArray) Server.readJson(WEAPONS_JSON_NAME);
            for (Object jsonWeapon : jsonWeapons) {
                weaponCard = new WeaponCard((JSONObject)jsonWeapon);
                weaponsDeck.add(weaponCard);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    private void initPowerUpDeck(){
        //TODO
    }


    private void initAmmosDeck(){
        //TODO
    }


    public void killOccured(PcColourEnum killerColour, Boolean overkilled){
        gameBoard.killOccured(killerColour, overkilled);
    }


}


class GameBoardDeserializer implements JsonDeserializer<GameBoard> {

    @Override
    public GameBoard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonArray gameBoards = json.getAsJsonArray();

        JsonObject myGameBoard = gameBoards.get(numberOfMap).getAsJsonObject();

        int rows = myGameBoard.get("rows").getAsInt();

        int columns = myGameBoard.get("columns").getAsInt();

        ArrayList<SpawnPoint> spawnPoints = new ArrayList<>();

        JsonArray squares = myGameBoard.get("squares").getAsJsonArray();

        squares.forEach(s -> {

            JsonObject currSquare = s.getAsJsonObject();

            boolean isSpawnPoint = currSquare.get("isSpawnPoint").getAsBoolean();

            if (isSpawnPoint){
                SpawnPoint mySpawnPoint = new SpawnPoint(
                        currSquare.get("x").getAsInt(),
                        currSquare.get("y").getAsInt(),
                        SquareColourEnum.valueOf(currSquare.get("colour").getAsString()),
                        weaponsDeck
                );
                spawnPoints.add(mySpawnPoint);

            } else {
                AmmoSquare myAmmoSquare = new AmmoSquare(
                        currSquare.get("x").getAsInt(),
                        currSquare.get("y").getAsInt(),
                        SquareColourEnum.valueOf(currSquare.get("colour").getAsString()),
                        ammoDeck
                );
            }
        });

    }
}
