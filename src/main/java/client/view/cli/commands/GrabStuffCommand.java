package client.view.cli.commands;

import server.controller.RemotePlayer;

import java.io.IOException;

class GrabStuffCommand extends CliCommand {
    GrabStuffCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.grabStuff();
    }
}
