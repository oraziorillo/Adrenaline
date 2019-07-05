package server.database;

import com.google.gson.annotations.Expose;
import common.enums.PcColourEnum;
import server.controller.Player;

import java.util.UUID;

class PlayerInfo {

    private Player player;
    @Expose private String username;
    @Expose private PcColourEnum pcColour;
    @Expose private UUID incompleteGameID;


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

    public PcColourEnum getPcColour() {
        return pcColour;
    }

    public void setPcColour(PcColourEnum pcColour) {
        this.pcColour = pcColour;
    }

    UUID getIncompleteGameID() {
        return incompleteGameID;
    }

    void setIncompleteGameID(UUID incompleteGameID) {
        this.incompleteGameID = incompleteGameID;
    }

    boolean hasPendentGame(){
        return incompleteGameID != null;
    }

    void gameEnded(){
        pcColour = null;
        incompleteGameID = null;
    }
}
