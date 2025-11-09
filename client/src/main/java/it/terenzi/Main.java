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
        if(response.equals("WAIT")){
            System.out.println("In attesa di un altro giocatore...");
            in.readLine();
        }
        System.out.println("Pronto a giocare!"); 
        
        do {
            String res = in.readLine();
            if(res == null) break;

            if(res.equals("YOUR_TURN")){
                System.out.println("\ncaselle\n0. in alto a sinistra\n1. \tin alto al centro\n2. in alto a destra\n3. \tcentro sinistra\n4.\tcentro\n" + //
                 "5.\tcentro destra\n6.\tbasso sinistra\n7.\tbasso centro\n8.basso destra");
            
            do{
                System.out.println("\nInserire casella: ");
                cell = scanner.nextLine();
                out.println(cell);
                res = in.readLine();

                if (res == null || res.equals("DISCONNECTED")) {
                    System.out.println("L'altro giocatore si è disconnesso.");
                    game = false;
                    break;
                }

            }while(res.equals("KO"));

            if (res.equals("W")) {
                System.out.println("Hai vinto!");
                game = false;
            } else if (res.equals("P")) {
                System.out.println("Pareggio!");
                game = false;
            } else if (res.equals("OK")) {
                System.out.println(res);
            } else {
                String[] cells = res.split(",");
                if (cells.length >= 9) {
                    System.out.println("\nTabellone:");
                    for (int i = 0; i < 9; i++) {
                        String symbol = switch (cells[i]) {
                            case "1" -> "X";
                            case "2" -> "O";
                            default -> " ";
                        };
                        System.out.print(symbol + ((i % 3 == 2) ? "\n" : " | "));
                    }
                }
                if (cells.length > 9) {
                    if (cells[9].equals("L")) {
                        System.out.println("Hai perso!");
                        game = false;
                    } else if (cells[9].equals("P")) {
                        System.out.println("Pareggio!");
                        game = false;
                    }
                }
            }
            }else if(res.equals("WAIT_TURN")){
                System.out.println("Attendi il turno dell'altro giocatore...");
            }
            
        } while (game);        

        scanner.close();
        s.close();
    }
}