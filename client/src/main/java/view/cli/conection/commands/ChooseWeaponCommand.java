package view.cli.conection.commands;

import controller.RemoteController;

import java.io.IOException;

public class ChooseWeaponCommand extends CliCommand {
    public ChooseWeaponCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.chooseWeapon( inputRequier.askInt( "Insert weapon index" ) );
    }
}
