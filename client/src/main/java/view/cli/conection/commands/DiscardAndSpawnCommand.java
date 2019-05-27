package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

public class DiscardAndSpawnCommand extends CliCommand {
    DiscardAndSpawnCommand(RemoteController controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.discardAndSpawn( inputRequier.askInt( "Insert card number" ) );
    }
}
