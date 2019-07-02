package server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import common.enums.PcColourEnum;
import common.events.ModelEventListener;
import server.controller.states.InactiveState;
import server.controller.states.SetupMapState;
import server.database.DatabaseHandler;
import server.database.GameInfo;
import server.model.Game;
import server.model.GameBoard;
import server.model.Pc;
import server.model.WeaponCard;
import server.model.actions.Action;
import server.model.deserializers.ActionDeserializer;
import server.model.deserializers.GameBoardDeserializer;
import server.model.squares.Square;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static common.Constants.ACTIONS_PER_FRENZY_TURN_AFTER_FIRST_PLAYER;
import static common.Constants.ACTIONS_PER_TURN;

public class Controller{

    private UUID gameUUID;
    private Game game;
    private int currPlayerIndex;
    private int lastPlayerIndex;
    private int remainingActions;
    private List<Player> players;
    private Set<PcColourEnum> availablePcColours;
    private Set<Square> squaresToRefill;
    private LinkedList<Player> deadPlayers;


    public Controller(UUID gameUUID, List<Player> players) {
        this.gameUUID = gameUUID;
        this.players = players;
        this.squaresToRefill = new HashSet<>();
        this.deadPlayers = new LinkedList<>();
        this.availablePcColours = Arrays.stream(PcColourEnum.values()).collect(Collectors.toSet());
        this.lastPlayerIndex = -1;
        this.remainingActions = 2;
    }


    public void initGame(UUID gameUUID) {
        try {
            DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(Action.class, new ActionDeserializer())
                    .registerTypeAdapter(GameBoard.class, new GameBoardDeserializer())
                    .create();

            JsonReader reader = new JsonReader(new FileReader(databaseHandler.getGamePath(gameUUID)));
            GameInfo gameInfo = gson.fromJson(reader, GameInfo.class);

            game = gameInfo.getGame();
            currPlayerIndex = gameInfo.getCurrPlayerIndex();
            lastPlayerIndex = gameInfo.getLastPlayerIndex();
            addListenersToModel();
            players.forEach(player -> player.setPc(game.getPc(databaseHandler.getPlayerColour(player.getToken()))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void initGame(){
        game = Game.getGame();
        addListenersToModel();
        for (Player p: players) {
            if (players.get(0) == p)
                p.setCurrState(new SetupMapState(this));
            else
                p.setCurrState(new InactiveState(this, InactiveState.PC_SELECTION_STATE));
        }
        try {
            getCurrPlayer().getView().ack("It's your turn!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void addListenersToModel(){
        players.forEach(p -> {
            ModelEventListener listener = null;
            try {
                listener = p.getView().getListener();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            game.addModelEventListener(p.getToken(), listener);
        });
    }


    public UUID getGameUUID() {
        return gameUUID;
    }

    public boolean isFinalFrenzy() {
        return game.isFinalFrenzy();
    }


    public Game getGame() {
        return game;
    }


    public List<Player> getPlayers() {
        return players;
    }


    public Player getCurrPlayer(){
        return players.get(currPlayerIndex);
    }


    public Pc getCurrPc() {
        return players.get(currPlayerIndex).getPc();
    }


    public void addDeadPlayer(Player deadPlayer){
        deadPlayers.add(deadPlayer);
    }


    public LinkedList<Player> getDeadPlayers() {
        return deadPlayers;
    }


    public int getCurrPlayerIndex() {
        return currPlayerIndex;
    }

    public int getLastPlayerIndex() {
        return lastPlayerIndex;
    }

    public int getRemainingActions() {
        return remainingActions;
    }


    public WeaponCard getCurrWeapon() {
        return getCurrPlayer().getCurrWeapon();
    }


    public Set<Square> getSquaresToRefill(){
        return squaresToRefill;
    }




    public void setLastPlayerIndex(int index) {
        lastPlayerIndex = index;
    }


    public boolean beforeFirstPlayer(int playerIndex) {
        return playerIndex > lastPlayerIndex;
    }


    public void setCurrWeapon(WeaponCard weapon) {
        getCurrPlayer().setCurrWeapon(weapon);
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


    public boolean checkAvailableColour(String pcColour) {
        return availablePcColours.contains(PcColourEnum.fromString(pcColour));
    }


    public void removeAvailableColour(String pcColour) {
        availablePcColours.remove(PcColourEnum.fromString(pcColour));
    }


    public void addSquareToRefill(Square s) {
        squaresToRefill.add(s);
    }


    public void resetSquaresToRefill() {
        squaresToRefill.clear();
    }


    public void nextTurn() {
        if (deadPlayers.isEmpty()) {
            increaseCurrPlayerIndex();
            getCurrPlayer().setActive();
            if (currPlayerIndex == lastPlayerIndex)
                game.computeWinner();
                //TODO gestire il valore di ritorno del metodo precedente e implementare la fine della partita chiudendo connessioni..
        } else {
            deadPlayers.get(0).hasToRespawn();
        }
    }

    public void increaseCurrPlayerIndex(){
        if (currPlayerIndex == players.size() - 1)
            currPlayerIndex = 0;
        else
            currPlayerIndex++;

        try {
            getCurrPlayer().getView().ack("You are the current player now!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isNextOnDuty(Player player){
        return currPlayerIndex < players.size() - 1 && players.indexOf(player) == currPlayerIndex + 1 ||
                currPlayerIndex == players.size() - 1 && players.indexOf(player) == 0;
    }

    public synchronized void saveUpdates() {
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
