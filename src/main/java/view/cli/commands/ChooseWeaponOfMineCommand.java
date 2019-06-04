package view.cli.commands;

import common.RemotePlayer;
import java.io.IOException;

public class ChooseWeaponOfMineCommand extends CliCommand {
    ChooseWeaponOfMineCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOfMine( inputRequier.askInt( "Insert weapon index" ) );
    }
}
