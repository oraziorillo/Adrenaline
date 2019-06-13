package client;

import client.controller.CliController;
import client.view.InputReader;
import client.view.cli.CliInputReader;
import client.view.cli.commands.CliCommand;
import client.view.cli.commands.CommandFactory;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import server.exceptions.PlayerAlreadyLoggedInException;
import server.exceptions.PlayerAlreadyRegisteredException;

import java.io.IOException;
import java.rmi.NotBoundException;

public class LaunchCli {

    public static void main(String[] args) throws IOException, NotBoundException, PlayerAlreadyLoggedInException, ClassNotFoundException, PlayerAlreadyRegisteredException {
        InputReader input = new CliInputReader();
        CliController clientController = new CliController(input);
        RemoteLoginController loginController = clientController.getLoginController();
        RemotePlayer player = clientController.loginRegister( loginController );
        while (true){
            CliCommand command = CommandFactory.getCommand( input.requestString( "Insert command:"),player );
            command.execute();
        }
    }
}
