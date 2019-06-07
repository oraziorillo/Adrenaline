package client.view.cli.commands;

import server.controller.RemotePlayer;
import java.io.IOException;

public class ChooseWeaponOnSpawnPointCommand extends CliCommand {
    ChooseWeaponOnSpawnPointCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOnSpawnPoint( inputRequire.requestInt( "Insert weapon number" ) );
    }
}
