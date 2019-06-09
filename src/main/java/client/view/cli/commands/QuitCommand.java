package client.view.cli.commands;

import server.controller.RemotePlayer;

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
