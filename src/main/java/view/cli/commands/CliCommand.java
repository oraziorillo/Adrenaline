package view.cli.commands;

import common.RemotePlayer;
import view.InputRequier;
import view.cli.CliInputRequier;
import view.gui.GuiInputRequier;

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
