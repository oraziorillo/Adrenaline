package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

final class ShootCommand extends CliCommand {

    ShootCommand(RemoteController controller, boolean gui) {
        super( controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.shootPeople();
    }
}
