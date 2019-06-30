package common.remote_interfaces;

import common.events.ModelEventListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteView extends Remote {

    void ack(String message) throws RemoteException;
    
    void chatMessage(String message) throws RemoteException;

    void error(String msg) throws RemoteException;

    ModelEventListener getListener() throws RemoteException;

}
