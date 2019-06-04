
package common;

import server.controller.Player;
import server.model.Game;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.UUID;

public interface RemoteLoginController extends Remote {
   HashMap<UUID, Player> players = new HashMap<>();
   HashMap<UUID, String> gamePaths = new HashMap<>();
   UUID register(String username) throws IOException;
   boolean containsPlayer(UUID token) throws IOException;
   RemotePlayer login(UUID fromString) throws IOException;
   void enterSavedGame(UUID gameId) throws IOException, ClassNotFoundException;
   void saveGame(Game game);
}
