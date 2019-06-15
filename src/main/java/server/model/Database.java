package server.model;

import common.enums.FileEnum;
import common.remote_interfaces.RemoteView;
import server.controller.Lobby;
import server.controller.Player;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class Database {

    private static final int INCOMPLETE_GAMES = 0;
    private static final int PLAYERS = 1;
    private static final int USER_NAMES = 2;

    private static Database instance;

    private HashMap<UUID, UUID> incompleteGamesByPlayerToken;

    private HashMap<UUID, Player> playersByToken;

    private HashMap<UUID, String> userNamesByToken;

    private HashMap<UUID, RemoteView> viewsByToken = new HashMap<>();

    private HashMap<UUID, Lobby> lobbiesByGameID = new HashMap<>();


    private Database() {
        incompleteGamesByPlayerToken = (HashMap<UUID, UUID>) initDataFrom("database/incompleteGamesByUUID");
        playersByToken = (HashMap<UUID, Player>) initDataFrom("database/playersByToken");
        userNamesByToken = (HashMap<UUID, String>) initDataFrom("database/userNamesByToken");
    }


    private HashMap initDataFrom(String fileName) {

        try (FileInputStream fis = new FileInputStream(fileName)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap data = (HashMap<UUID, UUID>) ois.readObject();
            ois.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            HashMap data = new HashMap();
            overwrite(FileEnum.fromPath(fileName));
            return data;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    public boolean isRegistered(UUID token) {
        return userNamesByToken.containsKey(token);
    }


    public boolean isRegistered(String username) {
        return userNamesByToken.containsValue(username);
    }


    public boolean hasAGameToFinish(UUID token) {
        return incompleteGamesByPlayerToken.containsKey(token);
    }


    public String getUsername(UUID token) {
        return userNamesByToken.get(token);
    }


    public Player getPlayer(UUID token) {
        return playersByToken.get(token);
    }


    public RemoteView getView(UUID token) {
        return viewsByToken.get(token);
    }


    public Lobby getLobby(UUID token) {
        UUID incompleteGameID = incompleteGamesByPlayerToken.get(token);
        if (lobbiesByGameID.containsKey(incompleteGameID))
            //if the lobby is still active return it
            return lobbiesByGameID.get(incompleteGameID);
        else {
            //if the server had crushed and the lobby was gone create a new one
            Lobby newLobby = new Lobby(incompleteGameID);
            lobbiesByGameID.put(incompleteGameID, newLobby);
            return newLobby;
        }
    }


    public void registerPlayer(UUID token, String username, Player player) throws IOException {
        userNamesByToken.put(token, username);
        playersByToken.put(token, player);
        overwrite(FileEnum.PLAYERS_BY_TOKEN);
    }


    public void registerView(UUID token, RemoteView view) {
        viewsByToken.put(token, view);
    }


    void registerIncompleteGame(Lobby lobby) {
        if (incompleteGamesByPlayerToken.containsValue(lobby.getGameUUID())) {
            UUID gameUUID = lobby.getGameUUID();
            lobby.getPlayers().forEach(p -> incompleteGamesByPlayerToken.put(p.getToken(), gameUUID));
        }
        overwrite(FileEnum.INCOMPLETE_GAMES_BY_UUID);
    }


    void removeIncompleteGame(UUID gameUUID) {
        incompleteGamesByPlayerToken.forEach((k, v) -> {
            if (v.equals(gameUUID))
                incompleteGamesByPlayerToken.remove(k);
        });
        lobbiesByGameID.remove(gameUUID);
        overwrite(FileEnum.INCOMPLETE_GAMES_BY_UUID);
    }


    private void overwrite(FileEnum file) {
        try (FileOutputStream fos = new FileOutputStream(file.getFilePath())) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            switch (file.ordinal()) {
                case (INCOMPLETE_GAMES):
                    if (incompleteGamesByPlayerToken != null)
                        oos.writeObject(incompleteGamesByPlayerToken);
                    break;
                case (PLAYERS):
                    if (playersByToken != null)
                        oos.writeObject(playersByToken);
                    break;
                case USER_NAMES:
                    if (userNamesByToken != null)
                        oos.writeObject(userNamesByToken);
                    break;
                default:
                    break;
            }
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
