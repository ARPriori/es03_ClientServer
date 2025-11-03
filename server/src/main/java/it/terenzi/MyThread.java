package it.terenzi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyThread extends Thread {
    Socket socket;

    public MyThread(Socket s) {
        socket = s;
    }

    public void run() {
        try {

            System.out.println("Un client si è connesso");

            // creo un modo per ascoltare e parlare
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            int operator = 0;

            do {
                boolean error = false;
                String errorMsg = "";

                String operatorString = in.readLine();

                if (operatorString == null) break;
                try {
                    operator = Integer.parseInt(operatorString.trim());
                } catch (NumberFormatException e) {
                    error = true;
                    errorMsg = "KO: INVALID_OPERATOR";
                    out.println(errorMsg); 
                }

                if(operator == 0) break;

                String n1String = in.readLine();
                String n2String = in.readLine();
                float n1 = 0;
                float n2 = 0;

                try {
                    n1 = Float.parseFloat(n1String.trim());
                    n2 = Float.parseFloat(n2String.trim());
                } catch (NumberFormatException e) {
                    error = true;
                    errorMsg = "KO: INVALID_NUMBER";
                    out.println(errorMsg);
                    continue;
                }

                float ris = 0;
                switch(operator){
                    case 1:
                        ris = n1 + n2;
                        break;
                    case 2:
                        ris = n1 - n2;
                        break;
                    case 3:
                        ris = n1 * n2;
                        break;
                    case 4:
                        if(n2 == 0){
                            error = true;
                            errorMsg = "KO: DIVISION_BY_ZERO";
                            out.println(errorMsg);
                        }else{
                            ris = n1 / n2;
                        }
                        break;
                    default:
                        error = true;
                        errorMsg = "KO: NULL_OPERATOR";
                        out.println(errorMsg);
                        continue;
                }

                if(operator != 0 && !error){
                    out.println("OK: " + ris);
                }
                
            } while (operator != 0);

            socket.close();
        } catch (Exception e) {}

    }
}
