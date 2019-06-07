package client.view.cli.commands;

import server.controller.RemotePlayer;
import java.io.IOException;

public class ChooseWeaponOfMineCommand extends CliCommand {
    ChooseWeaponOfMineCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOfMine( inputRequire.requestInt( "Insert weapon index" ) );
    }
}
