package view.cli.conection.commands;

import controller.RemoteController;

import java.io.IOException;

public class QuitCommand extends CliCommand {
    QuitCommand(RemoteController controller, boolean gui) {
        super(controller, gui);
    }

    @Override
    public void execute() throws IOException {
        controller.quit();
    }
}
