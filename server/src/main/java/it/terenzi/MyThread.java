package it.terenzi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class MyThread extends Thread {
    Socket p1, p2;

    public MyThread(Socket player1, Socket player2) {
        p1 = player1;
        p2 = player2;
    }

    public void run() {

        ArrayList<Integer> board = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));

        try {

            System.out.println("Una partita iniziata");

            BufferedReader in1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            PrintWriter out1 = new PrintWriter(p1.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            PrintWriter out2 = new PrintWriter(p2.getOutputStream(), true);

            boolean ended = false;
            int movesCount = 0;

            out1.println("YOUR_TURN");
            out2.println("WAIT_TURN");

            do {
                boolean wrongInput = false;

                // Turno p1
                do {
                    String cell1 = null;
                    try {
                        cell1 = in1.readLine(); 
                        if (cell1 == null) throw new IOException(); //disconnessione
                        int c1 = Integer.parseInt(cell1);

                        if (board.get(c1) != 0 || c1 >= board.size() || c1 < 0) {
                            out1.println("KO");
                            wrongInput = true;
                        } else {
                            wrongInput = false;
                            movesCount++;
                            board.set(c1, 1);

                            if (checkWin(board, 1)) {
                                out1.println("W");
                                out2.println(generateString(board, "L"));
                                ended = true;
                            } else if (movesCount == 9) {
                                out1.println("P");
                                out2.println(generateString(board, "P"));
                                ended = true;
                            } else {
                                out1.println("OK");
                                out2.println(generateString(board, ""));

                                out1.println("WAIT_TURN");
                                out2.println("YOUR_TURN");
                            }
                        }
                    } catch (IOException e) {
                        out2.println("DISCONNECTED");
                        System.out.println("Player 1 disconnesso.");
                        p1.close();
                        p2.close();
                        return;
                    }
                } while (wrongInput && !ended);

                // Turno p2
                do {
                    String cell2 = null;
                    try {
                        cell2 = in2.readLine();
                        if (cell2 == null) throw new IOException(); // disconnessione
                        int c2 = Integer.parseInt(cell2);

                        if (board.get(c2) != 0 || c2 >= board.size() || c2 < 0) {
                            out2.println("KO");
                            wrongInput = true;
                        } else {
                            wrongInput = false;
                            movesCount++;
                            board.set(c2, 2);

                            if (checkWin(board, 2)) {
                                out2.println("W");
                                out1.println(generateString(board, "L"));
                                ended = true;
                            } else if (movesCount == 9) {
                                out2.println("P");
                                out1.println(generateString(board, "P"));
                                ended = true;
                            } else {
                                out2.println("OK");
                                out1.println(generateString(board, ""));

                                out2.println("WAIT_TURN");
                                out1.println("YOUR_TURN");
                            }
                        }
                    } catch (IOException e) {
                        out1.println("DISCONNECTED");
                        System.out.println("Player 2 disconnesso.");
                        p1.close();
                        p2.close();
                        return; 
                    }
                } while (wrongInput && !ended);

            } while (!ended);

            p1.close();
            p2.close();
        } catch (Exception e) {
        }

    }

    private boolean checkWin(ArrayList<Integer> board, int player) {

        // Controlla le righe
        for (int i = 0; i < 9; i += 3) {
            if (board.get(i) == player && board.get(i + 1) == player && board.get(i + 2) == player) {
                return true;
            }
        }

        // Controlla le colonne
        for (int i = 0; i < 3; i++) {
            if (board.get(i) == player && board.get(i + 3) == player && board.get(i + 6) == player) {
                return true;
            }
        }

        // Controlla le diagonali
        if (board.get(0) == player && board.get(4) == player && board.get(8) == player) {
            return true;
        }
        if (board.get(2) == player && board.get(4) == player && board.get(6) == player) {
            return true;
        }

        return false;
    }

    private String generateString(ArrayList<Integer> board, String status) {
        String boardString = String.join(",", board.stream().map(String::valueOf).toArray(String[]::new));
        return boardString + "," + status;
    }
}
