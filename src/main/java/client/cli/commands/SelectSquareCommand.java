package client.cli.commands;

import server.RemotePlayer;

import java.io.IOException;

class SelectSquareCommand extends CliCommand {
    SelectSquareCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseSquare( inputRequier.askInt( "Insert squere column" ),
                                inputRequier.askInt( "Insert squares row" ));
    }
}
