package it.terenzi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {

        Scanner scanner = new Scanner(System.in);
        String ip = "";
        int port = 0;

        System.out.println("Inserire indirizzo IP del server: ");
        ip = scanner.nextLine();
        System.out.println("Inserire porta del server: ");
        port = Integer.parseInt(scanner.nextLine());
        Socket s = new Socket(ip,port);
        System.out.println("connesso");

        //creo un modo per ascoltare e parlare
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        String operator = "";
        do {
            System.out.println("\nOPERAZIONI:\n0. interrompi connessione\n1. somma\n2. sottrazione\n3. moltiplicazione\n4. divisione");
            System.out.println("Inserire operazione: ");
            operator = scanner.nextLine();
            out.println(operator);

            if (operator.equals("0")) {
                out.println(operator); 
                System.out.println("Connessione interrotta.");
                break; 
            }

            

            String response = in.readLine();
            System.out.println(response);    
        } while (!operator.equals("0"));        

        scanner.close();
        s.close();
    }
}