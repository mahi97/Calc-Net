package ir.aut.dcalc.client;

import ir.aut.dcalc.core.Operation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Rahimi on 12/22/18.
 */


public class Client {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    public Client(int portNumber) {
        try {
            socket = new Socket("localhost", portNumber);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void sendRequest(Operation op, Double arg1, Double arg2) {
        if (socket != null && in != null && out != null) {
            try {
                out.writeBytes("$" + op.toString() + "$" + arg1.toString() + "$" + arg2.toString() + "$\n");
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    String[] str = responseLine.split("\\$");
                    if (str.length >= 3) {
                        System.out.println("Time: " + str[1] + " ANS: " + str[2]);
                    } else {
                        System.out.println("Something Went Wrong");
                    }
                }
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}