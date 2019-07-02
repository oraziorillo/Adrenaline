package server.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import common.enums.PcColourEnum;
import server.controller.Controller;
import server.controller.Lobby;
import server.controller.Player;
import server.model.Deck;
import server.model.actions.Action;
import server.model.serializers.ActionSerializer;
import server.model.serializers.DeckSerializer;
import server.model.serializers.TargetCheckerSerializer;
import server.model.target_checkers.TargetChecker;

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

    private Type deckType = new TypeToken<Deck>(){}.getType();

    private Gson gson = new GsonBuilder()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Action.class, new ActionSerializer())
            .registerTypeAdapter(TargetChecker.class, new TargetCheckerSerializer())
            .registerTypeAdapter(deckType, new DeckSerializer<>())
            .create();


    private DatabaseHandler() {
        initFromFile(TOKENS_BY_USER_NAME, gson);
        initFromFile(PLAYER_INFO_BY_TOKEN, gson);
        initFromFile(GAME_PATH_BY_UUID, gson);
    }


    public synchronized static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }


    private synchronized void initFromFile(FileEnum file, Gson gson) {
        try (JsonReader reader = new JsonReader(new FileReader(file.getFilePath()))) {
            Type type;
            switch (file) {
                case TOKENS_BY_USER_NAME:
                    type = new TypeToken<HashMap<String, UUID>>() {
                    }.getType();
                    tokensByUserName = gson.fromJson(reader, type);
                    if (tokensByUserName == null)
                        resetFile(TOKENS_BY_USER_NAME);
                    break;
                case PLAYER_INFO_BY_TOKEN:
                    type = new TypeToken<HashMap<UUID, PlayerInfo>>() {
                    }.getType();
                    playerInfoByToken = gson.fromJson(reader, type);
                    if (playerInfoByToken == null)
                        resetFile(PLAYER_INFO_BY_TOKEN);
                    break;
                case GAME_PATH_BY_UUID:
                    type = new TypeToken<HashMap<UUID, String>>() {
                    }.getType();
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


    public synchronized boolean isRegistered(UUID token) {
        return playerInfoByToken.containsKey(token);
    }


    public synchronized boolean isRegistered(String username) {
        return tokensByUserName.containsKey(username);
    }


    public synchronized boolean isLoggedIn(UUID token) {
        return playerInfoByToken.get(token).getPlayer().getView() != null;
    }


    public synchronized boolean isPendantGame(UUID gameUUID) {
        return gamePathByUUID.containsKey(gameUUID);
    }


    public synchronized boolean hasPendentGame(UUID token) {
        return playerInfoByToken.get(token).hasPendentGame();
    }


    public synchronized String getUsername(UUID token) {
        return playerInfoByToken.get(token).getUsername();
    }


    public synchronized Player getPlayer(UUID token) {
        try {
            if (playerInfoByToken.containsKey(token)) {
                PlayerInfo pi = playerInfoByToken.get(token);
                if (pi.getPlayer() == null) {
                    pi.setPlayer(new Player(token));
                }
                return pi.getPlayer();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


    public synchronized List<Lobby> getLobbies() {
        List<Lobby> lobbies = new ArrayList<>();
        for (UUID gameUUID : gamePathByUUID.keySet()) {
            lobbies.add(new Lobby(gameUUID));
        }
        return lobbies;
    }


    public synchronized UUID getGameUUID(UUID playerToken) {
        return playerInfoByToken.get(playerToken).getIncompleteGameID();
    }


    public synchronized String getGamePath(UUID gameUUID) {
        return gamePathByUUID.get(gameUUID);
    }


    public synchronized boolean containsGame(UUID gameUUID) {
        return gamePathByUUID.containsKey(gameUUID);
    }


    public synchronized void setPlayerColour(UUID playerToken, PcColourEnum pcColour) {
        playerInfoByToken.get(playerToken).setPcColour(pcColour);
    }

    public synchronized PcColourEnum getPlayerColour(UUID playerToken) {
        return playerInfoByToken.get(playerToken).getPcColour();
    }


    public synchronized void registerPlayer(UUID token, String username, Player player) {
        tokensByUserName.put(username, token);
        playerInfoByToken.put(token, new PlayerInfo(username, player));
        overwrite(TOKENS_BY_USER_NAME);
        overwrite(PLAYER_INFO_BY_TOKEN);
    }


    public synchronized void save(Controller controller) {
        UUID gameUUID = controller.getGameUUID();
        List<Player> players = controller.getPlayers();
        if (!gamePathByUUID.containsKey(gameUUID)) {
            String filePath = generateFilePath(gameUUID);
            gamePathByUUID.put(gameUUID, filePath);
            players.forEach(p -> playerInfoByToken.get(p.getToken()).setIncompleteGameID(gameUUID));
        }
        GameInfo gameInfo = new GameInfo(
                players
                .stream()
                .map(Player::getToken)
                .collect(Collectors.toList()));
        gameInfo.gameStarted();
        gameInfo.setCurrPlayerIndex(controller.getCurrPlayerIndex());
        gameInfo.setLastPlayerIndex(controller.getLastPlayerIndex());
        gameInfo.setGame(controller.getGame());
        overWrite(gameInfo, gameUUID);
        overwrite(GAME_PATH_BY_UUID);
        overwrite(PLAYER_INFO_BY_TOKEN);
    }


    public synchronized void gameEnded(Controller controller) {
        //Todo da rivedere e usare
        List<UUID> playersInGame = controller.getPlayers().stream().map(Player::getToken).collect(Collectors.toList());
        playersInGame.forEach(t -> playerInfoByToken.get(t).gameEnded());
        File gameFile = new File(generateFilePath(controller.getGameUUID()));
        gameFile.delete();
        gamePathByUUID.remove(controller.getGameUUID());
        overwrite(PLAYER_INFO_BY_TOKEN);
        overwrite(GAME_PATH_BY_UUID);
    }


    private synchronized void overwrite(FileEnum file) {

        try (FileWriter writer = new FileWriter(file.getFilePath())) {
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


    private synchronized void overWrite(GameInfo gameInfo, UUID gameUUID) {
        try (FileWriter writer = new FileWriter(gamePathByUUID.get(gameUUID))) {
            gson.toJson(gameInfo, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    private synchronized String generateFilePath(UUID uuid) {
        return "src/main/java/server/database/files/game_infos/" + uuid + ".json";
    }


    //TODO mettere view a null quando il player si disconnette
}
