package client.cli.commands;

import server.RemotePlayer;

import java.io.IOException;

class QuitCommand extends CliCommand {
    QuitCommand(RemotePlayer controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.quit();
    }
}
