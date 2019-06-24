package common.remote_interfaces;

import java.beans.PropertyChangeListener;
import java.io.IOException;

public interface RemoteView {

    void ack(String message) throws IOException;

    PropertyChangeListener getListener();


}
