package model;

import controller.Server;
import enums.PcColourEnum;
import enums.SquareColourEnum;
import exceptions.HoleInMapException;
import model.squares.Square;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.*;

import static model.Constants.WEAPONS_JSON_NAME;

public class Game {
    private ArrayList<Pc> pcs;
    private String message;
    private GameBoard gameBoard;
    Deck<AmmoTile> ammoDeck;
    Deck<WeaponCard> weaponsDeck;
    Deck<PowerUpCard> powerUpsDeck;


    public Game() {
        this.message = "";
        this.pcs = new ArrayList<>();
        this.weaponsDeck = new Deck<>();
        this.powerUpsDeck = new Deck<>();
        this.ammoDeck = new Deck<>();
        initDecks();

    }


    public  String getMessage(){
        return message;
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


    public void setMessage(String s){
        this.message = s;
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
        gameBoard.initMap(numberOfMap);
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
