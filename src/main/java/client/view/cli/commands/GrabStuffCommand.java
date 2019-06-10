package client.view.cli.commands;

import common.rmi_interfaces.RemotePlayer;

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
