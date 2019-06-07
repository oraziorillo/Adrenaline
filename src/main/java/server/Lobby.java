package server;

import server.controller.Controller;
import server.controller.Player;
import server.enums.PcColourEnum;
import server.exceptions.PlayerAlreadyLoggedInException;
import server.model.Game;
import server.model.Pc;

import javax.swing.Timer;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Pre-game, singleton waiting room. Stores players and starts a game when has enough of them.
 */
class Lobby {

    //TODO: timer preso dal file di config
    private static final int TIME = 1000 * 60 * 3;

    private Controller controller;

    private boolean old;
    private UUID gameUUID;
    private List<Player> players;
    private Timer timer;



    Lobby() {
        this.gameUUID = UUID.randomUUID();
        this.players = new LinkedList<>();
        this.timer = new Timer(TIME, actionEvent -> startNewGame());
        this.timer.stop();
    }


    Lobby(UUID gameUUID) {
        this.old = true;
        this.gameUUID = gameUUID;
        this.players = new LinkedList<>();
        this.timer = new Timer(TIME, actionEvent -> startNewGame());
        this.timer.stop();
    }


    boolean isOld() {
        return old;
    }


    UUID getGameUUID() {
        return gameUUID;
    }


    List<Player> getPlayers() {
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
    void addPlayer(Player player) throws PlayerAlreadyLoggedInException {
        if (players.contains(player))
            throw new PlayerAlreadyLoggedInException();
        players.add(player);
        if (players.size() >= 3) {
            timer.restart();
        }
        if (players.size() == 5) {
            timer.stop();
            startNewGame();
        }
    }


    void removePlayer(Player player) {
        //TODO pingare il player per vedere se è ancora attivo ed eventualmente rimuoverlo (da fare non in questo metodo ma nel loginController)
        players.remove(player);
        timer.stop();
    }


    private void startNewGame() {
        try {
            if (!old)
                this.controller = new Controller(players);
            //TODO: else this.controller = fromJson
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
