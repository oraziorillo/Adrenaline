package server.controller.socket;

import common.enums.interfaces_names.SocketPlayerEnum;
import common.remote_interfaces.RemotePlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerSocketListener implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final RemotePlayer player;

    /**
     * Creates a connection listener by an existing socket
     * @param socket the given socket, created by ServerSocket.accept and then already opened
     */
    PlayerSocketListener(Socket socket, RemotePlayer player) throws IOException {
        this.socket = socket;
        this.player = player;
        out = new PrintWriter( socket.getOutputStream() );
        out.flush();
        in = new BufferedReader( new InputStreamReader( socket.getInputStream() ));
    }
    
    /**
     * While the socket is opened, ciclically listens for a command and executes it
     */
    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                SocketPlayerEnum command = SocketPlayerEnum.valueOf( in.readLine() );
                int argInt;
                switch (command) {
                    case CHOOSE_MAP:
                        argInt = Integer.parseInt( in.readLine() );
                        player.chooseMap( argInt );
                        break;
                    case CHOOSE_NUMBER_OF_SKULLS:
                        argInt = Integer.parseInt( in.readLine() );
                        player.chooseNumberOfSkulls( argInt );
                        break;
                    case CHOOSE_PC_COLOUR:
                        player.choosePcColour( in.readLine() );
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
                        argInt = Integer.parseInt( in.readLine() );
                        int argInt2 = Integer.parseInt( in.readLine() );
                        player.chooseSquare( argInt, argInt2 );
                        break;
                    case GRAB_WEAPON_ON_SPAWNPOINT:
                        argInt = Integer.parseInt( in.readLine() );
                        player.chooseWeaponOnSpawnPoint( argInt );
                        break;
                    case CHOOSE_WEAPON_OF_MINE:
                        argInt = Integer.parseInt( in.readLine() );
                        player.chooseWeaponOfMine( argInt );
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
                        boolean beforeBasicEffect = Boolean.getBoolean( in.readLine() );
                        player.chooseAsynchronousEffectOrder( beforeBasicEffect );
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
                    default:
                        throw new IllegalArgumentException( "Unexpected command" );

                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
