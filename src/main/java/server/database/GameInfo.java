package server.database;

import com.google.gson.annotations.Expose;
import server.controller.Lobby;
import server.controller.Player;
import server.model.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class GameInfo {

    @Expose private List<UUID> playersTokens;
    private boolean active;
    @Expose private Game game;
    @Expose private int currPlayerIndex;
    @Expose private int lastPlayerIndex;
    @Expose private int remainingActions;

    GameInfo(){}

    GameInfo(List<Player> players){
        this.playersTokens = players.stream().map(Player::getToken).collect(Collectors.toList());
    }

    boolean isActive() {
        return active;
    }

    List<UUID> getPlayersTokens() {
        return playersTokens;
    }


    void gameStarted(){
        this.active = true;
    }

    public void setPlayersTokens(List<UUID> playersTokens) {
        this.playersTokens = playersTokens;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getCurrPlayerIndex() {
        return currPlayerIndex;
    }

    public void setCurrPlayerIndex(int currPlayerIndex) {
        this.currPlayerIndex = currPlayerIndex;
    }

    public int getLastPlayerIndex() {
        return lastPlayerIndex;
    }

    public void setLastPlayerIndex(int lastPlayerIndex) {
        this.lastPlayerIndex = lastPlayerIndex;
    }

    public int getRemainingActions() {
        return remainingActions;
    }

    public void setRemainingActions(int remainingActions) {
        this.remainingActions = remainingActions;
    }
}
