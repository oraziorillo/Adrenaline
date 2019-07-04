package client.view;

import client.ClientPropertyLoader;
import common.enums.ConnectionMethodEnum;
import common.events.ModelEventListener;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemoteView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public abstract class AbstractView extends UnicastRemoteObject implements RemoteView, ModelEventListener {

     protected static transient final String HOST = "localhost";
     protected static transient final int SOCKET_PORT = 10000;
     protected static transient final int RMI_PORT = 9999;
   // protected static transient final String HOST = System.getProperty("java.server.hostname");
   // protected static transient final int SOCKET_PORT = ClientPropertyLoader.getInstance().getSocketPort();
   // protected static transient final int RMI_PORT = ClientPropertyLoader.getInstance().getRmiPort();

    protected AbstractView() throws RemoteException {
        super();
    }

    public void printMessage(String msg) {
    }

    public String nextCommand() {
        return null;
    }

    public ConnectionMethodEnum acquireConnectionMethod() {
        return null;
    }

    public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
        return null;
    }

    public boolean wantsToRegister() {
        return false;
    }

    public String acquireUsername() {
        return null;
    }

    public UUID acquireToken() {
        return null;
    }

    public String requestString(String message) {
        return null;
    }

    public void error(String msg) {}
    
    @Override
    public boolean isReachable() throws RemoteException, IOException {
        return true;
    }
}
