package server.database;

import com.google.gson.annotations.Expose;
import common.remote_interfaces.RemoteView;
import server.controller.Player;

import java.util.UUID;

class PlayerInfo {

    @Expose private String username;
    @Expose private Player player;
    @Expose private UUID incompleteGameID;
    private RemoteView view;


    PlayerInfo(){}

    PlayerInfo(String username, Player player){
        this.username = username;
        this.player = player;
    }

    String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

    Player getPlayer() {
        return player;
    }

    void setPlayer(Player player) {
        this.player = player;
    }

    UUID getIncompleteGameID() {
        return incompleteGameID;
    }

    void setIncompleteGameID(UUID incompleteGameID) {
        this.incompleteGameID = incompleteGameID;
    }

    RemoteView getView() {
        return view;
    }

    void setView(RemoteView view) {
        this.view = view;
    }

    boolean hasPendentGame(){
        return incompleteGameID != null;
    }

    void gameEnded(UUID gameUUID){
        if (gameUUID.equals(incompleteGameID))
            incompleteGameID = null;
    }
}
