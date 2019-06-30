package server.controller;

import com.google.gson.annotations.Expose;
import common.remote_interfaces.RemoteView;
import server.database.DatabaseHandler;

import javax.swing.Timer;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Pre-game, singleton waiting room. Stores players and starts a game when has enough of them.
 */
public class Lobby {

    //TODO: timer preso dal file di config
    private static final int TIME = Math.toIntExact(TimeUnit.SECONDS.toMillis(10));

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
        ackAllPlayersExcept("Say hello to @" + databaseHandler.getUsername(player.getToken()) + System.lineSeparator() + System.lineSeparator() +
                "There are " + players.size() + " players in this lobby now");
        try {
            player.getView().ack("Joined a lobby. There are " + players.size() + " players in this room");
        } catch (IOException e) {
            player.quit();
            removePlayer(player);
        }

        ackAllPlayersExcept(getAllUserNames());
        if (players.size() >= 3 && players.size() < 5) {
            timer.start();
            ackAllPlayersExcept(players.size() + " players have joined! The game will start in " + TimeUnit.MILLISECONDS.toMinutes(timer.getDelay()) + " minutes");
        } else if (players.size() == 5) {
            timer.stop();
            startNewGame();
            ackAllPlayersExcept(players.size() + " players have joined! Game is starting!");
        }
    }


    private void removePlayer(Player player) {
        players.remove(player);
        ackAllPlayersExcept("@" + databaseHandler.getUsername(player.getToken()) + " has disconnected. BOOOOO!" + System.lineSeparator() + getAllUserNames());
        if (players.size() < 3) {
            timer.stop();
            ackAllPlayersExcept(TimeUnit.MILLISECONDS.toSeconds(timer.getDelay()) + " seconds and the game would have started. Now we have to start again." + System.lineSeparator() +
                    "Blame @" + databaseHandler.getUsername(player.getToken()) + " for this!");
        }
    }


    private void startNewGame() {
        timer.stop();
        controller = new Controller(gameUUID, players);
        if (databaseHandler.containsGame(gameUUID)) {
            controller.initGame(gameUUID);
        } else {
            controller.initGame();
        }
        databaseHandler.saveUpdates(controller);
        getViews().forEach(v -> {
            try {
                v.ack("Game Started");
            } catch (IOException e) {
                //todo fare qualcosa
            }
        });
        gameStarted = true;
    }


    private void ackAllPlayersExcept(String message, Player... excluded) {
        Collection<Player> excludedColl = Arrays.asList(excluded);
        for (Player p1 : players) {
            if (!excludedColl.contains(p1)) {
                try {
                    p1.getView().ack(message);
                } catch (IOException e) {
                    p1.quit();
                    removePlayer(p1);

                }
            }
        }
    }


    private List<RemoteView> getViews(){
        return players.stream().map(Player::getView).collect(Collectors.toList());
    }


    private String getAllUserNames() {
        StringBuilder result = new StringBuilder("Players in the room:");
        String at = System.lineSeparator() + "@";
        for (Player p : players) {
            result.append(at + databaseHandler.getUsername(p.getToken()));
        }
        return result.toString();
    }

}

