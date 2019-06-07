package client;

import client.view.RemoteView;
import server.RemoteLoginController;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient extends UnicastRemoteObject implements Runnable, RemoteView {

    protected RmiClient() throws RemoteException {}

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.getRegistry();
            RemoteLoginController loginController = (RemoteLoginController)registry.lookup("LoginController");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
