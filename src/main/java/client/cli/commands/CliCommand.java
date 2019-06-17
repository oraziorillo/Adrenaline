package client.cli.commands;

import client.InputReader;
import client.cli.CliInputReader;
import common.remote_interfaces.RemotePlayer;

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
