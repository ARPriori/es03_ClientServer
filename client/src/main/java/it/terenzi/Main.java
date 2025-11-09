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

        // connessione server
        System.out.println("Inserire indirizzo IP del server: ");
        ip = scanner.nextLine();
        System.out.println("Inserire porta del server: ");
        port = Integer.parseInt(scanner.nextLine());
        Socket s = new Socket(ip, port);
        System.out.println("connesso");

        // creo un modo per ascoltare e parlare
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        Boolean ended = false;
        String cell = "";

        String response = in.readLine();
        Boolean firstPlayer = false;
        if (response.equals("WAIT")) {
            firstPlayer = true;
            System.out.println("In attesa di un altro giocatore...");
            response = in.readLine(); // aspetto che il server mi inivii "READY"
        }
        // primo giocatore ha X, secondo giocatore ha O
        System.out.println(
                ((firstPlayer) ? "\nPronto a giocare! - Giocatore 1 (X)" : "Pronto a giocare! - Giocatore 2 (O)"));

        do {
            response = in.readLine();
            if (response == null)
                break;

            if (response.equals("YOUR_TURN")) {
                System.out.println(
                        "\nCaselle:\n0. in alto a sinistra\n1. in alto al centro\n2. in alto a destra\n3. centro sinistra\n4. centro\n5. centro destra\n6. basso sinistra\n7. basso centro\n8. basso destra");

                // gestione inserimento casella
                do {
                    System.out.println("\nInserire casella (0-8): ");
                    cell = scanner.nextLine();
                    out.println(cell);
                    response = in.readLine();

                    if (response == null || response.equals("DISCONNECTED")) {
                        System.out.println("L'altro giocatore si è disconnesso.");
                        ended = true;
                        break;
                    } else if (response.equals("KO")) {
                        System.out.println(response + "- casella inesistente o già occupata!");
                    }

                } while (response.equals("KO"));

                // gestione risposta server
                if (response.equals("W")) {
                    System.out.println("HAI VINTO!");
                    ended = true;
                } else if (response.equals("P")) {
                    System.out.println("PAREGGIO!");
                    ended = true;
                } else if (response.equals("OK")) {
                    System.out.println(response);
                }

            } else if (response.equals("WAIT_TURN")) {

                System.out.println("\nAttendi il turno dell'altro giocatore...");

            } else if (response.contains(",")) {

                String[] cells = response.split(",");
                if (cells.length >= 9) {

                    // stampa del tabellone di gioco
                    System.out.println("\nTabellone:");
                    for (int i = 0; i < 9; i++) {
                        String symbol = switch (cells[i]) {
                            case "1" -> "X";
                            case "2" -> "O";
                            default -> " ";
                        };
                        System.out.print(symbol + ((i % 3 == 2) ? "\n" : " | "));
                    }

                    // gestione possibile messaggio di fine gioco
                    String esito = (cells.length > 9) ? cells[9] : "";
                    if (esito.equals("L")) {
                        System.out.println("HAI PERSO!");
                        ended = true;
                    } else if (esito.equals("P")) {
                        System.out.println("PAREGGIO!");
                        ended = true;
                    }
                }
            } else if (response.equals("DISCONNECTED")) {
                System.out.println("L'altro giocatore si è disconnesso.");
                ended = true;
            }

        } while (!ended);

        scanner.close();
        s.close();
    }
}