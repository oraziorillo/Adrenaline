package client;

import client.view.cli.CliView;
import client.view.cli.commands.CliCommand;
import client.view.cli.commands.CommandFactory;
import server.controller.RemotePlayer;

import java.io.IOException;
import java.rmi.NotBoundException;

public class LaunchCli {

    public static void main(String[] args) throws IOException, NotBoundException {
        CliView view = new CliView();
        view.setupConnection();
        RemotePlayer player = view.login_register();
        while (true){
            CliCommand command = CommandFactory.getCommand( view.requestString( "Insert command:"),player );
            command.execute();
        }
    }
}
