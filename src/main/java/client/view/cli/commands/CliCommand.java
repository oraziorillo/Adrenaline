package client.view.cli.commands;

import client.view.InputRequire;
import server.controller.RemotePlayer;
import client.view.cli.CliInputReader;
import client.view.gui.GuiInputReader;

import java.io.IOException;

public abstract class CliCommand {
    protected final RemotePlayer controller;
    protected final InputRequire inputRequire;

    CliCommand(RemotePlayer controller, boolean gui){
        this.controller = controller;
        this.inputRequire = gui? new GuiInputReader( ) : new CliInputReader(  );
    }

    public abstract void execute() throws IOException;
}
