package common.dto_model;

import server.controller.Player;
import server.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class LobbyDTO implements DTO {

    private List<String> players;

    public LobbyDTO(List<Player> players){
        this.players = new ArrayList<>();
        players.stream()
                .map(p -> DatabaseHandler.getInstance().getUsername(p.getToken()))
                .forEach(u -> this.players.add(u));
    }

    public String lastPlayerAddedName() {
        return players.get(size() - 1);
    }

    public List<String> otherUserNames(){
        return players.subList(0, size() - 1);
    }

    public int size(){
        return players.size();
    }
}
