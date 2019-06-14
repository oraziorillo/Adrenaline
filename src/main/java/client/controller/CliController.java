package client.controller;

import client.controller.socket.LoginControllerSocketProxy;
import client.view.InputReader;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public class CliController extends UnicastRemoteObject implements AbstractClientController, RemoteView, Serializable {


    private final transient InputReader inputReader;


    public CliController(InputReader in) throws RemoteException {
        super();
        inputReader = in;
    }


    @Override
    public RemoteLoginController getLoginController() throws IOException, NotBoundException {
        RemoteLoginController controller;
        String cmd;
        HashSet<String> socketCommands = new HashSet<>(Arrays.asList("s", "socket"));
        HashSet<String> rmiCommands = new HashSet<>(Arrays.asList("r", "rmi"));
        boolean validCommand;
        do {
            cmd = inputReader.requestString("Choose a connection method:"+System.lineSeparator()+" - (s)ocket"+System.lineSeparator()+" - (r)mi").toLowerCase();
            System.out.println();
            validCommand = socketCommands.contains(cmd) || rmiCommands.contains(cmd);
            if (!validCommand)
                System.out.println("Illegal command");
        } while (!validCommand);

        if (socketCommands.contains(cmd)) {
            Socket socket = new Socket(HOST, SOCKET_PORT);
            controller = new LoginControllerSocketProxy(socket);
        } else {  //if the loop has ended, cmd is an rmi command
            Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
            controller = (RemoteLoginController) registry.lookup("LoginController");
        }
        return controller;
    }


    @Override
    public RemotePlayer loginRegister(RemoteLoginController loginController) throws IOException, PlayerAlreadyLoggedInException, ClassNotFoundException {
        UUID token = null;
        HashSet<String> yesAnswers = new HashSet<>(Arrays.asList("y", "yes"));
        HashSet<String> noAnswers = new HashSet<>(Arrays.asList("n", "no"));
        do {
            String cmd = inputReader.requestString("Do you have a login token?").toLowerCase();
            System.out.println();
            if (yesAnswers.contains(cmd)) {
                token = UUID.fromString(inputReader.requestString("Insert your token"));
            } else if (noAnswers.contains(cmd)) {
                String username;
                username = inputReader.requestString("Insert an username");
                System.out.println();
                token = loginController.register(username);
            } else {
                System.out.println("Illegal command\n");
            }
        } while (token == null);
        RemotePlayer player = loginController.login(token,this);
        System.out.println("This is your token: " + token + "\n\nUse it to login next time\n");
        loginController.joinLobby(token);
        return player;
    }


    @Override
    public synchronized void ack(String message) {
        System.out.println(message);
        System.out.println();
        //TODO: non funziona rmi in nessun modo
    }
}
