package common.rmi_interfaces;

import java.rmi.Remote;

public interface RemoteView extends Remote /*GameObserver, PlayerObserver, SquareObserver*/ {
   public void showMessage(String message);
}
