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

    private static final int FIRST_MAP = 1;
    private static final int LAST_MAP = 4;
    private static final int MIN_KILL_SHOT_TRACK_SIZE = 5;
    private static final int MAX_KILL_SHOT_TRACK_SIZE = 8;

    static final int RUN = 1;
    static final int GRAB = 2;
    static final int SHOOT = 3;

    static int requestedAction;

    private Game game;
    private ArrayList<PcColourEnum> availablePcColours;
    private int currPlayerIndex;
    private State currState;
    private boolean isFirstTurn = false;
    private boolean finalFrenzy = false;
    ArrayList<Player> players;
    State setupMap, setupKillShotTrack, pcChoice, firstTurn, startTurn, run, grab, shoot;

    public Controller(ArrayList<Player> players) throws RemoteException {
        super();
        this.game = new Game();
        this.players = new ArrayList<>();
        this.availablePcColours = new ArrayList<>();
        Collections.addAll(availablePcColours, PcColourEnum.values());
        State.setController(this);
        this.players.addAll(players);
        this.currPlayerIndex = 0;
        this.setupMap = new SetupMapState();
        this.setupKillShotTrack = new SetupKillShotTrackState();
        this.pcChoice = new PcChoiceState();
        this.firstTurn = new FirstTurnState();
        this.startTurn = new StartTurnState();
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

    public void setCurrState(State nextState){
        currState = nextState;
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
    public void runAround() {
        currState.changeState(RUN);
    }

    @Override
    public void grabStuff() {
        currState.changeState(GRAB);
    }

    @Override
    public void shoot() {
        currState.changeState(SHOOT);
    }

    @Override
    public void quit() {

    }

    @Override
    public void selectSquare(int x, int y) {

    }

    private void nextTurn() {
        if (currPlayerIndex == players.size() - 1){
            currPlayerIndex = 0;
            currState.nextState();
        } else
            currPlayerIndex++;
        if (isFirstTurn){
            Pc currPc = players.get(currPlayerIndex).getPc();
            currPc.drawPowerUp();
            currPc.drawPowerUp();
        }

    }

}
