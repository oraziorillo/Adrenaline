package client.view.cli.commands;

import common.RemotePlayer;
import java.io.IOException;

class RunAroundCommand extends CliCommand {
    RunAroundCommand(RemotePlayer controller) {
        super(controller );
    }

    @Override
    public void execute() throws IOException {
        controller.runAround();
    }
}
