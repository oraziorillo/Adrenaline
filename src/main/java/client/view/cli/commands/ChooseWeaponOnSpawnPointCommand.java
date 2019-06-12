package client.view.cli.commands;

import common.remote_interfaces.RemotePlayer;
import java.io.IOException;

public class ChooseWeaponOnSpawnPointCommand extends CliCommand {
    ChooseWeaponOnSpawnPointCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOnSpawnPoint( inputReader.requestInt( "Insert weapon number" ) );
    }
}
