
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SocketServer-side main class
 */
public class SocketServer implements Runnable {

    @Override
    public void run() {

        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(10000)){

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                pool.submit(new LoginSocketListener(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}