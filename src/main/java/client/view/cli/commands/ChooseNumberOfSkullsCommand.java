package client.view.cli.commands;

import server.controller.RemotePlayer;
import java.io.IOException;

class ChooseNumberOfSkullsCommand extends CliCommand {
    ChooseNumberOfSkullsCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.chooseNumberOfSkulls( inputRequier.requestInt( "Select number of skulls" ) );
    }
}
