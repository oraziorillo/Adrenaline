package common.remote_interfaces;

import common.dto_model.GameDTO;
import common.events.ModelEventListener;
import common.events.lobby_events.LobbyEvent;
import common.events.requests.Request;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

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
     * tells the client number of players actually in the lobby
     * @param event contains infos about the lobby
     * @throws RemoteException rmi
     */
    void notifyEvent(LobbyEvent event) throws RemoteException;
    
    /**
     * make an asynchronous request to the client
     * @param request contains the request data
     * @throws RemoteException rmi
     */
    void request(Request request) throws RemoteException;
    
    /**
     * @return the implementing class, or someone for it
     * @throws RemoteException rmi
     */
    ModelEventListener getListener() throws RemoteException;
    
    /**
     * sends all the infos about a failed game
     * @param game contains the infos above
     * @throws RemoteException rmi
     */
    void resumeGame(GameDTO game) throws RemoteException;
    
}
