package model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import controller.Server;
import enums.PcColourEnum;
import enums.SquareColourEnum;
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
        this.weaponsDeck = new Deck<>();
        this.powerUpsDeck = new Deck<>();
        this.ammoDeck = new Deck<>();
        initDecks();
    }


    public void initMap(int numberOfMap){
        Gson gson = new Gson();

        JsonArray gameBoards = gson.fromJson("game_boards", JsonArray.class);

        JsonObject myJsonGameBoard = gameBoards.get(numberOfMap).getAsJsonObject();

        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonDeserializer<Square> squareDeserializer = new SquareDeserializer();
        JsonDeserializer<GameBoard> gameBoardDeserializer = new GameBoardDeserializer();
        gsonBuilder.registerTypeAdapter(Square.class, squareDeserializer);
        gsonBuilder.registerTypeAdapter(GameBoard.class, gameBoardDeserializer);

        Gson customGson = gsonBuilder.create();
        gameBoard = customGson.fromJson(myJsonGameBoard, GameBoard.class);
    }


    public void initKillShotTrack(int numberOfSkulls){
        gameBoard.initKillShotTrack(numberOfSkulls);
    }


    private void initDecks() {
        initWeaponsDeck();
        initPowerUpsDeck();
        initAmmoDeck();
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


    private void initPowerUpsDeck(){
        //TODO
    }


    private void initAmmoDeck(){
        //TODO
    }


    public Square getSquare(int x, int y){
        return gameBoard.getSquare(x, y);
    }


    public Square getSpawnPoint(SquareColourEnum requiredColour) {
        return gameBoard.getSpawnPoint(requiredColour);
    }


    public void setTargetableSquares(Set<Square> targetableSquares, boolean isTargetable){
        if (targetableSquares.isEmpty())
            return;
        targetableSquares.forEach(s -> s.setTargetable(isTargetable));
    }


    public void addPc(Pc pc){
        pcs.add(pc);
    }



    PowerUpCard drawPowerUp(){
        return powerUpsDeck.draw();
    }


    public void killOccured(PcColourEnum killerColour, Boolean overkilled){
        gameBoard.killOccured(killerColour, overkilled);
    }


    class SquareDeserializer implements JsonDeserializer<Square> {

        @Override
        public Square deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject currSquare = json.getAsJsonObject();

            boolean isSpawnPoint = currSquare.get("isSpawnPoint").getAsBoolean();

            if (isSpawnPoint)
                return new SpawnPoint(
                        currSquare.get("x").getAsInt(),
                        currSquare.get("y").getAsInt(),
                        SquareColourEnum.valueOf(currSquare.get("colour").getAsString()),
                        weaponsDeck
                );
            else
                return new AmmoSquare(
                        currSquare.get("x").getAsInt(),
                        currSquare.get("y").getAsInt(),
                        SquareColourEnum.valueOf(currSquare.get("colour").getAsString()),
                        ammoDeck
                );
        }
    }


    class GameBoardDeserializer implements JsonDeserializer<GameBoard> {

        @Override
        public GameBoard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonGameBoard = json.getAsJsonObject();
            Gson gson = new Gson();

            Type squaresType = new TypeToken<ArrayList<Square>>(){}.getType();

            JsonArray jsonDoors = gson.fromJson(jsonGameBoard.get("doors"), JsonArray.class);
            int[] doors = new int[jsonDoors.size()];
            for (int i = 0; i < jsonDoors.size(); i++)
                doors[i] = jsonDoors.get(i).getAsInt();

            return new GameBoard(
                    jsonGameBoard.get("rows").getAsInt(),
                    jsonGameBoard.get("columns").getAsInt(),
                    gson.fromJson(jsonGameBoard.get("squares"), squaresType),
                    doors
            );
        }
    }
}





