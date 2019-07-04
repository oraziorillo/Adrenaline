package common.dto_model;

import java.util.List;

public class LobbyDTO implements DTO {

    private List<String> players;

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    private boolean gameStarted;

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
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
