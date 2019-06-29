package server.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.enums.PcColourEnum;
import common.remote_interfaces.RemoteView;
import server.controller.Controller;
import server.controller.Lobby;
import server.controller.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static server.database.FileEnum.*;

public class DatabaseHandler {

    private static DatabaseHandler instance;

    private HashMap<String, UUID> tokensByUserName;
    private HashMap<UUID, PlayerInfo> playerInfoByToken;
    private HashMap<UUID, String> gamePathByUUID;

    private Gson gson = new GsonBuilder()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    // ideale sarebbe aggiungere un hashmap lobbyByGameUUID che contiene una lista vuota di players e viene inizializzata subito
    // avere un hashmap come ora gameInfoByToken che contiene informazioni sul gioco
    // avere un hashmap come ora players con UUID del game


    private DatabaseHandler() {

        initFromFile(TOKENS_BY_USER_NAME, gson);
        initFromFile(PLAYER_INFO_BY_TOKEN, gson);
        initFromFile(GAME_PATH_BY_UUID, gson);
    }


    private void initFromFile(FileEnum file, Gson gson) {

        try (JsonReader reader = new JsonReader(new FileReader(file.getFilePath()))) {
            Type type;
            switch (file) {
                case TOKENS_BY_USER_NAME:
                    type = new TypeToken<HashMap<String, String>>() {}.getType();
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
                case GAME_PATH_BY_UUID:
                    type = new TypeToken<HashMap<UUID, String>>() {}.getType();
                    gamePathByUUID = gson.fromJson(reader, type);
                    if (gamePathByUUID == null)
                        resetFile(GAME_PATH_BY_UUID);
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


    public boolean isPendantGame(UUID gameUUID) { return gamePathByUUID.containsKey(gameUUID); }


    public boolean hasPendentGame(UUID token) {
        return playerInfoByToken.get(token).hasPendentGame();
    }


    public String getUsername(UUID token) {
        return playerInfoByToken.get(token).getUsername();
    }


    public Player getPlayer(UUID token)  {
        if (playerInfoByToken.containsKey(token)) {
            if (playerInfoByToken.get(token).getPlayer() == null){
                Player player = null;
                try {
                    player = new Player(token);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                playerInfoByToken.get(token).setPlayer(player);
            }
            return playerInfoByToken.get(token).getPlayer();
        }
        return null;
    }



    public List<Lobby> getLobbies() {
        List<Lobby> lobbies = new ArrayList<>();
        for (UUID gameUUID: gamePathByUUID.keySet()) {
            lobbies.add(new Lobby(gameUUID));
        }
        return lobbies;
    }


    public UUID getGameUUID (UUID playerToken){
        return playerInfoByToken.get(playerToken).getIncompleteGameID();
    }


    public boolean containsGame(UUID gameUUID) {
        return gamePathByUUID.containsKey(gameUUID);
    }


    public void setPlayerColour(UUID playerToken, PcColourEnum pcColour){
        playerInfoByToken.get(playerToken).setPcColour(pcColour);
    }

    public PcColourEnum getPlayerColour(UUID playerToken){
        return playerInfoByToken.get(playerToken).getPcColour();
    }


//    public Lobby getMyOldLobby(UUID playerToken) {
//        UUID incompleteGameID = playerInfoByToken.get(playerToken).getIncompleteGameID();
//        GameInfo incompleteGameInfo = gameInfoByUUID.get(incompleteGameID);
//        if (incompleteGameInfo.isActive()) {
//            //if the lobby is still active return back the lobby
//            return incompleteGameInfo.getLobby();
//        } else {
//            //if the server had crushed and the lobby was gone create a new one
//            Lobby newLobby = new Lobby(incompleteGameID);
//            incompleteGameInfo.setLobby(newLobby);
//            return newLobby;
//        }
//    }


    public void registerPlayer(UUID token, String username, Player player) {
        tokensByUserName.put(username, token);
        playerInfoByToken.put(token, new PlayerInfo(username, player));
        overwrite(TOKENS_BY_USER_NAME);
        overwrite(PLAYER_INFO_BY_TOKEN);
    }


    public synchronized void saveUpdates(Controller controller) {
        UUID gameUUID = controller.getGameUUID();
        if (!gamePathByUUID.containsKey(gameUUID)){
            //if the starting game is a new one, then add to the db the corresponding data
            //TODO scrivere il file con le informazioni del game
            String filePath = generateFilePath(gameUUID);
            gamePathByUUID.put(gameUUID, filePath);
            controller.getPlayers().forEach(p -> { playerInfoByToken.get(p.getToken()).setIncompleteGameID(gameUUID); });
        }
        GameInfo gameInfo = new GameInfo(controller.getPlayers());
        gameInfo.gameStarted();
        gameInfo.setCurrPlayerIndex(0);
        gameInfo.setLastPlayerIndex(-1);
        gameInfo.setRemainingActions(2);
        gameInfo.setGame(controller.getGame());
        overWrite(gameInfo, gameUUID);
        overwrite(GAME_PATH_BY_UUID);
        overwrite(PLAYER_INFO_BY_TOKEN);
    }


    public synchronized void save(Controller controller) {
        UUID gameUUID = controller.getGameUUID();
    }



    public synchronized void gameEnded(Controller controller) {
        List<UUID> playersInGame = controller.getPlayers().stream().map(Player::getToken).collect(Collectors.toList());
        playersInGame.forEach(t -> playerInfoByToken.get(t).gameEnded());
        File gameFile = new File(generateFilePath(controller.getGameUUID()));
        boolean success = gameFile.delete();
        assert (success);
        gamePathByUUID.remove(controller.getGameUUID());
        overwrite(PLAYER_INFO_BY_TOKEN);
        overwrite(GAME_PATH_BY_UUID);
    }


    private synchronized void overwrite(FileEnum file) {

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
                case GAME_PATH_BY_UUID:
                    gson.toJson(gamePathByUUID, writer);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private synchronized void overWrite(GameInfo gameInfo, UUID gameUUID){
        try (FileWriter writer = new FileWriter(gamePathByUUID.get(gameUUID))) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            gson.toJson(gameInfo, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //to use in case you want to clear data
    private synchronized void resetAllData(){
        tokensByUserName = new HashMap<>();
        overwrite(TOKENS_BY_USER_NAME);
        playerInfoByToken = new HashMap<>();
        overwrite(PLAYER_INFO_BY_TOKEN);
        gamePathByUUID = new HashMap<>();
        overwrite(GAME_PATH_BY_UUID);
    }

    private synchronized void resetFile(FileEnum file) {
        switch (file) {
            case TOKENS_BY_USER_NAME:
                tokensByUserName = new HashMap<>();
                break;
            case PLAYER_INFO_BY_TOKEN:
                playerInfoByToken = new HashMap<>();
                break;
            case GAME_PATH_BY_UUID:
                gamePathByUUID = new HashMap<>();
                break;
            default:
                break;
        }
        overwrite(file);
    }


    public synchronized String generateFilePath(UUID uuid){
        return "src/main/java/server/database/files/" + uuid + ".json";
    }

}
