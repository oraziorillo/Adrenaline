package view.cli.conection.commands;

import common.RemoteController;

import java.io.IOException;

class SkipCommand extends CliCommand {
   SkipCommand(RemoteController controller, boolean gui) {
      super(controller, gui );
   }
   
   @Override
   public void execute() throws IOException {
      controller.skip();
   }
}
