package client.view.cli.commands;

import server.controller.RemotePlayer;

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
