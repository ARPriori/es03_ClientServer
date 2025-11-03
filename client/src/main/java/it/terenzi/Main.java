package it.terenzi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("Hello world!");
        Scanner scanner =new Scanner(System.in);
        String ip="";
        int port =0;
        System.out.println(" ip server : ");
        ip = scanner.nextLine();
        System.out.println("porta : ");
        port =Integer.parseInt(scanner.nextLine());
        Socket s =new Socket(ip ,port);
        

        BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out =new PrintWriter(s.getOutputStream(), true);

        String e = "";
        String op = "";
        do{
            System.out.println("\nOPERAZIONI\n1.\tsomma\n2.\tsottrazione\n3.\tmoltiplicazione\n4.\tdivisione");
            System.out.println("\ninserire operando: ");
            op = scanner.nextLine();
            out.println(op);
            e =scanner.nextLine();
            out.println(e);
            e =in.readLine();
            System.out.println("stringa trasformata : " + e);
        }while(e.equals("EXIT"));
        scanner.close();
    }
}