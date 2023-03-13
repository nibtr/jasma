package com.mycompany.payment_system;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            closeEverything(socket, in, out);
        }
    }

    @Override
    public void run() {

        while (socket.isConnected()) {
            try {
                String request = in.readLine();
                System.out.println("Request from client: " + request);
                out.write("Hello from server");
                out.newLine();
                out.flush();
            } catch (IOException e) {
                closeEverything(socket, in, out);
                break;
            }
        }
    }

    private void closeEverything(Socket socket, BufferedReader in, BufferedWriter out) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
