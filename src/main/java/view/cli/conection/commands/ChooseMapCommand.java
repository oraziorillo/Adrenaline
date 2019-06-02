package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

class ChooseMapCommand extends CliCommand {
    ChooseMapCommand(RemoteController controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.chooseMap( inputRequier.askInt( "Insert map code" ) );
    }
}
