package client.view.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

public class SwitchFireModeCommand extends CliCommand {

    SwitchFireModeCommand(RemotePlayer controller) {
        super(controller);
    }

    @Override
    public void execute() throws IOException {
        controller.switchFireMode();
    }
}
