package view.cli.conection.commands;

import controller.RemoteController;

import java.io.IOException;

public class ChoosePcColourCommand extends CliCommand {
    ChoosePcColourCommand(RemoteController controller, boolean gui) {
        super( controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.choosePcColour( inputRequier.askPcColourEnum() );
    }
}
