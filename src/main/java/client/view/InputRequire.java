package client.view;

/**
 * this interface must be used to require input to the user
 */
public interface InputRequire {

    int requestInt(String message);

    String requestString(String message);

    //PcColourEnum askPcColourEnum();
}
