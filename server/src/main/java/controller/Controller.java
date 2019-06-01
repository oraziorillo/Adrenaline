package controller;

import common.RemoteController;
import controller.player.Player;
import controller.states.SetupMapState;
import controller.states.State;
import enums.PcColourEnum;
import exceptions.NotCurrPlayerException;
import model.Game;
import model.Pc;
import model.squares.Square;
import model.WeaponCard;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller extends UnicastRemoteObject implements RemoteController {

    private static final int FIRST_MAP = 1;
    private static final int LAST_MAP = 4;
    private static final int MIN_KILL_SHOT_TRACK_SIZE = 5;
    private static final int MAX_KILL_SHOT_TRACK_SIZE = 8;
    private static final int ACTIONS_PER_TURN = 2;
    private static final int ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER = 1;

    private boolean finalFrenzy = false;
    private Game game;
    private ArrayList<PcColourEnum> availablePcColours;
    private int remainingActions;
    private int currPlayerIndex;
    private int lastPlayerIndex; //to set when final frenzy starts
    private State currState;
    private WeaponCard currWeapon;
    private ArrayList<Player> players;
    private ArrayList<Square> squaresToRefill;

    public Controller(List<Player> players) throws RemoteException {
        super();
        this.game = new Game();
        this.players = new ArrayList<>();
        this.squaresToRefill = new ArrayList<>();
        this.availablePcColours = new ArrayList<>();
        Collections.addAll(availablePcColours, PcColourEnum.values());
        this.players.addAll(players);
        this.currPlayerIndex = 0;
        this.currState = new SetupMapState(this);
    }

    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    public Game getGame() {
        return game;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public synchronized Pc getCurrPc() {
        return players.get(currPlayerIndex).getPc();
    }

    public int getCurrPlayerIndex() {
        return currPlayerIndex;
    }

    public int getRemainingActions() {
        return remainingActions;
    }

    public WeaponCard getCurrWeapon() {
        return currWeapon;
    }

    public void resetCurrWeapon() { this.currWeapon = null; }

    public List<Square> getSquaresToRefill(){
        return squaresToRefill;
    }

    public void setFinalFrenzy(boolean booleanValue) {
        finalFrenzy = booleanValue;
    }

    public void setLastPlayerIndex(int index) {
        lastPlayerIndex = index;
    }

    public void setCurrWeapon(WeaponCard weapon) {
        this.currWeapon = weapon;
    }

    public void decreaseRemainingActions() {
        this.remainingActions--;
    }

    public void resetRemainingActions() {
        if (!isFinalFrenzy() || beforeFirstPlayer(getCurrPlayerIndex()))
            this.remainingActions = ACTIONS_PER_TURN;
        else
            this.remainingActions = ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER;
    }

    public synchronized void addSquareToRefill(Square s) {
        squaresToRefill.add(s);
    }

    public synchronized void resetSquaresToRefill() {
        squaresToRefill.clear();
    }

    /**
     * checks if the player who is trying to do something is the able to do it in this moment
     *
     * @throws NotCurrPlayerException iff the player who called a method is not able to do it in this moment
     */
    public synchronized void validateCurrPlayer() throws NotCurrPlayerException {
        //TODO: deve prendere come arg un token
        //      deve sollevare un eccezione NotCurrPlayerException
        //      aggiungere try catch a tutti i metodi
    }


    @Override
    public synchronized void chooseMap(int n) {
        if (n >= FIRST_MAP && n <= LAST_MAP)
            currState.selectMap(n);
    }


    @Override
    public synchronized void chooseNumberOfSkulls(int n) {
        if (n >= MIN_KILL_SHOT_TRACK_SIZE && n <= MAX_KILL_SHOT_TRACK_SIZE)
            currState.selectNumberOfSkulls(n);
    }


    @Override
    public synchronized void choosePcColour(PcColourEnum colour) {
        if (availablePcColours.contains(colour)) {
            currState.selectPcForPlayer(colour, players.get(currPlayerIndex));
            availablePcColours.remove(colour);
            nextTurn();
            if (currPlayerIndex == 0)
                currState = currState.nextState();
        }
    }


    @Override
    public synchronized void runAround() {
        if (currState.runAround()){
            currState = currState.nextState();
        }
    }


    @Override
    public synchronized void grabStuff() {
        if (currState.grabStuff()){
            currState = currState.nextState();
        }
    }


    @Override
    public synchronized void shootPeople() {
        if (currState.shootPeople()){
            currState = currState.nextState();
        }
    }


    @Override
    public synchronized void usePowerUp() {
        if (currState.usePowerUp()){
            currState = currState.nextState();
        }
    }

    @Override
    public synchronized void chooseSquare(int x, int y) {
        Square chosenSquare = game.getSquare(x, y);
        if (chosenSquare != null)
            currState.selectSquare(chosenSquare);
    }

    @Override
    public synchronized void choosePowerUp(int index) {
        if (index >= 0 && index <= 2)
            currState.selectPowerUp(index);
    }


    @Override
    public synchronized void chooseWeaponOnSpawnPoint(int index) {
        if (index >= 0 && index <= 2)
            currState.selectWeaponOnBoard(index);
    }


    @Override
    public synchronized void chooseWeaponOfMine(int index) {
        if (index >= 0 && index <= 2)
            currState.selectWeaponOfMine(index);
    }


    @Override
    public synchronized void switchFireMode() {
        if(currWeapon.getFireModes().size() > 1)
            currState.switchFireMode(currWeapon);
    }

    @Override
    public synchronized void upgrade() {
        if(!currWeapon.getUpgrades().isEmpty())
            currState.upgrade(currWeapon);
    }

    @Override
    public synchronized void removeUpgrade() {
        currState.removeUpgrade(currWeapon);
    }


    @Override
    public synchronized void chooseAsynchronousEffectOrder(boolean beforeBasicEffect) {
        currState.setAsynchronousEffectOrder(currWeapon, beforeBasicEffect);
    }

    @Override
    public synchronized void skip() {
        if (currState.skipAction())
            currState = currState.nextState();
    }

    //se nello stato in cui siamo il comando di undo non ci fa cambiare stato
    // ma resettare quello in cui ci troviamo, restituisce false
    @Override
    public synchronized void undo() {
        if (currState.undo())
            currState = currState.nextState();
    }

    @Override
    public synchronized void ok() {
        if (currState.ok())
            currState = currState.nextState();
    }

    @Override
    public synchronized void reload() {
        if (currState.reload())
            currState = currState.nextState();
    }

    @Override
    public synchronized void pass() {
        if (currState.ok()) {
            nextTurn();
            currState = currState.nextState();
        }
    }

    @Override
    public synchronized void quit() {
        //gestire la disconnessione in modo tale da far saltare il turno al giocatore
    }


    @Override
    public boolean isOpened() {
        return true;
    }


    public synchronized void nextTurn() {
        if (currPlayerIndex == players.size() - 1)
            currPlayerIndex = 0;
        else
            currPlayerIndex++;
    }


    public synchronized boolean beforeFirstPlayer(int playerIndex) {
        return playerIndex > lastPlayerIndex;
    }
}
