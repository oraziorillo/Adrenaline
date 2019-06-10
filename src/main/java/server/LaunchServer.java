package server;

public class LaunchServer {

    public static void main(String[] args) {

        new Thread(new SocketServer()).start();
        new Thread(new RmiServer()).start();

    }
}
