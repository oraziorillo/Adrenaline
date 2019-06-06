package server;

import server.model.Game;
import java.io.IOException;
import java.rmi.Remote;
import java.util.UUID;

public interface RemoteLoginController extends Remote {

   UUID register(String username) throws IOException;

   boolean containsPlayer(UUID token) throws IOException;

   RemotePlayer login(UUID fromString) throws IOException;

   void continueOldGame(UUID gameId) throws IOException, ClassNotFoundException;

   void saveGame(Game game);
}
