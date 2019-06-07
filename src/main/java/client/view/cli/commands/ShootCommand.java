package client.view.cli.commands;

import server.controller.RemotePlayer;
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
