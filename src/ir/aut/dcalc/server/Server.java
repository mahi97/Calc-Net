package ir.aut.dcalc.server;

import ir.aut.dcalc.core.Operation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by Rahimi on 12/22/18.
 */


public class Server {

    private double calc(Operation op, double arg1, double arg2) {
        switch (op) {
            case Add:
                return arg1 + arg2;
            case Subtract:
                return arg1 - arg2;
            case Divide:
                return arg1 / arg2;
            case Multiply:
                return arg1 * arg2;
            case Sin:
                return Math.sin(arg1);
            case Cos:
                return Math.cos(arg1);
            case Tan:
                return Math.tan(arg1);
            case Cot:
                return 1 / Math.tan(arg1);
            default:
                return -1;
        }
    }

    public Server(int port) {

        try {
            ServerSocket socket = new ServerSocket(port);

            while (true) {
                Socket csocket = socket.accept();
                DataInputStream in = new DataInputStream(csocket.getInputStream());
                DataOutputStream out = new DataOutputStream(csocket.getOutputStream());
                String request;
                request = in.readLine();
                if (request == null || request.isEmpty()) {
                    in.close();
                    out.close();
                    continue;
                }
                System.out.println("Client: " + request);
                if (request.equals("#END")) {
                    out.close();
                    in.close();
                    break;
                }

                String[] str = request.split("\\$");
                if (str.length >= 3) {
                    Operation op = Operation.valueOf(str[1]);
                    Double arg1 = Double.valueOf(str[2]);
                    Double arg2 = Double.valueOf(str[3]);
                    long start = System.nanoTime();
                    Double res = calc(op, arg1, arg2);
                    Long t = (System.nanoTime() - start);
                    Double time = t / 1000000.0;
                    out.writeBytes("$" + time.toString() + "ms$" + res.toString() + "$\n");
                    System.out.println("$" + time.toString() + "ms$" + res.toString() + "$");
                }
                in.close();
                out.close();
            }
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
