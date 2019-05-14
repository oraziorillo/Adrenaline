package view.cli.conection.commands;

import controller.RemoteController;

import java.io.IOException;

public class ChooseNumberOfSkullsCommand extends CliCommand {
    ChooseNumberOfSkullsCommand(RemoteController controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.chooseNumberOfSkulls( inputRequier.askInt( "Select number of skulls" ) );
    }
}
