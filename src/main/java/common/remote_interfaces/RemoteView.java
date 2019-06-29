package common.remote_interfaces;

import common.events.ModelEventListener;

import java.io.IOException;
import java.rmi.RemoteException;

public interface RemoteView {

    void ack(String message) throws IOException;

    ModelEventListener getListener() throws RemoteException;

}
