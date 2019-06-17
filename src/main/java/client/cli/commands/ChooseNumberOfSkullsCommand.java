package client.cli.commands;

import common.remote_interfaces.RemotePlayer;
import java.io.IOException;

class ChooseNumberOfSkullsCommand extends CliCommand {
    ChooseNumberOfSkullsCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseNumberOfSkulls( inputReader.requestInt( "Select number of skulls" ) );
    }
}
