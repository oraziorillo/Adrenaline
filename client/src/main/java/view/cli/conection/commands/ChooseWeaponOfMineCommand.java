package view.cli.conection.commands;

import common.RemoteController;
import java.io.IOException;

public class ChooseWeaponOfMineCommand extends CliCommand {
    ChooseWeaponOfMineCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeaponOfMine( inputRequier.askInt( "Insert weapon index" ) );
    }
}
