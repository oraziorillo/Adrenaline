package common.events.lobby_events;

import common.dto_model.LobbyDTO;

import static common.Constants.PLAYER_JOINED;

public class PlayerJoinedEvent extends LobbyEvent{

    private int eventID = PLAYER_JOINED;

    public PlayerJoinedEvent(LobbyDTO lobby) {
        super(lobby);
    }

    PlayerJoinedEvent(LobbyDTO lobby, boolean censored) {
        super(lobby, censored);
    }

    @Override
    public String toString() {
        String m1 = System.lineSeparator() + "Say hello to @" + lobby.lastPlayerAddedName();
        String m2 = System.lineSeparator() + "You joined a lobby";
        StringBuilder m3 = new StringBuilder(((lobby.size() > 1)
                ? System.lineSeparator() + "There are " + lobby.size() + " players"
                : System.lineSeparator() + "There is " + 1 + " player") + " in this lobby");
        for (String u : lobby.otherUserNames())
            m3.append(System.lineSeparator()).append("@").append(u);
        m3.append(System.lineSeparator()).append("@").append(lobby.lastPlayerAddedName());
        return censored
                ? m1 + m3
                : m2 + m3;
    }

    @Override
    public PlayerJoinedEvent censor() {
        return new PlayerJoinedEvent(lobby, true);
    }


}
