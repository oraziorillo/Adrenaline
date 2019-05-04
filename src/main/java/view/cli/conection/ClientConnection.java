package view.cli.conection;

import java.util.UUID;

public abstract class ClientConnection {

    public abstract String update();

    public abstract boolean login(UUID token);

    public abstract UUID register(String username);
}

