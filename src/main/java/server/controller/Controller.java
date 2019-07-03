package server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.enums.PcColourEnum;
import common.events.ModelEventListener;
import common.events.requests.Request;
import server.ServerPropertyLoader;
import server.controller.states.InactiveState;
import server.controller.states.SetupMapState;
import server.database.DatabaseHandler;
import server.database.GameInfo;
import server.model.*;
import server.model.actions.Action;
import server.model.deserializers.*;
import server.model.squares.Square;

import javax.swing.Timer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static common.Constants.*;

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
    private boolean locked;
    private Timer requestTimer;
    private Player requestRecipient;


    public Controller(UUID gameUUID, List<Player> players) {
        this.gameUUID = gameUUID;
        this.players = players;
        this.squaresToRefill = new HashSet<>();
        this.deadPlayers = new LinkedList<>();
        this.availablePcColours = Arrays.stream(PcColourEnum.values()).collect(Collectors.toSet());
        this.lastPlayerIndex = -1;
        this.remainingActions = 2;
        this.requestTimer = new Timer( ServerPropertyLoader.getInstance().getRequestTimer(), actionEvent -> {
            try {
                requestRecipient.response(requestRecipient.getActiveRequest().getChoices().get(1));
                requestRecipient.getView().ack("Time to decide is up!");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        this.requestTimer.stop();
    }


    void initGame(UUID gameUUID) {
        try {
            DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
            Type weaponsDeckType = new TypeToken<Deck<WeaponCard>>(){}.getType();
            Type ammoDeckType = new TypeToken<Deck<AmmoTile>>(){}.getType();
            Type powerUpDeckType = new TypeToken<Deck<PowerUpCard>>(){}.getType();
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(Action.class, new ActionDeserializer())
                    .registerTypeAdapter(Square.class, new SquareDeserializer())
                    .registerTypeAdapter(weaponsDeckType, new WeaponsDeckDeserializer())
                    .registerTypeAdapter(ammoDeckType, new AmmoDeckDeserializer())
                    .registerTypeAdapter(powerUpDeckType, new PowerUpsDeckDeserializer())
                    .create();

            JsonReader reader = new JsonReader(new FileReader(databaseHandler.getGamePath(gameUUID)));
            GameInfo gameInfo = gson.fromJson(reader, GameInfo.class);

            game = gameInfo.getGame();
            game.restore();
            currPlayerIndex = gameInfo.getCurrPlayerIndex();
            lastPlayerIndex = gameInfo.getLastPlayerIndex();
            addListenersToModel();
            ackAll("Game restored!");
            players.forEach(player -> {
                player.setPc(game.getPc(databaseHandler.getPlayerColour(player.getToken())));
                player.setCurrState(new InactiveState(this, 2));
            });
            nextTurn();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    void initGame(){
        game = Game.getGame();
        addListenersToModel();
        for (Player p: players) {
            if (players.get(0) == p)
                p.setCurrState(new SetupMapState(this));
            else
                p.setCurrState(new InactiveState(this, InactiveState.PC_SELECTION_STATE));
        }
        ackAll("Game started!");
        ackCurrent("It's your turn!");
        ackCurrent("You're the first to join this lobby, so I'll reward you by making you choose the game board we'll play on");
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


    public boolean isLocked() {
        return locked;
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
            ackCurrent("\nIt's your turn");
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
    }


    public boolean isNextOnDuty(Player player){
        return currPlayerIndex < players.size() - 1 && players.indexOf(player) == currPlayerIndex + 1 ||
                currPlayerIndex == players.size() - 1 && players.indexOf(player) == 0;
    }


    public boolean amITheLast() {
        return currPlayerIndex == players.size() - 1;
    }


    public void sendRequest(Request request, Player recipient) {
        try {
            locked = true;
            requestRecipient = recipient;
            recipient.getView().request(request);
            requestTimer.start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void sendNonBlockingRequest(Request request) {
        try {
            getCurrPlayer().getView().request(request);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void ackRequestRecipient(String msg) {
        try {
            requestRecipient.getView().ack(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void ackPlayer(Player p, String msg) {
        try {
            p.getView().ack(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void ackCurrent(String msg){
        try {
            getCurrPlayer().getView().ack(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void ackAll(String msg){
        players.parallelStream().forEach(p -> {
            try {
                p.getView().ack(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }


    public String availableColours() {
        StringBuilder availableColours = new StringBuilder();
        for (PcColourEnum c : availablePcColours) {
            availableColours.append("\n> ").append(c.toString()).append(c.getTabs()).append("(").append(c.getName()).append(")");
        }
        return availableColours.toString();
    }


    public void stopRequestTimer() {
        this.requestTimer.stop();
    }


    public void unlock() {

    }


    public void checkGameStatus() {
        if (players.stream().filter(Player::isOnLine).count() < 3){
            //TODO
        }
    }


    public void sendChatMessage(String msg) {
        players.parallelStream().forEach(p -> {
            try {
                p.getView().chatMessage(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
