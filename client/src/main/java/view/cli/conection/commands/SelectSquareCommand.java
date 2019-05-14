package view.cli.conection.commands;

import controller.RemoteController;

import java.io.IOException;

public class SelectSquareCommand extends CliCommand {
    public SelectSquareCommand(RemoteController controller, boolean gui) {
        super(controller, gui );
    }

    @Override
    public void execute() throws IOException {
        controller.selectSquare( inputRequier.askInt( "Insert squere column" ),
                                inputRequier.askInt( "Insert square row" ));
    }
}
