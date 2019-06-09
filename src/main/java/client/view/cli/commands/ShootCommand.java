package client.view.cli.commands;

import common.RemotePlayer;
import java.io.IOException;

final class ShootCommand extends CliCommand {

    ShootCommand(RemotePlayer controller) {
        super( controller );
    }

    @Override
    public void execute() throws IOException {
        controller.shootPeople();
    }
}
