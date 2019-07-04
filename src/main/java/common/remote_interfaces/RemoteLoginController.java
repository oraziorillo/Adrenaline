package common.remote_interfaces;

import common.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface RemoteLoginController extends Remote {

   UUID register(String username, RemoteView view) throws RemoteException;

   RemotePlayer login(UUID fromString, RemoteView view) throws IOException, PlayerAlreadyLoggedInException;
   
   void joinLobby(UUID token) throws RemoteException;
}
