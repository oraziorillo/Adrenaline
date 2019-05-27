package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

class GrabStuffCommand extends CliCommand {
    GrabStuffCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.grabStuff();
    }
}
