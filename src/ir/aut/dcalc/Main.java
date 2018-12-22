package ir.aut.dcalc;

/**
 * Created by Rahimi on 12/22/18.
 */

import ir.aut.dcalc.client.Client;
import ir.aut.dcalc.core.Operation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
            Client client = new Client(1234);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Input Operator:");
            try {
                Operation op;
                op = Operation.valueOf(scanner.next());
                System.out.println("Now Input arg1 and arg2");
                Double arg1 = scanner.nextDouble();
                Double arg2;
                try {
                    arg2 = scanner.nextDouble();
                } catch (InputMismatchException e) {
                    arg2 = 0.0;
                }
                client.sendRequest(op, arg1, arg2);
            } catch (InputMismatchException e) {
                System.out.println("Wrong Input");
            }
    }
}
