package client.cli.commands;

import common.remote_interfaces.RemotePlayer;
import java.io.IOException;

class ChoosePcColourCommand extends CliCommand {
    ChoosePcColourCommand(RemotePlayer controller) {
        super( controller );
    }

    @Override
    public void execute() throws IOException {
        //socket.choosePcColour( inputReader.askPcColourEnum() );
    }
}
