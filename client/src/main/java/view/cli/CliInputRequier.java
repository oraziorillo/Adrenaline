package view.cli;

import enums.PcColourEnum;
import view.InputRequier;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * requires some input to the user, via cli/terminal or other inputstreamreader
 */
public class CliInputRequier implements InputRequier {
    private final Scanner in;

    public CliInputRequier(InputStream in) {
        this.in = new Scanner( in );
    }

    public CliInputRequier(){
        this( System.in );
    }

    /**
     *
     * @param message the text to show to the user
     * @return an integer inserted by the user
     */
    @Override
    public int askInt(String message) {
        System.out.println(message);
        return in.nextInt();
    }

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

    @Override
    public String askString() {
        System.out.println("Insert a line of text");
        return in.next();
    }
}
