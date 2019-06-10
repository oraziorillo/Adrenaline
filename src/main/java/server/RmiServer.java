package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer implements Runnable {

    @Override
    public void run() {

        try {

            Registry registry = LocateRegistry.createRegistry(9999);
            registry.rebind("LoginController", LoginController.getInstance());

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
