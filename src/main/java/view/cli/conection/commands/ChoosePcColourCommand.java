package view.cli.conection.commands;

import common.RemoteController;
import java.io.IOException;

class ChoosePcColourCommand extends CliCommand {
    ChoosePcColourCommand(RemoteController controller, boolean gui) {
        super( controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.choosePcColour( inputRequier.askPcColourEnum() );
    }
}
