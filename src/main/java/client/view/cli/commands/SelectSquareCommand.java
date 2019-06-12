package client.view.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

class SelectSquareCommand extends CliCommand {
    SelectSquareCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseSquare( inputReader.requestInt( "Insert squere column" ),
                                inputReader.requestInt( "Insert squares row" ));
    }
}
