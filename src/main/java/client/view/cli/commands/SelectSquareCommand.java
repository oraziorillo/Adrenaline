package client.view.cli.commands;

import server.controller.RemotePlayer;

import java.io.IOException;

class SelectSquareCommand extends CliCommand {
    SelectSquareCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseSquare( inputRequier.requestInt( "Insert squere column" ),
                                inputRequier.requestInt( "Insert squares row" ));
    }
}
