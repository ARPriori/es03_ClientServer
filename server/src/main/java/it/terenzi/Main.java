package it.terenzi;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket mySS = new ServerSocket(3000);


        do {
            Socket sock1 = mySS.accept();

            PrintWriter out1 = new PrintWriter(sock1.getOutputStream(), true);
            out1.println("WAIT"); 

            Socket sock2 = mySS.accept(); 

            PrintWriter out2 = new PrintWriter(sock2.getOutputStream(), true);
            out1.println("READY"); 
            out2.println("READY"); 

            MyThread t = new MyThread(sock1,sock2);
            t.start();
        } while (true);

    }
}