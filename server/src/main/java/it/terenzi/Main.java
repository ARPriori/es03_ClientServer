package it.terenzi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket mySS = new ServerSocket(3000);

        do {
            Socket mySocket = mySS.accept(); 
            MyThread t = new MyThread(mySocket);
            t.start();
        } while (true);

    }
}