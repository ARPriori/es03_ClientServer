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

        Boolean game =true;
        String cell = "";
        String response = in.readLine();
        if( response == "WAIT"){

            System.out.println("waiting for a player");
            in.readLine();
        }
            System.out.println("ready to play"); 
        
        do {
            String res ;
            do{
                System.out.println("\ncaselle\n0. in alto a sinistra\n1. \tin alto al centro\n2. in alto a destra\n3. \tcentro sinistra\n4.\tcentro\n" + //
                 "5.\tcentro destra\n6.\tbasso sinistra\n7.\tbasso centro\n8.basso destra");
                System.out.println("\nInserire casella: ");
                cell = scanner.nextLine();
                out.println(cell);//invio cella al server
                res = in.readLine();//attesa risposta OK,KO,W,P
            }while(!(res=="KO"));

            if(res == "W"){
                System.out.println("hai vinto"); 
                game =false;
            }else if(res == "P"){
                System.out.println("pareggio"); 
                game = false;
            }else if(res == "OK"){
                System.out.println("mossa valida"); 
            }else{
                String received = in.readLine();
                System.out.println(received); 
                String[] cells = received.split(",");  
                if(cells[9]=="L"){
                    System.out.println("hai perso"); 
                    game = false;
                }else if(cells[9]=="P"){
                    System.out.println("pareggio"); 
                    game = false;
                }
            }
        } while (game);        

         // if (cell.equals("0")) {
            //     out.println(cell); 
            //     System.out.println("Connessione interrotta.");
            //     break; 
            // }

        scanner.close();
        s.close();
    }
}