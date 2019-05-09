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

    static final int SETUP_STEPS = 1;
    static final int FIRST_TURN = 2;

    private Game game;
    private ArrayList<Player> players;
    private ArrayList<PcColourEnum> availablePcColours;
    private int currPlayerIndex;
    private State currState, setupSteps, firstTurn, startTurn, run, grab, shoot;

    public Controller(ArrayList<Player> players) throws RemoteException {
        super();
        this.game = new Game();
        this.players = new ArrayList<>();
        this.availablePcColours = new ArrayList<>();
        Collections.addAll(availablePcColours, PcColourEnum.values());
        this.players.addAll(players);
        this.currPlayerIndex = 0;
        this.setupSteps = new SetupStepsState(game);
        this.firstTurn = new FirstTurnState(game);
        this.firstTurn = new StartTurnState(game);
    }

    public void validateCurrPlayer() throws NotCurrPlayerException {
        //TODO: deve prendere come arg un token
        //      deve sollevare un eccezione NotCurrPlayerException
        //      aggiungere try catch a tutti i metodi
    }

    @Override
    public synchronized void chooseMap(int n) {
        if (n >= FIRST_MAP && n <= LAST_MAP) {
            if (currState.initializeMap(n))
                currState = nextState();
        }
    }

    @Override
    public synchronized void chooseNumberOfSkulls(int n) {
        if (n >= MIN_KILL_SHOT_TRACK_SIZE && n <= MAX_KILL_SHOT_TRACK_SIZE) {
            if (currState.setNumberOfSkulls(n))
                currState = nextState();
        }
    }

    @Override
    public synchronized void choosePcColour(PcColourEnum colour) {
        if (availablePcColours.contains(colour))
            if (currState.assignPcToPlayer(colour, players.get(currPlayerIndex))) {
                availablePcColours.remove(colour);
                nextTurn();
                currState.drawTwoPowerUps(players.get(currPlayerIndex).getPc());
            }
    }

    @Override
    public synchronized void discardAndSpawn(int n) {
        if (n == 0 || n == 1)
            if (currState.spawnPc(n)) {
                nextTurn();
                currState.drawTwoPowerUps(players.get(currPlayerIndex).getPc());
            }
    }

    @Override
    public synchronized void showComment(String comment) {

    }

    @Override
    public void runAround() {
    }

    @Override
    public void grabStuff() {

    }

    @Override
    public void shoot() {

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
            currState = nextState();
        } else
            currPlayerIndex++;

    }

    private State nextState() {
        return null;
    }

}
