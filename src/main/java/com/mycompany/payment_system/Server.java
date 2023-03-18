
package com.mycompany.payment_system;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() {
        System.out.println("Server is running on port " + serverSocket.getLocalPort() + "...");
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept(); //block
                System.out.println("Server: Client " + clientSocket.getInetAddress() + " has connected");

//                Give the client socket to a new thread
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(new ServerSocket(8080));
        server.start();
    }
}
