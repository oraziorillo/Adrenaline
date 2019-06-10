package client.view.cli.commands;

import common.rmi_interfaces.RemotePlayer;

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
