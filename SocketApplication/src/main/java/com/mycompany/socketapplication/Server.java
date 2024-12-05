/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.socketapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Daniel García
 * 05/12/2024
 */
public class Server {
    
    private static ServerSocket serverSocket;
    private static final int PORT = 8080;
    
    public static void main(String[] args) {
        System.out.println("Server listening at port " + PORT);
        try 
        {
            serverSocket = new ServerSocket(PORT);
        }
        catch(IOException e) 
        {
             System.out.println("Unable to attach to port" + PORT);
             System.exit(1);
        }
        do 
        {
             run();
        }while (true);
    }
    
    private static void run(){
        try{
            //Get socket from the client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            
            // Create input and output streams for data transmission
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read data from the client and display it
            String clientData = in.readLine();
            System.out.println("Received from client: " + clientData);
            
            // Send a response back to the client
            out.println("Hello from the server!");

            //Closes out buffer and writer
            in.close();
            out.close();
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
        
}
