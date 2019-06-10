package client.view.cli.commands;

import common.rmi_interfaces.RemotePlayer;

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
