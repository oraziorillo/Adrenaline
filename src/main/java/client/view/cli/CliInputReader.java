package client.view.cli;

import client.view.InputRequire;

import java.io.InputStream;
import java.util.Scanner;

/**
 * requires some input to the user, via cli/terminal or other input stream reader
 */
public class CliInputReader implements InputRequire {

    private final Scanner in;

    public CliInputReader(InputStream in) {
        this.in = new Scanner(in);
    }

    public CliInputReader() {
        this(System.in);
    }

    /**
     * @param message the text to show to the user
     * @return an integer inserted by the user
     */
    @Override
    public int requestInt(String message) {
        System.out.println(message);
        return in.nextInt();
    }


    @Override
    public String requestString(String message) {
        System.out.println(message);
        return in.next();
    }


    /*
    @Override
    public PcColourEnum askPcColourEnum() {
        String stringed;
        PcColourEnum choice = null;
       do{
            System.out.println("Select your colour. Existing colours are: " + Arrays.toString(PcColourEnum.values()));
            stringed = in.next().toUpperCase();
            try {
                choice = PcColourEnum.valueOf( stringed );
            }catch ( IllegalArgumentException e ){
                System.out.println("Illegal Argument");
            }
        } while (!Arrays.asList( PcColourEnum.values()).contains( choice ));
        return PcColourEnum.valueOf( stringed );
    }
     */
}
