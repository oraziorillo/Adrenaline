package view.cli.commands;

import common.RemotePlayer;
import java.io.IOException;

final class ShootCommand extends CliCommand {

    ShootCommand(RemotePlayer controller, boolean gui) {
        super( controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.shootPeople();
    }
}
