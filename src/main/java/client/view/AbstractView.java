package client.view;

import common.enums.ConnectionMethodEnum;
import common.events.ModelEventListener;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public abstract class AbstractView extends UnicastRemoteObject implements RemoteView, ModelEventListener {

    protected String HOST = "localhost";
    protected int SOCKET_PORT = 10000;
    protected int RMI_PORT = 9999;

    protected AbstractView() throws RemoteException {
    }

    public void printMessage(String msg) {
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
}
