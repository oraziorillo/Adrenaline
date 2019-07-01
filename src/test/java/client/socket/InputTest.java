package client.socket;

import common.enums.ControllerMethodsEnum;

import java.util.Scanner;

public class InputTest {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("\n>>> command");
        String[] input = in.nextLine().split("\\s+");
        for (String s : input)
            System.out.println("input:" + s);
        ControllerMethodsEnum command = ControllerMethodsEnum.parseString(input[0]);
        System.out.println("command " + input[0]);
        String[] arguments = new String[input.length - 1];
        System.arraycopy(input, 1, arguments, 0, input.length - 1);
        for (String s : arguments)
            System.out.println("arguments: " + s);
    }
}
