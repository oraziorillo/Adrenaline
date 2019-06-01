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

public class Game {
    private ArrayList<Pc> pcs;
    private GameBoard gameBoard;
    private Deck<WeaponCard> weaponsDeck;
    private Deck<PowerUpCard> powerUpsDeck;
    private Deck<AmmoTile> ammoDeck;


    public Game() throws FileNotFoundException {
        this.pcs = new ArrayList<>();
        this.weaponsDeck = new Deck<>();
        this.powerUpsDeck = new Deck<>();
        this.ammoDeck = new Deck<>();
        initWeaponsDeck();
        //todo initDecks();
    }


    public void initMap(int numberOfMap){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameBoard.class, new GameBoardDeserializer());
        Gson customGson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();

        JsonArray gameBoards = customGson.fromJson("game_boards.json", JsonArray.class);
        gameBoard = customGson.fromJson(gameBoards.get(numberOfMap), GameBoard.class);

        gameBoard.assignDecks(weaponsDeck, ammoDeck);
    }


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


    public void addPc(Pc pc){
        pcs.add(pc);
    }



    PowerUpCard drawPowerUp(){
        return powerUpsDeck.draw();
    }


    public void killOccured(PcColourEnum killerColour, Boolean overkilled){
        gameBoard.killOccured(killerColour, overkilled);
    }

}





