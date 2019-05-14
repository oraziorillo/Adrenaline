package view.cli.conection.commands;

import controller.RemoteController;

import java.io.IOException;

public class GrabWeaponCommand extends CliCommand {
    public GrabWeaponCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.grabWeapon( inputRequier.askInt( "Insert weapon number" ) );
    }
}
