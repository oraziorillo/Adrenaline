package view;

import model.enumerations.PcColourEnum;

/**
 * this interface must be used to require input to the user
 */
public interface InputRequier {
    public int askInt(String message);

    PcColourEnum askPcColourEnum();

    String askString();
}
