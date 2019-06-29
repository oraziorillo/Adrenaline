package client.view.cli;

import client.controller.socket.ClientSocketHandler;
import client.view.AbstractView;
import client.view.InputReader;
import common.enums.ConnectionsEnum;
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
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class CliView extends UnicastRemoteObject implements AbstractView {

    private transient InputReader inputReader;

    public CliView() throws RemoteException {
        this.inputReader = new CliInputReader();
    }

    @Override
    public RemoteLoginController acquireConnection() {
        ConnectionsEnum cmd = null;
        do {
            try {
                cmd = ConnectionsEnum.parseString(inputReader.requestString(
                        "Choose a connection method:" + System.lineSeparator() +
                         "s  ->  socket" + System.lineSeparator() +
                         "r  ->  rmi")
                        .toLowerCase());
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (cmd == null);
        switch (cmd) {
            case SOCKET:
                try {
                    Socket socket = new Socket(HOST, SOCKET_PORT);
                    ClientSocketHandler handler = new ClientSocketHandler(socket, this);
                    new Thread(handler).start();
                    return handler;
                } catch (IOException e) {
                    error("Server unreachable");
                }
            case RMI:
                try {
                    Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
                    return (RemoteLoginController) registry.lookup("LoginController");
                } catch (RemoteException | NotBoundException e) {
                    error("Server unreachable");
                }
            default:
                throw new IllegalArgumentException("Oak's words echoed... There's a time and place for everything, but not now");
        }

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
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
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
        return UUID.fromString(inputReader.requestString("Insert your token"));
    }

    @Override
    public String requestString(String message) {
        return inputReader.requestString(message);
    }

    @Override
    public void error(String msg) {
        inputReader.requestString(msg + System.lineSeparator() + "Press enter to exit.");
        System.exit(1);
    }

    /**
     * Cause every message is immediately displayed in the cli, no acks are pending
     *
     * @return an empty Collection
     */
    @Override
    public Collection<String> getPendingAcks() {
        return new HashSet<>();
    }


    @Override
    public void ack(String message) {
        System.out.println(message);
    }


    @Override
    public ModelEventListener getListener() {
        return this;
    }


    @Override
    public void onGameBoardUpdate(GameBoardEvent event) throws IOException {
        System.out.println(event);
    }

    @Override
    public void onKillShotTrackUpdate(KillShotTrackEvent event) throws IOException {
        System.out.println(event);
    }

    @Override
    public void onPcBoardUpdate(PcBoardEvent event) throws IOException {
        System.out.println(event);
    }

    @Override
    public void onPcUpdate(PcEvent event) throws IOException {
        System.out.println(event);
    }

    @Override
    public void onSquareUpdate(SquareEvent event) throws IOException {
        System.out.println(event);
    }
}
