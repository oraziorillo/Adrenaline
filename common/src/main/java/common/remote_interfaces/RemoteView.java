package common.remote_interfaces;

import common.dto_model.GameDTO;
import common.events.ModelEventListener;
import common.events.lobby_events.LobbyEvent;
import common.events.requests.Request;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Methods the server can call on the client
 */
public interface RemoteView extends Remote {
    
    /**
     * send a message as the server
     * @param message the message
     * @throws RemoteException rmi
     */
    void ack(String message) throws RemoteException;
    
    /**
     * send a message as an user
     * @param message the message
     * @throws RemoteException rmi
     */
    void chatMessage(String message) throws RemoteException;
    
    /**
     * tells the client numbe
     * @param event
     * @throws RemoteException
     */
    void notifyEvent(LobbyEvent event) throws RemoteException;

    void request(Request request) throws RemoteException;

    ModelEventListener getListener() throws RemoteException;

    void resumeGame(GameDTO game) throws RemoteException;

    void winners(List<String> winners) throws RemoteException;

    void close() throws RemoteException;

    boolean isReachable() throws RemoteException;

}
