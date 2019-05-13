package view.cli.conection;

import controller.RemoteController;

import java.util.UUID;

public abstract class ClientConnection implements RemoteController {

    public abstract String update();

    public abstract boolean login(UUID token);

    public abstract UUID signIn(String username);
}

