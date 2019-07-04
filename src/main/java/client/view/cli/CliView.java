package client.view.cli;

import client.controller.socket.LoginControllerSocketProxy;
import client.view.AbstractView;
import client.view.InputReader;
import common.enums.ConnectionMethodEnum;
import common.enums.ControllerMethodsEnum;
import common.events.ModelEventListener;
import common.events.game_board_events.GameBoardEvent;
import common.events.kill_shot_track_events.KillShotTrackEvent;
import common.events.lobby_events.LobbyEvent;
import common.events.pc_board_events.PcBoardEvent;
import common.events.pc_events.PcEvent;
import common.events.requests.Request;
import common.events.square_events.SquareEvent;
import common.remote_interfaces.RemoteLoginController;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

import static common.Constants.WRONG_TIME;

public class CliView extends AbstractView {

    private transient InputReader inputReader;


    public CliView() throws RemoteException {
        super();
        this.inputReader = new CliInputReader();
    }


    @Override
    public String nextCommand() {
        return inputReader.requestCommand("Insert command\n").toLowerCase();
    }


    @Override
    public void printMessage(String msg) {
        if(msg.length() != 0)
            System.out.println(msg);
    }


    @Override
    public ConnectionMethodEnum acquireConnectionMethod() {
        ConnectionMethodEnum cme = null;
        do {
            try {
                cme = ConnectionMethodEnum.parseString(inputReader.requestString(
                        "Please, provide a connection method:\n" +
                                "\t:s\t\tSocket\n" +
                                "\t:r\t\tRmi")
                        .toLowerCase());
            } catch (IllegalArgumentException e) {
                printMessage(e.getMessage());
            }
        } while (cme == null);
        return cme;
    }


    @Override
    public RemoteLoginController acquireConnection(ConnectionMethodEnum cme) {
        try {
            switch (cme) {
                case SOCKET:
                    Socket socket = new Socket(HOST, SOCKET_PORT);
                    return new LoginControllerSocketProxy(socket, this);
                case RMI:
                    Registry registry = LocateRegistry.getRegistry(HOST, RMI_PORT);
                    return (RemoteLoginController) registry.lookup("LoginController");
                default:
                    throw new IllegalArgumentException(WRONG_TIME);
            }
        } catch (IOException | NotBoundException e) {
            printMessage("Server unreachable");
            return null;
        }
    }


    @Override
    public boolean wantsToRegister() {
        ControllerMethodsEnum cmd = null;
        do {
            try {
                cmd = ControllerMethodsEnum.parseString(inputReader.requestString(
                        "Are you new?\n" +
                                "\t:s\t\tSign up\n" +
                                "\t:l\t\tLog in")
                        .toLowerCase());
            } catch (IllegalArgumentException e) {
                printMessage(e.getMessage());
            }
        } while (cmd == null);

        switch (cmd) {
            case SIGN_UP:
                return true;
            case LOG_IN:
                return false;
            default:
                throw new IllegalArgumentException(WRONG_TIME);
        }
    }


    @Override
    public String acquireUsername() {
        return inputReader.requestString("Please, provide a username");
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
    public synchronized void ack(String message) throws RemoteException {
        if (message.length() != 0) {
            printMessage(message);
        }
    }
    
    @Override
    public void chatMessage(String message) throws RemoteException {
        //TODO: orazio, ho aggiunto questo metodo per differenziare i messaggi dagli utenti
    }
    
    
    @Override
    public synchronized void error(String msg){
        inputReader.requestString(msg + System.lineSeparator() + "Press enter to exit.");
        System.exit(1);
    }


    @Override
    public synchronized void notifyEvent(LobbyEvent event) throws RemoteException {
        printMessage(event.toString());
    }


    @Override
    public void request(Request request) throws RemoteException {
        printMessage(request.toString());
    }


    @Override
    public synchronized ModelEventListener getListener() throws RemoteException{
        return this;
    }


    @Override
    public boolean isReachable() throws RemoteException, IOException {
        return true;
    }


    @Override
    public synchronized void onGameBoardUpdate(GameBoardEvent event) throws RemoteException {
        printMessage(event.toString());
        event.getDTO().getSquares().forEach(s -> printMessage( "\n" + s.description()));
    }


    @Override
    public synchronized void onKillShotTrackUpdate(KillShotTrackEvent event) throws RemoteException {
        printMessage(event.toString());
    }


    @Override
    public synchronized void onPcBoardUpdate(PcBoardEvent event) throws RemoteException {
        printMessage(event.toString());
    }


    @Override
    public synchronized void onPcUpdate(PcEvent event) throws RemoteException {
        printMessage(event.toString());
    }


    @Override
    public synchronized void onSquareUpdate(SquareEvent event) throws RemoteException {
        printMessage(event.toString());
    }
}
