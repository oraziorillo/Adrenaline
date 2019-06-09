package client.view.cli.commands;

import server.controller.RemotePlayer;

import java.io.IOException;

class SelectSquareCommand extends CliCommand {
    SelectSquareCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseSquare( inputRequire.requestInt( "Insert squere column" ),
                                inputRequire.requestInt( "Insert squares row" ));
    }
}
