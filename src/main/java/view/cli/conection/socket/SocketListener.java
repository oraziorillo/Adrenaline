package view.cli.conection.socket;

import java.io.IOException;

public class SocketListener implements Runnable {
    private SocketConnection connection;
    public SocketListener(SocketConnection socketConnection) {
        this.connection=socketConnection;
    }

    @Override
    public void run() {
        while (connection.socket.isConnected()){
            listen();
        }
    }

    private synchronized void listen() {
        try {
            if(connection.in.readLine().equalsIgnoreCase("update")){
                connection.update();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong reading from socket");
        }
    }
}
