package client.cli.commands;

import server.RemotePlayer;
import java.io.IOException;

class ChoosePcColourCommand extends CliCommand {
    ChoosePcColourCommand(RemotePlayer controller, boolean gui) {
        super( controller, gui );
    }

    @Override
    public void execute() throws IOException {
        //controller.choosePcColour( inputRequier.askPcColourEnum() );
    }
}
