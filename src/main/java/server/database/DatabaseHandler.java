package server.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.remote_interfaces.RemoteView;
import server.controller.Lobby;
import server.controller.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static server.database.FileEnum.*;

public class DatabaseHandler {

    private static DatabaseHandler instance;

    private HashMap<String, UUID> tokensByUserName;
    private HashMap<UUID, PlayerInfo> playerInfoByToken;
    private HashMap<UUID, GameInfo> gameInfoByUUID;


    private DatabaseHandler() {

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        initFromFile(TOKENS_BY_USER_NAME, gson);
        initFromFile(PLAYER_INFO_BY_TOKEN, gson);
        initFromFile(GAME_INFO_BY_UUID, gson);
    }


    private void initFromFile(FileEnum file, Gson gson) {

        try (JsonReader reader = new JsonReader(new FileReader(file.getFilePath()))) {
            Type type;
            switch (file) {
                case TOKENS_BY_USER_NAME:
                    type = new TypeToken<HashMap<String, UUID>>() {}.getType();
                    tokensByUserName = gson.fromJson(reader, type);
                    if (tokensByUserName == null)
                        resetFile(TOKENS_BY_USER_NAME);
                    break;
                case PLAYER_INFO_BY_TOKEN:
                    type = new TypeToken<HashMap<UUID, PlayerInfo>>() {}.getType();
                    playerInfoByToken = gson.fromJson(reader, type);
                    if (playerInfoByToken == null)
                        resetFile(PLAYER_INFO_BY_TOKEN);
                    break;
                case GAME_INFO_BY_UUID:
                    type = new TypeToken<HashMap<UUID, GameInfo>>() {}.getType();
                    gameInfoByUUID = gson.fromJson(reader, type);
                    if (gameInfoByUUID == null)
                        resetFile(GAME_INFO_BY_UUID);
                    break;
                default:
                    break;
            }
        } catch (FileNotFoundException e) {
            resetFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }


    public boolean isRegistered(UUID token) {
        return playerInfoByToken.containsKey(token);
    }


    public boolean isRegistered(String username) {
        return tokensByUserName.containsKey(username);
    }


    public boolean hasPendentGame(UUID token) {
        return playerInfoByToken.get(token).hasPendentGame();
    }


    public String getUsername(UUID token) {
        return playerInfoByToken.get(token).getUsername();
    }


    public Player getPlayer(UUID token) {
        return playerInfoByToken.get(token).getPlayer();
    }


    public RemoteView getView(UUID token) {
        return playerInfoByToken.get(token).getView();
    }


    public Lobby getMyOldLobby(UUID playerToken) {
        UUID incompleteGameID = playerInfoByToken.get(playerToken).getIncompleteGameID();
        GameInfo incompleteGameInfo = gameInfoByUUID.get(incompleteGameID);
        if (incompleteGameInfo.isActive()) {
            //if the lobby is still active return back the lobby
            return incompleteGameInfo.getLobby();
        } else {
            //if the server had crushed and the lobby was gone create a new one
            Lobby newLobby = new Lobby(incompleteGameID);
            incompleteGameInfo.setLobby(newLobby);
            return newLobby;
        }
    }


    public void registerPlayer(UUID token, String username, Player player) {
        tokensByUserName.put(username, token);
        playerInfoByToken.put(token, new PlayerInfo(username, player));
        overwrite(TOKENS_BY_USER_NAME);
        overwrite(PLAYER_INFO_BY_TOKEN);
    }


    public void registerView(UUID token, RemoteView view) {
        playerInfoByToken.get(token).setView(view);
    }


    void gameStarted(Lobby lobby) {
        UUID gameUUID = lobby.getGameUUID();
        if (!gameInfoByUUID.containsKey(gameUUID)){
            //if the starting game is a new one, then add to the db the corresponding data
            gameInfoByUUID.put(gameUUID, new GameInfo(lobby));
            lobby.getPlayers().forEach(p -> {
                playerInfoByToken.get(p.getToken()).setIncompleteGameID(gameUUID);
                gameInfoByUUID.get(gameUUID).addPlayer(p.getToken());
            });
        }
        //whether the game is a new one or a pending one, set it as active
        gameInfoByUUID.get(gameUUID).gameStarted();
        overwrite(GAME_INFO_BY_UUID);
        overwrite(PLAYER_INFO_BY_TOKEN);
    }


    void gameEnded(UUID gameUUID) {
        List<UUID> playersInGame = gameInfoByUUID.get(gameUUID).getPlayersTokens();
        playersInGame.forEach(t -> playerInfoByToken.get(t).gameEnded(gameUUID));
        gameInfoByUUID.remove(gameUUID);
        overwrite(PLAYER_INFO_BY_TOKEN);
        overwrite(GAME_INFO_BY_UUID);
    }


    private void overwrite(FileEnum file) {

        try (FileWriter writer = new FileWriter(file.getFilePath())) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            switch (file) {
                case TOKENS_BY_USER_NAME:
                    gson.toJson(tokensByUserName, writer);
                    break;
                case PLAYER_INFO_BY_TOKEN:
                    gson.toJson(playerInfoByToken, writer);
                    break;
                case GAME_INFO_BY_UUID:
                    gson.toJson(gameInfoByUUID, writer);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //to use in case you want to clear data
    private void resetAllData(){
        tokensByUserName = new HashMap<>();
        overwrite(TOKENS_BY_USER_NAME);
        playerInfoByToken = new HashMap<>();
        overwrite(PLAYER_INFO_BY_TOKEN);
        gameInfoByUUID = new HashMap<>();
        overwrite(GAME_INFO_BY_UUID);
    }

    private void resetFile(FileEnum file) {
        switch (file) {
            case TOKENS_BY_USER_NAME:
                tokensByUserName = new HashMap<>();
                break;
            case PLAYER_INFO_BY_TOKEN:
                playerInfoByToken = new HashMap<>();
                break;
            case GAME_INFO_BY_UUID:
                gameInfoByUUID = new HashMap<>();
                break;
            default:
                break;
        }
        overwrite(file);
    }

}
