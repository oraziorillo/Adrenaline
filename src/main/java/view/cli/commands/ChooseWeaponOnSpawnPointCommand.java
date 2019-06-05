package view.cli.commands;

import common.RemotePlayer;
import java.io.IOException;

public class ChooseWeaponOnSpawnPointCommand extends CliCommand {
    ChooseWeaponOnSpawnPointCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOnSpawnPoint( inputRequier.askInt( "Insert weapon number" ) );
    }
}
