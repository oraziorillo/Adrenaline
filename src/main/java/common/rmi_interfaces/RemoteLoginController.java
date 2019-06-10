package common.rmi_interfaces;

import java.io.IOException;
import java.rmi.Remote;
import java.util.UUID;

public interface RemoteLoginController extends Remote {

   UUID register(String username) throws IOException;

   RemotePlayer login(UUID fromString) throws IOException;
}
