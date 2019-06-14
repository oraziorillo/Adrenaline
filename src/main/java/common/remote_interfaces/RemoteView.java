package common.remote_interfaces;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteView extends Remote /*GameObserver, PlayerObserver, SquareObserver*/ {

   void ack(String message) throws IOException;
}
