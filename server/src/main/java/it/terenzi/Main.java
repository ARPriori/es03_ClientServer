package it.terenzi;



import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("Hello world!");
        ServerSocket ss = new ServerSocket(3000);

        do {
            Socket mySocket = ss.accept();//istruzione bloccante fino a un collegamento alla port
            MioThread t = new MioThread(mySocket);
        } while (true);





        }
}