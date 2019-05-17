package view.cli;

import model.enumerations.PcColourEnum;
import view.InputRequier;

import java.io.InputStream;
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
        String stringed = null;
        while (!PcColourEnum.stringCollection().contains( stringed )){
            System.out.println("Select your colour. Existing colours are: "+PcColourEnum.values());
            stringed = in.next();
        }
        return PcColourEnum.fromString(stringed);
    }

    @Override
    public String askString() {
        System.out.println("Insert a line of text");
        return in.next();
    }
}
