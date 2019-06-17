package client.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

public class OKCommand extends CliCommand {

    OKCommand(RemotePlayer controller) {
        super(controller);
    }

    @Override
    public void execute() throws IOException {
        controller.ok();
    }
}
