package client.view;

import common.enums.ConnectionMethodEnum;
import common.events.ModelEventListener;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemoteView;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.UUID;

public interface AbstractView extends RemoteView, ModelEventListener {
    String HOST = "localhost";
    int SOCKET_PORT = 10000;
    int RMI_PORT = 9999;

    void error(String msg) throws RemoteException;

    ConnectionMethodEnum acquireConnectionMethod() throws RemoteException;

    RemoteLoginController acquireConnection(ConnectionMethodEnum cme) throws RemoteException;

    boolean wantsToRegister() throws RemoteException;

    String acquireUsername() throws RemoteException;

    UUID acquireToken() throws RemoteException;

    String requestString(String message) throws RemoteException;

    Collection<String> getPendingAcks() throws RemoteException;
}
