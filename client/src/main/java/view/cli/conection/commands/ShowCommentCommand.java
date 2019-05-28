package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

class ShowCommentCommand extends CliCommand {
    ShowCommentCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.showComment( inputRequier.askString() );
    }
}
