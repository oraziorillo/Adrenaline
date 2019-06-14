package server.controller;

import server.exceptions.PlayerAlreadyLoggedInException;
import server.model.Database;

import javax.swing.Timer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Pre-game, singleton waiting room. Stores players and starts a game when has enough of them.
 */
public class Lobby {

    //TODO: timer preso dal file di config
    private static final int TIME = 1000 * 60 * 3;

    private Controller controller;

    private boolean old;
    private UUID gameUUID;
    private List<Player> players;
    private Timer timer;
    private Database database = Database.getInstance();


    Lobby() {
        this.gameUUID = UUID.randomUUID();
        this.players = new LinkedList<>();
        this.timer = new Timer(TIME, actionEvent -> startNewGame());
        this.timer.stop();
    }


    public Lobby(UUID gameUUID) {
        this.gameUUID = gameUUID;
        this.players = new LinkedList<>();
        this.timer = new Timer(TIME, actionEvent -> startNewGame());
        this.timer.stop();
    }


    public UUID getGameUUID() {
        return gameUUID;
    }


    public List<Player> getPlayers() {
        return players;
    }


    /**
     * Adds p tho the waiting room. Then
     * If the room contains the minimum number of players to addPlayer starts a timer. When timer ends, a game is started.
     * If the room contains the maximum number of players, a game is started.
     * When a game is started, the waiting room is cleared and the timer is resetted
     *
     * @param player the player to add
     */
    void addPlayer(Player player) throws PlayerAlreadyLoggedInException {
        if (players.contains(player))
            throw new PlayerAlreadyLoggedInException();
        ackAllPlayersExcept( "Say hello to @"+ database.getUsername(player.getToken()) + System.lineSeparator()+System.lineSeparator() +
                "There are "+players.size()+" players in this lobby now" );
        players.add(player);
        try {
            database.getView( player.getToken() ).ack( "Joined a lobby. There are "+players.size()+" players in this room" );
        } catch ( IOException e ) {
            player.quit();
            removePlayer( player );
        }
        ackAllPlayersExcept( getAllUsernames() );
        if (players.size() >= 3) {
            timer.start();
            ackAllPlayersExcept( players.size()+" players has joined! The game will start in "+ TimeUnit.MILLISECONDS.toMinutes( timer.getDelay() )+" minutes" );
        }
        if (players.size() == 5) {
            timer.stop();
            startNewGame();
            ackAllPlayersExcept( players.size()+" players has joined! Game is starting!" );
        }
    }


    void removePlayer(Player player) {
        players.remove(player);
        ackAllPlayersExcept( "@"+database.getUsername( player.getToken() )+" has disconnected. BOOOOO!"+System.lineSeparator()+getAllUsernames());
        if(players.size()<3) {
            timer.stop();
            ackAllPlayersExcept( TimeUnit.MILLISECONDS.toSeconds( timer.getDelay() )+" seconds and the game would have started. Now we have to start again."+System.lineSeparator()+
                    "Blame @"+database.getUsername( player.getToken() )+" for this!");
        }
    }


    private void startNewGame() {
        try {
            if (!old)
                this.controller = new Controller(players);
            //TODO: else this.controller = fromJson
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void ackAllPlayersExcept(String message, Player... excluded){
        Collection<Player> excludedColl = Arrays.asList(excluded);
        for(Player p1:players){
            if(!excludedColl.contains( p1 )){
                try {
                    database.getView( p1.getToken() ).ack( message );
                } catch ( IOException e ) {
                    p1.quit();
                    removePlayer( p1 );
                    
                }
            }
        }
    }
    
    private String getAllUsernames(){
        StringBuilder usernames = new StringBuilder("Players in the room:").append( System.lineSeparator() );
        for(Player p: players){
            usernames.append( "@"+database.getUsername( p.getToken() )+System.lineSeparator() );
        }
        return usernames.toString();
    }
    
}

