package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

public class ShowCommentCommand extends CliCommand {
    public ShowCommentCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.showComment( inputRequier.askString() );
    }
}
