package client.cli.commands;

import server.RemotePlayer;

import java.io.IOException;

class GrabStuffCommand extends CliCommand {
    GrabStuffCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.grabStuff();
    }
}
