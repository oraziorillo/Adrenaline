package common.remote_interfaces;

import common.dto_model.GameDTO;
import common.events.ModelEventListener;
import common.events.lobby_events.LobbyEvent;
import common.events.requests.Request;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteView extends Remote {

    void ack(String message) throws RemoteException;
    
    void chatMessage(String message) throws RemoteException;

    void notifyEvent(LobbyEvent event) throws RemoteException;

    void request(Request request) throws RemoteException;

    ModelEventListener getListener() throws RemoteException;

    void resumeGame(GameDTO game) throws RemoteException;

    boolean isReachable() throws RemoteException;

    void winners(List<String> gameWinners) throws RemoteException;

    void close() throws RemoteException;
}
