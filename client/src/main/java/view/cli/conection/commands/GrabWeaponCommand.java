package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

class GrabWeaponCommand extends CliCommand {
    GrabWeaponCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOnSpawnPoint( inputRequier.askInt( "Insert weapon number" ) );
    }
}
