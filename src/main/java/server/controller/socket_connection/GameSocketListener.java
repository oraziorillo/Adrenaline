package server.controller.socket_connection;
/*
import server.controller.Controller;
import server.enums.SocketCommandsEnum;
import server.enums.PcColourEnum;

import java.io.*;
import java.net.Socket;

public class GameSocketListener implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final Controller controller;

    /**
     * Creates a connection listener by an existing socket
     * @param socket the given socket, created by ServerSocket.accept and then already opened
     *
    GameSocketListener(Socket socket, Controller controller) throws IOException {
        this.socket = socket;
        this.controller = controller;
        out = new PrintWriter( socket.getOutputStream() );
        out.flush();
        in = new BufferedReader( new InputStreamReader( socket.getInputStream() ));
    }
    
    /**
     * While the socket is opened, cyclically listens for a command and executes it
     *
    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                SocketCommandsEnum command = SocketCommandsEnum.valueOf( in.readLine() );
                int argInt;
                switch (command) {
                    case CHOOSE_MAP:
                        argInt = Integer.parseInt( in.readLine() );
                        controller.chooseMap( argInt );
                        break;
                    case CHOOSE_NUMBER_OF_SKULLS:
                        argInt = Integer.parseInt( in.readLine() );
                        controller.chooseNumberOfSkulls( argInt );
                        break;
                    case CHOOSE_PC_COLOUR:
                        PcColourEnum colour = PcColourEnum.fromString( in.readLine() );
                        controller.choosePcColour( colour );
                        break;
                    case RUN_AROUND:
                        controller.runAround();
                        break;
                    case GRAB_STUFF:
                        controller.grabStuff();
                        break;
                    case SHOOT_PEOPLE:
                        controller.shootPeople();
                        break;
                    case SELECT_SQUARE:
                        argInt = Integer.parseInt( in.readLine() );
                        int argInt2 = Integer.parseInt( in.readLine() );
                        controller.chooseSquare( argInt, argInt2 );
                        break;
                    case GRAB_WEAPON:
                        argInt = Integer.parseInt( in.readLine() );
                        controller.chooseWeaponOnSpawnPoint( argInt );
                        break;
                    case CHOOSE_WEAPON:
                        argInt = Integer.parseInt( in.readLine() );
                        controller.chooseWeaponOfMine( argInt );
                        break;
                    case QUIT:
                        controller.quit();
                        break;
                    case SWITCH_FIREMODE:
                        controller.switchFireMode();
                        break;
                    case UPGRADE:
                        controller.upgrade();
                        break;
                    case CHOOSE_ASYNCH_EFFECT_ORDER:
                        boolean beforeBasicEffect = Boolean.getBoolean( in.readLine() );
                        controller.chooseAsynchronousEffectOrder( beforeBasicEffect );
                        break;
                    case OK:
                        controller.ok();
                        break;
                    case RELOAD:
                        controller.reload();
                        break;
                    case PASS:
                        controller.pass();
                        break;
                    default:
                        throw new IllegalArgumentException( "Unexpected command" );

                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}

 */
