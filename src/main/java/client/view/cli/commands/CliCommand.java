package client.view.cli.commands;

import server.controller.RemotePlayer;
import client.view.InputRequier;
import client.view.cli.CliInputReader;
import client.view.gui.GuiInputReader;

import java.io.IOException;

public abstract class CliCommand {
    protected final RemotePlayer controller;
    protected final InputRequier inputRequier;

    CliCommand(RemotePlayer controller, boolean gui){
        this.controller = controller;
        this.inputRequier = gui? new GuiInputReader( ) : new CliInputReader(  );
    }

    public abstract void execute() throws IOException;
}
