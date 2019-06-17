package client.cli.commands;

import common.remote_interfaces.RemotePlayer;
import java.io.IOException;

public class ChooseWeaponOfMineCommand extends CliCommand {
    ChooseWeaponOfMineCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOfMine( inputReader.requestInt( "Insert weapon index" ) );
    }
}
