package client.controller;

import client.view.cli.CliView;

import java.io.IOException;
import java.util.UUID;

public abstract class ClientController {
    // reference to networking layer
    private Thread receiver;

    // the view
    private final CliView view;

    public ClientController(CliView cliView) {
        this.view = cliView;        
    }

    public CliView getView() {
        return view;
    }

    public abstract void init();

    public abstract UUID registerWith(String username);

    public abstract void login(UUID token);

    public abstract void sendMessage(String message);
}
