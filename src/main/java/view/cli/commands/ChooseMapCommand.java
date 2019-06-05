package view.cli.commands;

import common.RemotePlayer;

import java.io.IOException;

class ChooseMapCommand extends CliCommand {
    ChooseMapCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.chooseMap( inputRequier.askInt( "Insert map code" ) );
    }
}
