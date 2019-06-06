package server;

public class LaunchServer {

    public static void main(String[] args) throws Exception {

        new Thread(new SocketServer()).start();
        new Thread(new RmiServer()).start();

    }
}
