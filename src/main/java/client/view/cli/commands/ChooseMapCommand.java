package client.view.cli.commands;

import server.controller.RemotePlayer;

import java.io.IOException;

class ChooseMapCommand extends CliCommand {
    ChooseMapCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.chooseMap( inputRequire.requestInt( "Insert map code" ) );
    }
}
