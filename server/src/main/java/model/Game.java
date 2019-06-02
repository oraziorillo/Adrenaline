package model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import enums.PcColourEnum;
import enums.SquareColourEnum;
import model.actions.Action;
import model.deserializers.ActionDeserializer;
import model.deserializers.GameBoardDeserializer;
import model.squares.Square;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * This class represents an ADRENALINE game
 */
public class Game {
    private GameBoard gameBoard;
    private Deck<WeaponCard> weaponsDeck;
    private Deck<PowerUpCard> powerUpsDeck;
    private Deck<AmmoTile> ammoDeck;


    public Game() throws FileNotFoundException {
        this.weaponsDeck = new Deck<>();
        this.powerUpsDeck = new Deck<>();
        this.ammoDeck = new Deck<>();
        initDecks();
    }

    /**
     * Loads a map given the index
     * @param numberOfMap the index of the map
     */
    public void initMap(int numberOfMap){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonArray gameBoards = customGson.fromJson("game_boards.json", JsonArray.class);
        gameBoard = customGson.fromJson(gameBoards.get(numberOfMap), GameBoard.class);

        gameBoard.assignProperDeckToEachSquare(weaponsDeck, ammoDeck);
    }

    /**
     * Inits the KillShotTrack with the given number of skulls
     * @param numberOfSkulls The desired number of skulls
     */
    public void initKillShotTrack(int numberOfSkulls){
        gameBoard.initKillShotTrack(numberOfSkulls);
    }


    private void initDecks() throws FileNotFoundException {
        initWeaponsDeck();
        initPowerUpsDeck();
        initAmmoDeck();
    }


    private void initWeaponsDeck() throws FileNotFoundException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Action.class, new ActionDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        Type weaponsType = new TypeToken<ArrayList<WeaponCard>>(){}.getType();
        JsonReader reader = new JsonReader(new FileReader("/home/orazio/Documents/ids_progetto/ing-sw-2019-23/json/weapons.json"));
        ArrayList<WeaponCard> weapons = customGson.fromJson(reader, weaponsType);

        weapons.forEach(w -> weaponsDeck.add(w));
        weaponsDeck.getCards().forEach(WeaponCard::init);
    }


    private void initPowerUpsDeck(){
        //TODO
    }


    private void initAmmoDeck(){
        //TODO
    }


    public Square getSquare(int row, int col){
        return gameBoard.getSquare(row, col);
    }


    public Square getSpawnPoint(SquareColourEnum requiredColour) {
        return gameBoard.getSpawnPoint(requiredColour);
    }


    public Deck<WeaponCard> getWeaponsDeck() {
        return weaponsDeck;
    }

    public void setTargetableSquares(Set<Square> targetableSquares, boolean isTargetable){
        if (targetableSquares.isEmpty())
            return;
        targetableSquares.forEach(s -> s.setTargetable(isTargetable));
    }


    PowerUpCard drawPowerUp(){
        return powerUpsDeck.draw();
    }

    /**
     * Marks a kill on the KillShotTrack
     * @param killerColour the colour of the player who performed the kill
     * @param overkilled true if the player was overkilled, see the manual
     */
    public void killOccured(PcColourEnum killerColour, Boolean overkilled){
        gameBoard.killOccured(killerColour, overkilled);
    }

}





