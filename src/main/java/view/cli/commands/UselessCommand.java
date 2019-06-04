package view.cli.commands;

/**
 * This is a do-nothing command, it has been created to avoid NullPointerException
 */
public class UselessCommand extends CliCommand {
    UselessCommand() {
        //Just filling superclass contructor
        super( null, false);
    }

    /**
     * This method does literally nothing, as the whole class
     */
    @Override
    public void execute() {
    }
}
