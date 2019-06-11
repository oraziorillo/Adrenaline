package client.view.cli.commands;

import client.view.InputReader;
import common.rmi_interfaces.RemotePlayer;
import client.view.cli.CliInputReader;

import java.io.IOException;

public abstract class CliCommand {
    protected final RemotePlayer controller;
    protected final InputReader inputRequire;

    CliCommand(RemotePlayer controller){
        this.controller = controller;
        this.inputRequire = new CliInputReader();
    }

    public abstract void execute() throws IOException;
}
