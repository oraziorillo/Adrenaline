package client.view.cli.commands;

import common.RemotePlayer;

import java.io.IOException;

class ChooseMapCommand extends CliCommand {
    ChooseMapCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseMap( inputRequire.requestInt( "Insert map code" ) );
    }
}
