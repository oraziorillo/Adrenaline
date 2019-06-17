package client.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

class ChooseMapCommand extends CliCommand {
    ChooseMapCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseMap( inputReader.requestInt( "Insert map code" ) );
    }
}
