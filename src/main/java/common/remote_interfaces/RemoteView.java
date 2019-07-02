package common.remote_interfaces;

import common.events.ModelEventListener;
import common.events.lobby_events.LobbyEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteView extends Remote {

    void ack(String message) throws RemoteException;
    
    void chatMessage(String message) throws RemoteException;

    void error(String msg) throws RemoteException;

    void notifyEvent(LobbyEvent event) throws RemoteException;

    ModelEventListener getListener() throws RemoteException;

}
