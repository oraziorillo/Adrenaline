package client.view.cli;

import client.controller.socket.ClientSocketHandler;
import client.view.AbstractView;
import client.view.InputReader;
import common.enums.ConnectionMethodEnum;
import common.enums.ControllerMethodsEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteLoginController;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class CliView extends AbstractView {

    private transient InputReader inputReader;


    public CliView() throws RemoteException {
        this.inputReader = new CliInputReader();
    }

    @Override
    public void printMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public ConnectionMethodEnum acquireConnectionMethod() {
        ConnectionMethodEnum cme = null;
        do {
            try {
                cme = ConnectionMethodEnum.parseString(inputReader.requestString(
                        "Choose a connection method:" + System.lineSeparator() +
                         "s  ->  socket" + System.lineSeparator() +
                         "r  ->  rmi")
                        .toLowerCase());
            } catch (IllegalArgumentException e) {
                printMessage(e.getMessage());
            }
        } while (cme == null);
        return cme;
    }


    @Override
    public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
        switch (cme) {
            case SOCKET:
                try {
                    Socket socket = new Socket(HOST, SOCKET_PORT);
                    ClientSocketHandler handler = new ClientSocketHandler(socket, this);
                    new Thread(handler).start();
                    return handler;
                } catch (IOException e) {
                    printMessage("Server unreachable");
                }
                break;
            case RMI:
                try {
                    Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
                    return (RemoteLoginController) registry.lookup("LoginController");
                } catch (RemoteException | NotBoundException e) {
                    printMessage("Server unreachable");
                }
                break;
            default:
                throw new IllegalArgumentException("Oak's words echoed... There's a time and place for everything, but not now");
        }
        return acquireConnection(cme);
    }


    @Override
    public boolean wantsToRegister() {
        ControllerMethodsEnum cmd = null;
        do {
            try {
                cmd = ControllerMethodsEnum.parseString(inputReader.requestString(
                        "r  ->  register" + System.lineSeparator() +
                        "l  ->  login")
                        .toLowerCase());
            } catch (IllegalArgumentException e) {
                printMessage(e.getMessage());
            }
        } while (cmd == null);

        switch (cmd) {
            case REGISTER:
                return true;
            case LOGIN:
                return false;
            default:
                throw new IllegalArgumentException("Oak's words echoed... There's a time and place for everything, but not now");
        }
    }


    @Override
    public String acquireUsername() {
        return inputReader.requestString("Insert a username");
    }

    @Override
    public UUID acquireToken() {
        try {
            return UUID.fromString(inputReader.requestString("Insert your token"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    @Override
    public String requestString(String message) {
        return inputReader.requestString(message);
    }


    @Override
    public void ack(String message) throws RemoteException{
        printMessage(message + "\n");
    }


    @Override
    public void error(String msg) throws RemoteException{
        inputReader.requestString(msg + System.lineSeparator() + "Press enter to exit.");
        System.exit(1);
    }


    @Override
    public ModelEventListener getListener() throws RemoteException{
        return this;
    }


    @Override
    public void onGameBoardUpdate(GameBoardEvent event) throws RemoteException {
        printMessage(">>> " + event + "\n");
    }

    @Override
    public void onKillShotTrackUpdate(KillShotTrackEvent event) throws RemoteException {
        printMessage(">>> " + event + "\n");
    }

    @Override
    public void onPcBoardUpdate(PcBoardEvent event) throws RemoteException {
        printMessage(">>> " + event + "\n");
    }

    @Override
    public void onPcUpdate(PcEvent event) throws RemoteException {
        printMessage(">>> " + event + "\n");
    }

    @Override
    public void onSquareUpdate(SquareEvent event) throws RemoteException {
        printMessage(">>> " + event + "\n");
    }
}
