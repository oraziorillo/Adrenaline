package client.controller;

import common.remote_interfaces.RemoteLoginController;
import common.remote_interfaces.RemotePlayer;
import server.exceptions.PlayerAlreadyLoggedInException;
import server.exceptions.PlayerAlreadyRegisteredException;

import java.io.IOException;
import java.rmi.NotBoundException;

public interface AbstractClientController {


   String HOST = "localhost";
   int SOCKET_PORT = 10000;
   int RMI_PORT = 9999;


   RemoteLoginController getLoginController() throws IOException, NotBoundException;


   RemotePlayer loginRegister(RemoteLoginController loginController) throws IOException, PlayerAlreadyLoggedInException, PlayerAlreadyRegisteredException, ClassNotFoundException;
}
