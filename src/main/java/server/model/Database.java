package server.model;

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


    private Database() throws IOException {

        try (FileInputStream fis = new FileInputStream("src/main/resources/database/incompleteGamesByUUID")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            incompleteGamesByPlayerToken = (HashMap<UUID, UUID>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            incompleteGamesByPlayerToken = new HashMap<>();
            overwrite(INCOMPLETE_GAMES);
        }

        try (FileInputStream fis = new FileInputStream("src/main/resources/database/playersByToken")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            playersByToken = (HashMap<UUID, Player>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            playersByToken = new HashMap<>();
            overwrite(PLAYERS);
        }

        try (FileInputStream fis = new FileInputStream("src/main/resources/database/userNamesByToken")) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            userNamesByToken = (HashMap<UUID, String>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            userNamesByToken = new HashMap<>();
            overwrite(USER_NAMES);
        }
    }


    public static Database getInstance() throws IOException {
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
        overwrite(PLAYERS);
    }


    public void registerView(UUID token, RemoteView view) {
        viewsByToken.put(token, view);
    }


    void registerIncompleteGame(Lobby lobby) throws IOException {
        if (incompleteGamesByPlayerToken.containsValue(lobby.getGameUUID())) {
            UUID gameUUID = lobby.getGameUUID();
            lobby.getPlayers().forEach(p -> incompleteGamesByPlayerToken.put(p.getToken(), gameUUID));
        }
        overwrite(INCOMPLETE_GAMES);
    }


    void removeIncompleteGame(UUID gameUUID) throws IOException {
        incompleteGamesByPlayerToken.forEach((k, v) -> {
            if (v.equals(gameUUID))
                incompleteGamesByPlayerToken.remove(k);
        });
        lobbiesByGameID.remove(gameUUID);
        overwrite(INCOMPLETE_GAMES);
    }



    private void overwrite(int file) throws IOException {

        switch (file) {

            case(INCOMPLETE_GAMES):
                try (FileOutputStream fos = new FileOutputStream("src/main/resources/database/incompleteGamesByUUID")) {
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(incompleteGamesByPlayerToken);
                    oos.close();
                }
                break;

            case (PLAYERS):
                try (FileOutputStream fos = new FileOutputStream("src/main/resources/database/playersByToken")) {
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(playersByToken);
                    oos.close();
                }
                break;

            case USER_NAMES:
                try (FileOutputStream fos = new FileOutputStream("src/main/resources/database/userNamesByToken")) {
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(userNamesByToken);
                    oos.close();
                }
                break;

            default:
                break;
        }
    }
}
