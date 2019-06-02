package view.cli.conection.commands;

import common.RemoteController;
import view.InputRequier;
import view.cli.CliInputRequier;
import view.gui.GuiInputRequier;

import java.io.IOException;

public abstract class CliCommand {
    protected final RemoteController controller;
    protected final InputRequier inputRequier;

    CliCommand(RemoteController controller, boolean gui){
        this.controller = controller;
        this.inputRequier = gui? new GuiInputRequier( ) : new CliInputRequier(  );
    }

    public abstract void execute() throws IOException;
}
