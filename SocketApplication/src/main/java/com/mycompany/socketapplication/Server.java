/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.socketapplication;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

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
            String output = "";
            
            //Get socket from the client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            
            // Create input and output streams for data transmission
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read data from the client and display it
            String clientUrl = in.readLine();
            System.out.println("Received from client: " + clientUrl);
            //If client sends blank, set default url
            if(clientUrl.equals("")){
                clientUrl = "http://nginx.org/";
            }
            
            try{
                HttpClient client = HttpClient.newBuilder().build();
                //Connect with url
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(clientUrl))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .build();
                HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());
                HttpHeaders headers = response.headers();
                
                System.out.println(headers);
                
                //Get Status code (200 -> Success, 400 -> Error in request, 500 -> Server error
                int status = response.statusCode();
                System.out.println(headers);
                System.out.println(response.statusCode());
                
                if(status == 200){
                    output = "Connection to " + clientUrl + " is successful";
                }else{
                    output = "Server can't connect to url";
                }
                
            }catch(InterruptedException e){
                System.out.println("Error: " + e);
            }catch(IllegalArgumentException iae){
                System.out.println("Error in URL: " + iae);
                output = "Syntax error in URL. Please review your URL and send again";
            }catch(ConnectException ce){
                System.out.println("Error in URL: " + ce);
                output = "The server can't find that specific URL. Try again";
            }
            
            // Send a response back to the client
            out.println(output);

            //Closes out buffer and writer
            in.close();
            out.close();
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
        
}
