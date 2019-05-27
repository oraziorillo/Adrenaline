package view.cli.conection.commands;

import common.RemoteController;
import java.io.IOException;

public class RunAroundCommand extends CliCommand {
    RunAroundCommand(RemoteController controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.runAround();
    }
}
