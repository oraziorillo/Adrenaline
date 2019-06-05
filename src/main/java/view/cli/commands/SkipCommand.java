package view.cli.commands;

import common.RemotePlayer;

import java.io.IOException;

class SkipCommand extends CliCommand {
   SkipCommand(RemotePlayer controller, boolean gui) {
      super(controller, gui );
   }
   
   @Override
   public void execute() throws IOException {
      controller.skip();
   }
}
