package controller;

import controller.player.Player;
import model.Game;
import javax.swing.Timer;
import java.util.ArrayList;

/**
 * Pre-game, singleton waiting room. Stores players and starts a game when has enough of them.
 */
public class WaitingRoom {
    private static WaitingRoom instance = new WaitingRoom();
    private ArrayList<Player> waitingPlayers;
    private Timer timer;
    private static final int TIME = 1000 * 60 * 3;

    private WaitingRoom() {
        waitingPlayers = new ArrayList<>();
        timer = new Timer(TIME, actionEvent -> startGame());
        timer.stop();
    }
    
    static WaitingRoom getInstance(){
        return instance;
    }
    
    /**
     * Adds p tho the waiting room. Then
     * If the room contains the minimum number of players to play starts a timer. When timer ends, a game is started.
     * If the room contains the maximum number of players, a game is started.
     * When a game is started, the waiting room is cleared and the timer is resetted
     * @param p the player to add
     */
    public void addPlayer(Player p) {
        waitingPlayers.add(p);
        if (waitingPlayers.size() > 2) {
            timer.restart();
        }
        if (waitingPlayers.size() == 5) {
            timer.stop();
            startGame();
        }
    }

    private void startGame() {
        Game g = new Game();
        //add Pcs
        waitingPlayers.clear();
        timer.stop();
    }
    
    public int size() {
        return waitingPlayers.size();
    }
}

