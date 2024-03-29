package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class AbstractSocketProxy {

    protected final Socket socket;
    protected final PrintWriter out;
    protected final BufferedReader in;


    public AbstractSocketProxy(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

}
