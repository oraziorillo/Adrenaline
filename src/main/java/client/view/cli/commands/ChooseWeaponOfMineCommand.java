package client.view.cli.commands;

import server.controller.RemotePlayer;
import java.io.IOException;

public class ChooseWeaponOfMineCommand extends CliCommand {
    ChooseWeaponOfMineCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOfMine( inputRequire.requestInt( "Insert weapon index" ) );
    }
}
