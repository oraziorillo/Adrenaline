package client;

import common.remote_interfaces.RemoteLoginController;

import java.util.UUID;

public interface ClientView {
    String HOST = "localhost";
    int SOCKET_PORT = 10000;
    int RMI_PORT = 9999;
    
    RemoteLoginController aquireConnection() ;
    
    boolean wantsRegister();
    
    String acquireUnregisteredUsername();
    
    UUID acquireToken();
    
    String requestString(String message);
    
    void displayErrorAndExit(String msg);
}
