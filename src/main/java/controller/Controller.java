package controller;

import exceptions.NotCurrPlayerException;
import model.Game;
import model.Pc;
import model.enumerations.PcColourEnum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

public class Controller extends UnicastRemoteObject implements RemoteController {

    static final int FIRST_MAP = 1;
    static final int LAST_MAP = 4;
    static final int MIN_KILL_SHOT_TRACK_SIZE = 5;
    static final int MAX_KILL_SHOT_TRACK_SIZE = 8;
    private static final int ACTIONS_PER_TURN = 2;
    private static final int ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER = 1;


    private boolean finalFrenzy = false;
    private boolean firstTurn = false;
    private Game game;
    private ArrayList<PcColourEnum> availablePcColours;
    private int remainingActions;
    private int currPlayerIndex;
    private int lastPlayerIndex; //to set when final frenzy starts
    private State currState;
    ArrayList<Player> players;
    State setupMapState, setupKillShotTrackState, pcChoiceState, firstTurnState,
          startTurnState, runAroundState, grabStuffState, collectWeaponState, shootState, endTurn;

    public Controller(ArrayList<Player> players) throws RemoteException {
        super();
        this.game = new Game();
        this.players = new ArrayList<>();
        this.availablePcColours = new ArrayList<>();
        Collections.addAll(availablePcColours, PcColourEnum.values());
        this.players.addAll(players);
        this.currPlayerIndex = 0;
        this.setupMapState = new SetupMapState(this);
        this.setupKillShotTrackState = new SetupKillShotTrackState(this);
        this.pcChoiceState = new PcChoiceState(this);
        this.firstTurnState = new FirstTurnState(this);
        this.startTurnState = new StartTurnState(this);
        this.runAroundState = new RunAroundState(this);
        this.grabStuffState = new GrabStuffState(this);
    }

    public boolean isFirstTurn() {
        return firstTurn;
    }

    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }


    public Game getGame(){
        return game;
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }


    public int getCurrPlayerIndex() {
        return currPlayerIndex;
    }


    public void setFirstTurn(boolean booleanValue){
        firstTurn = booleanValue;
    }


    public void setFinalFrenzy(boolean booleanValue){
        finalFrenzy =  booleanValue;
    }


    public void setCurrState(State nextState){
        currState = nextState;
    }

    public void setLastPlayerIndex(int index){
        lastPlayerIndex = index;
    }

    int getRemainingActions(){
        return remainingActions;
    }

    void decreaseRemainingActions(){
        this.remainingActions--;
    }

    void resetRemainingActions(){
        if(!isFinalFrenzy() || beforeFirstPlayer(getCurrPlayerIndex()))
            this.remainingActions = ACTIONS_PER_TURN;
        else
            this.remainingActions = ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER;
    }

    /**
     * checks if the player who is trying to do something is the able to do it in this moment
     * @throws NotCurrPlayerException iff the player who called a method is not able to do it in this moment
     */
    public void validateCurrPlayer() throws NotCurrPlayerException {
        //TODO: deve prendere come arg un token
        //      deve sollevare un eccezione NotCurrPlayerException
        //      aggiungere try catch a tutti i metodi
    }

    @Override
    public synchronized void showComment(String comment) {
        game.setMessage(comment);
    }


    @Override
    public synchronized void chooseMap(int n) {
        if (n >= FIRST_MAP && n <= LAST_MAP) {
            if (currState.initializeMap(n))
                currState.nextState();
        }
    }


    @Override
    public synchronized void chooseNumberOfSkulls(int n) {
        if (n >= MIN_KILL_SHOT_TRACK_SIZE && n <= MAX_KILL_SHOT_TRACK_SIZE) {
            if (currState.setNumberOfSkulls(n))
                currState.nextState();
        }
    }


    @Override
    public synchronized void choosePcColour(PcColourEnum colour) {
        if (availablePcColours.contains(colour))
            if (currState.assignPcToPlayer(colour, players.get(currPlayerIndex))) {
                availablePcColours.remove(colour);
                nextTurn();
            }
    }


    @Override
    public synchronized void discardAndSpawn(int n) {
        Pc currPc = players.get(currPlayerIndex).getPc();
        if (n == 0 || n == 1)
            if (currState.spawnPc(currPc, n))
                nextTurn();
    }


    @Override
    public synchronized void runAround() {
        if(currState.runAround())
            currState.setTargetables(players.get(currPlayerIndex).getPc());
    }


    @Override
    public synchronized void grabStuff() {
        if(currState.grabStuff())
            currState.setTargetables(players.get(currPlayerIndex).getPc());
    }


    @Override
    public synchronized void shootPeople() {
        if(currState.shootPeople())
            currState.setTargetables(players.get(currPlayerIndex).getPc());
    }


    @Override
    public synchronized void selectSquare(int x, int y) {
        if(currState.execute(players.get(currPlayerIndex).getPc(), game.map[x][y]))
            currState.nextState();
    }


    @Override
    public synchronized void quit() {

    }

    private synchronized void nextTurn() {
        if (currPlayerIndex == players.size() - 1){
            currPlayerIndex = 0;
            currState.nextState();
        } else
            currPlayerIndex++;
        if (isFirstTurn()){
            Pc currPc = players.get(currPlayerIndex).getPc();
            currPc.drawPowerUp();
            currPc.drawPowerUp();
        }

    }

    synchronized boolean beforeFirstPlayer(int playerIndex){
        return playerIndex > lastPlayerIndex;
    }

}
