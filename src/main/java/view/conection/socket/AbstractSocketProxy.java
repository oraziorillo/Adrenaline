package view.conection.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class AbstractSocketProxy {
   protected static final String IP = "localhost";
   protected static final int PORT = 10000;
   protected final Socket socket;
   protected final PrintWriter out;
   protected final BufferedReader in;
   public AbstractSocketProxy() throws IOException {
      this.socket = new Socket( IP, PORT );
      out = new PrintWriter( socket.getOutputStream() );
      in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
   }
}
