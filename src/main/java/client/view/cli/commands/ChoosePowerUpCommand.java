package client.view.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

public class ChoosePowerUpCommand extends CliCommand {

    ChoosePowerUpCommand(RemotePlayer controller) {
        super(controller);
    }

    @Override
    public void execute() throws IOException {
        controller.choosePowerUp( inputReader.requestInt( "Insert powerUp index" ));
    }
}
