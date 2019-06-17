package client.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

public class UsePowerUpCommand extends CliCommand {


    UsePowerUpCommand(RemotePlayer controller) {
        super(controller);
    }

    @Override
    public void execute() throws IOException {
        controller.usePowerUp();
    }
}
