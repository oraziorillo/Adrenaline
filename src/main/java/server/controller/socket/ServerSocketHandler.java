package server.controller.socket;

import common.enums.SocketLoginEnum;
import common.enums.SocketPlayerEnum;
import common.remote_interfaces.RemotePlayer;
import server.controller.LoginController;
import server.exceptions.PlayerAlreadyLoggedInException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class ServerSocketHandler implements Runnable {
    private final Socket socket;
    private final PrintWriter out;
    private final Scanner in;
    private LoginController loginController;
    private RemotePlayer player;

    public ServerSocketHandler(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream());
        this.in = new Scanner(socket.getInputStream());
        loginController = LoginController.getInstance();
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            String[] args = in.next().split(",");

            for (String s : args) System.out.println(s);//for debug

            try {
                handleLoginController(args);
                handlePlayerController(args);
            } catch (IOException e) {
                try {
                    socket.close();
                    out.close();
                    in.close();
                    if (player != null) player.quit();
                } catch (IOException ignored) {
                }
            } catch (IllegalArgumentException ignored) {
            } //only one of the "handle..." method will not throw it

        }
    }

    /**
     * Parser for login controller methods
     *
     * @param args the strings sent by the client
     * @throws IOException              if user is unreachable
     * @throws IllegalArgumentException if the method name (args[0]) is not in SocketLoginEnum
     * @see SocketLoginEnum
     */
    private void handleLoginController(String[] args) throws IOException {
        switch (SocketLoginEnum.valueOf(args[0])) {
            case REGISTER:
                out.println(loginController.register(args[1], new RemoteViewSocketProxy(socket)));
                out.flush();
                break;
            case LOGIN:
                this.player = loginController.login(UUID.fromString(args[1]));
                break;
            case JOIN_LOBBY:
                try {
                    loginController.joinLobby(UUID.fromString(args[1]));
                } catch (PlayerAlreadyLoggedInException e) {
                    e.printStackTrace(); //TODO
                }
                break;
            //TODO: incompleta
        }
    }

    /**
     * Parser for RemotePlayer methods
     *
     * @param args the strings to parse
     * @throws IOException              if client is unreachable
     * @throws IllegalArgumentException if the method name (args[0]) is not in SocketPlayerEnum
     * @see SocketPlayerEnum
     */
    private void handlePlayerController(String[] args) throws IOException {
        int argInt;
        switch (SocketPlayerEnum.valueOf(args[0])) {
            case CHOOSE_MAP:
                argInt = Integer.parseInt(args[1]);
                player.chooseMap(argInt);
                break;
            case CHOOSE_NUMBER_OF_SKULLS:
                argInt = Integer.parseInt(args[1]);
                player.chooseNumberOfSkulls(argInt);
                break;
            case CHOOSE_PC_COLOUR:
                player.choosePcColour(args[1]);
                break;
            case RUN_AROUND:
                player.runAround();
                break;
            case GRAB_STUFF:
                player.grabStuff();
                break;
            case SHOOT_PEOPLE:
                player.shootPeople();
                break;
            case SELECT_SQUARE:
                argInt = Integer.parseInt(args[1]);
                int argInt2 = Integer.parseInt(args[2]);
                player.chooseSquare(argInt, argInt2);
                break;
            case GRAB_WEAPON:
                argInt = Integer.parseInt(args[1]);
                player.chooseWeaponOnSpawnPoint(argInt);
                break;
            case CHOOSE_WEAPON:
                argInt = Integer.parseInt(args[1]);
                player.chooseWeaponOfMine(argInt);
                break;
            case QUIT:
                player.quit();
                break;
            case SWITCH_FIREMODE:
                player.switchFireMode();
                break;
            case UPGRADE:
                player.upgrade();
                break;
            case CHOOSE_ASYNCH_EFFECT_ORDER:
                boolean beforeBasicEffect = Boolean.getBoolean(args[1]);
                player.chooseAsynchronousEffectOrder(beforeBasicEffect);
                break;
            case OK:
                player.ok();
                break;
            case RELOAD:
                player.reload();
                break;
            case PASS:
                player.pass();
                break;
            //TODO: incompleta
        }
    }
}
