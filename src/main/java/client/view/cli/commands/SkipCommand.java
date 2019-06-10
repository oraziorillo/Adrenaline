package client.view.cli.commands;

import common.rmi_interfaces.RemotePlayer;

import java.io.IOException;

class SkipCommand extends CliCommand {
   SkipCommand(RemotePlayer controller) {
      super(controller );
   }
   
   @Override
   public void execute() throws IOException {
      controller.skip();
   }
}
