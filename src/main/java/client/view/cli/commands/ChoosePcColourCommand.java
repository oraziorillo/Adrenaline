package client.view.cli.commands;

import common.RemotePlayer;
import java.io.IOException;

class ChoosePcColourCommand extends CliCommand {
    ChoosePcColourCommand(RemotePlayer controller) {
        super( controller );
    }

    @Override
    public void execute() throws IOException {
        //socket_proxies.choosePcColour( inputRequire.askPcColourEnum() );
    }
}
