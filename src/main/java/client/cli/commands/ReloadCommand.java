package client.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

public class ReloadCommand extends CliCommand {

    ReloadCommand(RemotePlayer controller) {
        super(controller);
    }

    @Override
    public void execute() throws IOException {
        controller.reload();
    }
}
