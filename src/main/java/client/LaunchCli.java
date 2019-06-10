package client;

import client.view.InputReader;
import client.view.cli.CliInputReader;
import client.view.cli.commands.CliCommand;
import client.view.cli.commands.CommandFactory;
import client.view.cli.controller.AbstractClientController;
import client.view.cli.controller.CliController;
import common.rmi_interfaces.RemoteLoginController;
import common.rmi_interfaces.RemotePlayer;

import java.io.IOException;
import java.rmi.NotBoundException;

public class LaunchCli {

    public static void main(String[] args) throws IOException, NotBoundException {
        InputReader input = new CliInputReader();
        AbstractClientController clientController = new CliController(input);
        RemoteLoginController loginController = clientController.getLoginController();
        RemotePlayer player = clientController.loginRegister( loginController );
        while (true){
            CliCommand command = CommandFactory.getCommand( input.requestString( "Insert command:"),player );
            command.execute();
        }
    }
}
