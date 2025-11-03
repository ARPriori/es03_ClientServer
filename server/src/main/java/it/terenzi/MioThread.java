package it.terenzi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MioThread extends Thread{
    
    Socket socket;

    public MioThread(Socket s){
        socket =s;

    }

    public void run() {
    try{
        System.out.println("connesso");
        BufferedReader in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out =new PrintWriter(socket.getOutputStream(), true);

        out.println("welcome | v1.0.0");
        int op;
        boolean e =false;
        String msg ="";
    do{
        op = in.read();
        if(op <0 || op>4){
            e =true;
            msg = "KO: incorrect_ operator";
        }
        int n1 = in.read();
        int n2 = in.read();
        
        if(n2==0){
            e =true;
            msg = "KO: div_by_zero";
        }
        int a=0;
        switch (op) {
            
            case 1:
                a = n1 + n2;
                
                break;
            case 2:
                a = n1 - n2;
                break;
            case 3:
                a = n1 * n2;
                break;
            case 4:
                a = n1 / n2;
                break;
        }
        out.println((e)? msg : "OK: " + a);
    }while(op!=0);
    }catch(Exception e){}
    }
}
