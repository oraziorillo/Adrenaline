package client.view.cli;

import client.view.InputReader;

import java.io.InputStream;
import java.util.Scanner;

/**
 * requires some input to the user, via cli/terminal or other input stream reader
 */
public class CliInputReader implements InputReader {

    private final Scanner in;

    CliInputReader(InputStream in) {
        this.in = new Scanner(in);
    }

    CliInputReader() {
        this(System.in);
    }

    /**
     * @param message the text to show to the user
     * @return an integer inserted by the user
     */
    @Override
    public int requestInt(String message) {
        System.out.print(message + "\n" + ">>> ");
        return in.nextInt();
    }


    @Override
    public String requestString(String message) {
        System.out.print(message + "\n" + ">>> ");
        return in.next();
    }
}
