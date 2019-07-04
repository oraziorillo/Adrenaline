package client.view;

import client.ClientPropertyLoader;
import common.enums.ConnectionMethodEnum;
import common.enums.ControllerMethodsEnum;
import common.events.ModelEventListener;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemoteView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public abstract class AbstractView extends UnicastRemoteObject implements RemoteView, ModelEventListener {

    protected static transient final String HOST = ClientPropertyLoader.getInstance().getHostAddress();
    protected static transient final int SOCKET_PORT = ClientPropertyLoader.getInstance().getSocketPort();
    protected static transient final int RMI_PORT = ClientPropertyLoader.getInstance().getRmiPort();

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

    public ControllerMethodsEnum authMethod() {
        return null;
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

    public void error(String error) {}

    @Override
    public boolean isReachable() throws RemoteException {
        return true;
    }
}
