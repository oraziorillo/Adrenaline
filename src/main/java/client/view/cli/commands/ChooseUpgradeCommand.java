package client.view.cli.commands;

import common.remote_interfaces.RemotePlayer;

import java.io.IOException;

public class ChooseUpgradeCommand extends CliCommand {

    ChooseUpgradeCommand(RemotePlayer controller) {
        super(controller);
    }

    @Override
    public void execute() throws IOException {
        controller.chooseUpgrade( inputReader.requestInt( "Insert upgrade index" ));
    }
}
