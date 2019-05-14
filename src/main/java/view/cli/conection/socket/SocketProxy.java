package view.cli.conection.socket;

import controller.RemoteController;
import model.enumerations.PcColourEnum;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static controller.socketConnection.CommandsEnum.*;

public class SocketProxy implements RemoteController {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public SocketProxy(String ip, int port ) throws IOException {
        this(new Socket( ip, port));
    }

    public SocketProxy(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream( socket.getOutputStream() );
        out.flush();
        in = new ObjectInputStream( socket.getInputStream() );
    }

    public SocketProxy() throws IOException {
        this("localhost",10000);
    }

    @Override
    public void chooseMap(int n) throws IOException {
        out.writeObject( CHOOSE_MAP );
        out.writeInt( n );
        out.flush();
    }

    @Override
    public void chooseNumberOfSkulls(int n) throws IOException {
        out.writeObject( CHOOSE_NUMBER_OF_SKULLS );
        out.writeInt( n );
        out.flush();
    }

    @Override
    public void choosePcColour(PcColourEnum colour) throws IOException {
        out.writeObject( CHOOSE_PC_COLOUR );
        out.writeObject( colour );
        out.flush();
    }

    @Override
    public void discardAndSpawn(int n) throws IOException {
        out.writeObject( DISCARD_AND_SPAWN );
        out.writeInt( n );
        out.flush();
    }

    @Override
    public void showComment(String comment) throws IOException {
        out.writeObject( SHOW_COMMENT );
        out.writeObject( comment );
        out.flush();
    }

    @Override
    public void runAround() throws IOException {
        out.writeObject( RUN_AROUND );
        out.flush();
    }

    @Override
    public void grabStuff() throws IOException {
        out.writeObject( GRAB_STUFF );
        out.flush();
    }

    @Override
    public void shootPeople() throws IOException {
        out.writeObject( SHOOT_PEOPLE );
        out.flush();
    }

    @Override
    public void selectSquare(int x, int y) throws IOException {
        out.writeObject( SELECT_SQUARE );
        out.writeInt( x );
        out.writeInt( y );
        out.flush();
    }

    @Override
    public void grabWeapon(int n) throws IOException {
        out.writeObject( GRAB_WEAPON );
        out.writeInt( n );
        out.flush();
    }

    @Override
    public void chooseWeapon(int n) throws IOException {
        out.writeObject( CHOOSE_WEAPON );
        out.writeInt( n );
        out.flush();
    }

    @Override
    public void quit() throws IOException {
        out.writeObject( QUIT );
        out.close();
        in.close();
        socket.close();
    }
}
