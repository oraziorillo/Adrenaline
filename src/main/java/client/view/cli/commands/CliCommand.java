package client.view.cli.commands;

import client.view.InputReader;
import common.remote_interfaces.RemotePlayer;
import client.view.cli.CliInputReader;

import java.io.IOException;

public abstract class CliCommand {

    protected final RemotePlayer controller;
    final InputReader inputReader;

    CliCommand(RemotePlayer controller){
        this.controller = controller;
        this.inputReader = new CliInputReader( );
    }

    public abstract void execute() throws IOException;
}
