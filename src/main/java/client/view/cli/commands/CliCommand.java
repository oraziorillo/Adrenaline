package client.view.cli.commands;

import client.view.InputRequire;
import server.controller.RemotePlayer;
import client.view.cli.CliInputReader;

import java.io.IOException;

public abstract class CliCommand {
    protected final RemotePlayer controller;
    protected final InputRequire inputRequire;

    CliCommand(RemotePlayer controller){
        this.controller = controller;
        this.inputRequire = new CliInputReader(  );
    }

    public abstract void execute() throws IOException;
}
