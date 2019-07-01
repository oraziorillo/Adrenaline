package client.controller.socket;

import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import common.remote_interfaces.RemoteView;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static common.Constants.*;
import static common.Constants.REGEX;
import static common.enums.ControllerMethodsEnum.*;

public class LoginControllerSocketProxy extends AbstractSocketProxy implements RemoteLoginController {

    private UUID token;

    LoginControllerSocketProxy(Socket socket, ) throws IOException {
        super(socket);
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
            this.token = token;
            new Thread(this).start();
            out.println(LOG_IN + REGEX + token);
            out.flush();
            try {
                String outcome = buffer.take()[0];
                if (outcome.equals(SUCCESS))
                    return this;
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
