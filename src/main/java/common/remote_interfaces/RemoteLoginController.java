package common.remote_interfaces;

import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.rmi.Remote;
import java.util.UUID;

public interface RemoteLoginController extends Remote {

   UUID register(String username) throws IOException, ClassNotFoundException;

   RemotePlayer login(UUID fromString, RemoteView view) throws IOException;
   
   void joinLobby(UUID token) throws IOException, PlayerAlreadyLoggedInException;
}
