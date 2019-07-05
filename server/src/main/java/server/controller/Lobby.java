package server.controller;

import com.google.gson.annotations.Expose;
import common.dto_model.LobbyDTO;
import common.events.lobby_events.LobbyEvent;
import common.events.lobby_events.PlayersChangedEvent;
import server.ServerPropertyLoader;
import server.database.DatabaseHandler;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Pre-game, singleton waiting room. Stores players and starts a game when has enough of them.
 */
public class Lobby {

    @Expose private Controller controller;
    @Expose private UUID gameUUID;
    private boolean gameStarted;
    private List<Player> players;
    private Timer timer;
    private DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


    Lobby() {
        this.gameUUID = UUID.randomUUID();
        this.players = new LinkedList<>();
        this.timer = new Timer(ServerPropertyLoader.getInstance().getLobbyTimer(), actionEvent -> startNewGame());
        this.timer.stop();
    }


    public Lobby(UUID gameUUID) {
        this.gameUUID = gameUUID;
        this.players = new LinkedList<>();
        this.timer = new Timer(ServerPropertyLoader.getInstance().getLobbyTimer(), actionEvent -> startNewGame());
        this.timer.stop();
    }


    boolean isAvailable() {
        return !gameStarted && players.size() <= 5;
    }


    boolean isGameStarted() {
        return gameStarted;
    }


    UUID getGameUUID() {
        return gameUUID;
    }


    public List<Player> getPlayers() {
        return players;
    }


    boolean hasPlayer(UUID token) {
        return players.parallelStream()
                .filter(p -> p.getToken().equals(token))
                .count() == 1;
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
        try {
            if (gameStarted) {
                controller.getGame().addModelEventListener(
                        player.getToken(),
                        player.getPc().getColour(),
                        player.getView().getListener());
                player.setOnLine(true);
                player.resumeGame(controller.getGame());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        players.add(player);
        publishEvent(new PlayersChangedEvent(this.convertToDTO()), player);
        if (players.size() >= 3 && players.size() < 5) {
            timer.start();
            ackAll("\nThe game will start in " + TimeUnit.MILLISECONDS.toMinutes(timer.getDelay()) + " minutes...");
        } else if (players.size() == 5) {
            timer.stop();
            startNewGame();
            ackAll("\nThe game is starting");
        }
    }


    void removePlayer(UUID token) {
        players.parallelStream()
                .filter(p -> p.getToken().equals(token))
                .findFirst()
                .ifPresent(p -> players.remove(p));

    }


    private void removePlayer(Player player) {
        players.remove(player);
        ackAll("@" + databaseHandler.getUsername(player.getToken()) + " has disconnected. BOOOOO!");
        if (players.size() < 3) {
            timer.stop();
            ackAll(TimeUnit.MILLISECONDS.toSeconds(timer.getDelay()) + " seconds and the game would have started. Now we have to start again." +
                    System.lineSeparator() + "Blame @" + databaseHandler.getUsername(player.getToken()) + " for this!");
        }
    }


    private void startNewGame() {
        timer.stop();
        controller = new Controller(gameUUID, players);
        if (databaseHandler.containsGame(gameUUID)) {
            controller.initGame(gameUUID);
        } else {
            controller.initGame();
            databaseHandler.save(controller);
        }
        gameStarted = true;
    }


    private void ackAll(String msg) {
        players.parallelStream().forEach(p -> {
            try {
                p.getView().ack(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
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


    public void publishEvent(LobbyEvent event) {
        LobbyEvent censored = event.censor();
        for (Player p : players) {
            try {
                p.getView().notifyEvent(censored);
            } catch (RemoteException e) {
                p.quit();
                removePlayer(p);
            }
        }
    }


    public LobbyDTO convertToDTO() {
        List<String> userNames = new ArrayList<>();
        players.stream()
                .map(p -> DatabaseHandler.getInstance().getUsername(p.getToken()))
                .forEach(userNames::add);
        LobbyDTO lobbyDTO = new LobbyDTO();
        lobbyDTO.setPlayers(userNames);
        lobbyDTO.setGameStarted(gameStarted);
        return lobbyDTO;
    }
}

