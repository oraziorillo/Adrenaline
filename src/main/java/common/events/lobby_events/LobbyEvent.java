package common.events.lobby_events;

import common.dto_model.LobbyDTO;

import java.io.Serializable;

public abstract class LobbyEvent implements Serializable {

    LobbyDTO lobby;
    boolean censored;

    LobbyEvent(LobbyDTO lobby){
        this.lobby = lobby;
        this.censored = false;
    }

    LobbyEvent(LobbyDTO lobby, boolean censored) {
        this.lobby = lobby;
        this.censored = censored;
    }

    public LobbyDTO getDTO() {
        return lobby;
    }

    public abstract PlayerJoinedEvent censor();
}
