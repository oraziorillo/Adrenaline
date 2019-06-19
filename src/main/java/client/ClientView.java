package client;

import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemoteView;

import java.rmi.RemoteException;
import java.util.UUID;

public interface ClientView extends RemoteView {
    String HOST = "localhost";
    int SOCKET_PORT = 10000;
    int RMI_PORT = 9999;
    
    RemoteLoginController acquireConnection() throws RemoteException;
    
    boolean wantsRegister() throws RemoteException;
    
    String acquireUsername() throws RemoteException;
    
    UUID acquireToken() throws RemoteException;
    
    String requestString(String message) throws RemoteException;
    
    void displayErrorAndExit(String msg) throws RemoteException;
}
