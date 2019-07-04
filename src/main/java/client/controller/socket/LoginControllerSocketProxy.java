package client.controller.socket;

import client.view.AbstractView;
import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import static common.Constants.*;
import static common.enums.ControllerMethodsEnum.*;

public class LoginControllerSocketProxy extends AbstractSocketProxy implements RemoteLoginController {

    private AbstractView view;
    private BlockingQueue<String[]> buffer = new PriorityBlockingQueue<>(10, (a1, a2) -> a1[0].compareToIgnoreCase(a2[0]));


    public LoginControllerSocketProxy(Socket socket, AbstractView view) throws IOException {
        super(socket);
        this.view = view;
        Thread handler = new Thread( new ClientSocketHandler( socket,view,buffer ));
        handler.setName( getClass().getName() );
        handler.setDaemon( true );
        handler.start();
        
    }


    @Override
    public synchronized UUID register(String username, RemoteView view) {
        out.println(SIGN_UP.toString() + REGEX + username);
        out.flush();
        String stringToken;
        try {
            stringToken = buffer.take()[0];
            return UUID.fromString(stringToken);
        } catch (InterruptedException | IllegalArgumentException invalidToken) {
            this.view.printMessage("Connection issues: " + invalidToken.getMessage());
            return null;
        }
    }


    @Override
    public synchronized RemotePlayer login(UUID token, RemoteView view) throws IOException, PlayerAlreadyLoggedInException {
        if (token != null) {
            RemotePlayer tmpPlayer = new PlayerSocketProxy(socket, token);
            out.println(LOG_IN + REGEX + token);
            out.flush();
            try {
                String outcome = buffer.take()[0];
                if (outcome.equals(SUCCESS))
                    return tmpPlayer;
                else if (outcome.equals(FAIL)){
                    throw new PlayerAlreadyLoggedInException();
                }
            } catch (InterruptedException e) {
                this.view.printMessage("Connection issues: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public synchronized void joinLobby(UUID token) {
        out.println(JOIN_LOBBY.toString() + REGEX + token);
        out.flush();
    }
}
