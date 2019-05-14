package view.cli.conection.commands;

import controller.RemoteController;

import java.io.IOException;

public class GrabStuffCommand extends CliCommand {
    GrabStuffCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.grabStuff();
    }
}
