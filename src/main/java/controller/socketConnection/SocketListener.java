package controller.socketConnection;

import controller.Controller;
import model.enumerations.PcColourEnum;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketListener implements Runnable {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Controller controller;

    /**
     * Creates a connection listener by an existing socket
     * @param socket the given socket, created by ServerSocket.accept and then already opened
     */
    SocketListener(Socket socket, Controller controller) throws IOException {
        this.socket = socket;
        this.controller = controller;
        out = new ObjectOutputStream( socket.getOutputStream() );
        in = new ObjectInputStream( socket.getInputStream() );
    }


    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                CommandsEnum command = ( CommandsEnum ) in.readObject();
                int argInt;
                switch (command) {
                    case CHOOSE_MAP:
                        argInt = in.readInt();
                        controller.chooseMap( argInt );
                        break;
                    case CHOOSE_NUMBER_OF_SKULLS:
                        argInt = in.readInt();
                        controller.chooseNumberOfSkulls( argInt );
                        break;
                    case CHOOSE_PC_COLOUR:
                        PcColourEnum colour = ( PcColourEnum ) in.readObject();
                        controller.choosePcColour( colour );
                        break;
                    case DISCARD_AND_SPAWN:
                        argInt = in.readInt();
                        controller.discardAndSpawn( argInt );
                        break;
                    case SHOW_COMMENT:
                        String comment = ( String ) in.readObject();
                        controller.showComment( comment );
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
                        argInt = in.readInt();
                        int argInt2 = in.readInt();
                        controller.selectSquare( argInt, argInt2 );
                        break;
                    case GRAB_WEAPON:
                        argInt = in.readInt();
                        controller.grabWeapon( argInt );
                        break;
                    case CHOOSE_WEAPON:
                        argInt = in.readInt();
                        controller.chooseWeapon( argInt );
                        break;
                    case QUIT:
                        controller.quit();
                        break;
                    default:
                        throw new IllegalArgumentException( "Unexpected command" );

                }
            } catch ( IOException e ) {
                e.printStackTrace();
            } catch ( ClassNotFoundException e ) {
                e.printStackTrace();
            }
        }
    }
}
