package client.view.cli;
import client.controller.ClientController;
import client.controller.RMIController;
import client.controller.SocketController;
import client.view.InputRequire;
import client.view.RemoteView;
import java.util.Scanner;
import java.util.UUID;

public class CliView implements RemoteView, InputRequire {

    private static final String NOPE = "Invalid Command";

    private ClientController controller;
    private Scanner inputFromKeyBoard;

    public CliView(){
        this.inputFromKeyBoard = new Scanner(System.in);
    }

    /**
     * @param message the text to show to the user
     * @return an integer inserted by the user
     */
    @Override
    public int requestInt(String message) {
        System.out.println(message);
        return inputFromKeyBoard.nextInt();
    }


    @Override
    public String requestString(String message) {
        System.out.println(message);
        return inputFromKeyBoard.next();
    }


    public void getConnection() {
        String cmd;
        do {
            cmd = requestString("(s)ocket \n (r)mi");
            if (!cmd.equals("s") && !cmd.equals("r"))
                System.out.println(NOPE);
        } while (cmd.equals("s") || cmd.equals("r"));

        if (cmd.equals("s"))
            controller = new SocketController(this);
        else
            controller = new RMIController(this);
    }


    public void login_register() {
        UUID token = null;
        System.out.println("Do you have a login token?");
        do {
            switch (inputFromKeyBoard.next().toLowerCase()) {
                case "y":
                case "yes":
                    System.out.println("Insert your token");
                    token = UUID.fromString(inputFromKeyBoard.next());
                    break;
                case "n":
                case "no":
                case "nope":
                    String username;
                    do {
                        System.out.println("Insert an username");
                        username = inputFromKeyBoard.next();
                        System.out.println(username + System.lineSeparator() + "Are you sure? Retype it to confirm.");
                    } while (!inputFromKeyBoard.next().equals(username));
                    token = controller.registerWith(username);
                    break;
                default:
                    System.out.println(NOPE);
            }
        } while (token == null);
        controller.login(token);
    }


    public void listening(){
        //controller.startMessaging();
        String message;
        do {
            message = inputFromKeyBoard.next();
            controller.sendMessage(message);
        } while (!message.startsWith(":q"));
    }
}
