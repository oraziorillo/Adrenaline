package client.cli.commands;

import server.RemotePlayer;
import client.InputRequier;
import client.cli.CliInputRequier;
import client.gui.GuiInputRequier;

import java.io.IOException;

public abstract class CliCommand {
    protected final RemotePlayer controller;
    protected final InputRequier inputRequier;

    CliCommand(RemotePlayer controller, boolean gui){
        this.controller = controller;
        this.inputRequier = gui? new GuiInputRequier( ) : new CliInputRequier(  );
    }

    public abstract void execute() throws IOException;
}
