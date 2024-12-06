/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.socketapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Daniel García
 * 05/12/2024
 */
public class Client {
    public static void main(String[] args) {
        try {
            // Create a client socket and connect to the server
            Socket clientSocket = new Socket("localhost", 8080);
            
            // Create input and output streams for data transmission
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader bfReader =new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("Please introduce an URL");
            String url = bfReader.readLine();
            
            // Send url to the server
            outputWriter.println(url);
            
            // Read the response from the server
            String serverResponse = inputReader.readLine();
            System.out.println("Received from server: " + serverResponse);
            
            // Close the client socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
