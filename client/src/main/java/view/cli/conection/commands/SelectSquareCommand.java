package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

class SelectSquareCommand extends CliCommand {
    SelectSquareCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseSquare( inputRequier.askInt( "Insert squere column" ),
                                inputRequier.askInt( "Insert squares row" ));
    }
}
