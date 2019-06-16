package server.database;

import com.google.gson.annotations.Expose;
import server.controller.Lobby;

import java.util.List;
import java.util.UUID;

class GameInfo {

    @Expose private Lobby lobby;
    @Expose private List<UUID> playersTokens;
    private boolean active;

    GameInfo(){}

    GameInfo(Lobby lobby){
        this.lobby = lobby;
    }

    boolean isActive() {
        return active;
    }

    Lobby getLobby() {
        return lobby;
    }

    void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    List<UUID> getPlayersTokens() {
        return playersTokens;
    }

    void addPlayer(UUID playerID) {
        playersTokens.add(playerID);
    }

    void gameStarted(){
        this.active = true;
    }
}
