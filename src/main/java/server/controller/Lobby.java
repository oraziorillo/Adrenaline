package server.controller;

import com.google.gson.annotations.Expose;
import common.dto_model.LobbyDTO;
import common.events.lobby_events.LobbyEvent;
import common.events.lobby_events.PlayerJoinedEvent;
import common.remote_interfaces.RemoteView;
import server.ServerPropertyLoader;
import server.database.DatabaseHandler;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Pre-game, singleton waiting room. Stores players and starts a game when has enough of them.
 */
public class Lobby {

    private static final int TIME = Math.toIntExact(TimeUnit.SECONDS.toMillis( ServerPropertyLoader.getInstance().getLobbyTimer() ));

    @Expose private Controller controller;
    @Expose private UUID gameUUID;
    private boolean gameStarted;
    private List<Player> players;
    private Timer timer;
    private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


    Lobby() {
        this.gameUUID = UUID.randomUUID();
        this.players = new LinkedList<>();
        this.timer = new Timer(TIME, actionEvent -> startNewGame());
        this.timer.stop();
    }


    public Lobby(UUID gameUUID) {
        this.gameUUID = gameUUID;
        this.players = new LinkedList<>();
        this.timer = new Timer(TIME, actionEvent -> startNewGame());
        this.timer.stop();
    }


    public boolean isAvailable() {
        return !gameStarted && players.size() <= 5;
    }


    public UUID getGameUUID() {
        return gameUUID;
    }


    public List<Player> getPlayers() {
        return players;
    }


    /**
     * Adds p tho the waiting room. Then
     * If the room contains the minimum number of players to addPlayer starts a timer. When timer ends, a game is started.
     * If the room contains the maximum number of players, a game is started.
     * When a game is started, the waiting room is cleared and the timer is resetted
     *
     * @param player the player to add
     */
    void addPlayer(Player player) {
        players.add(player);
        publishEvent(new PlayerJoinedEvent(new LobbyDTO(players, false, false )), player);
        if (players.size() >= 3 && players.size() < 5) {
            timer.start();
            ack("The game will start in " + TimeUnit.MILLISECONDS.toMinutes(timer.getDelay()) + " minutes", null);
        } else if (players.size() == 5) {
            timer.stop();
            startNewGame();
            ack("Let's start", null);
        }
    }


    private void removePlayer(Player player) {
        players.remove(player);
        ack("@" + databaseHandler.getUsername(player.getToken()) + " has disconnected. BOOOOO!", null);
        if (players.size() < 3) {
            timer.stop();
            ack(TimeUnit.MILLISECONDS.toSeconds(timer.getDelay()) + " seconds and the game would have started. Now we have to start again." +
                    System.lineSeparator() + "Blame @" + databaseHandler.getUsername(player.getToken()) + " for this!", null);
        }
    }


    private void startNewGame() {
        timer.stop();
        controller = new Controller(gameUUID, players);
        if (databaseHandler.containsGame(gameUUID)) {
            controller.initGame(gameUUID);
            ack("Game restored!", null);
        } else {
            controller.initGame();
            databaseHandler.save(controller);
        }
        getViews().forEach(v -> {
            try {
                v.ack("Game Started");
            } catch (IOException e) {
                //todo fare qualcosa
            }
        });
        gameStarted = true;
    }


    private void ack(String m1, String m2, Player... recipientsForM2) {
        List<UUID> rec2 = Arrays
                .stream(recipientsForM2)
                .map(Player::getToken)
                .collect(Collectors.toList());
        for (Player p : players) {
            try {
                if (rec2.contains(p.getToken()) && m2 != null) {
                    p.getView().ack(m2);
                } else if (!rec2.contains(p.getToken()) && m1 != null)
                    p.getView().ack(m1);
            } catch (IOException e) {
                p.quit();
                removePlayer(p);
            }
        }
    }


    private void publishEvent(LobbyEvent event, Player recipientOfUncensored) {
        try {
            recipientOfUncensored.getView().notifyEvent(event);
        } catch (RemoteException e) {
            recipientOfUncensored.quit();
            removePlayer(recipientOfUncensored);
        }
        LobbyEvent censored = event.censor();
        for (Player p : players)
            try {
                if (!p.getToken().equals(recipientOfUncensored.getToken()))
                    p.getView().notifyEvent(censored);
            } catch (RemoteException e) {
                p.quit();
                removePlayer(p);
            }
    }


    private List<RemoteView> getViews(){
        return players.stream().map(Player::getView).collect(Collectors.toList());
    }

}

