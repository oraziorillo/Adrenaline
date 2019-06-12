package client.view.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

class QuitCommand extends CliCommand {
    QuitCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.quit();
    }
}
