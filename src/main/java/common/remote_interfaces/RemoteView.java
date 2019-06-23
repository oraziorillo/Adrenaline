package common.remote_interfaces;

import java.io.IOException;

public interface RemoteView {

    void ack(String message) throws IOException;

    ModelChangeListener getListener();


}
