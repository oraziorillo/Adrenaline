package client.view.cli.commands;

import server.controller.RemotePlayer;
import java.io.IOException;

class ChoosePcColourCommand extends CliCommand {
    ChoosePcColourCommand(RemotePlayer controller) {
        super( controller );
    }

    @Override
    public void execute() throws IOException {
        //controller.choosePcColour( inputRequire.askPcColourEnum() );
    }
}
