package com.mycompany.cafe_management_app.util;

import com.mysql.cj.xdevapi.Client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class ClientUtil {
    public static ClientUtil instance;
    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    private ClientUtil() throws IOException {
        this.socket = new Socket("localhost", 8080);
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Client: Connection established!");
    }

    public static ClientUtil getInstance() {
        try {
            if (instance == null) {
                instance = new ClientUtil();
            }

            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CompletableFuture<String> sendRequestAsync(String request) {
        try {
            out.write(request);
            out.newLine();
            out.flush();

            System.out.println("Client: Sent request: " + request);
        } catch (Exception e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
            return CompletableFuture.failedFuture(e);
        }

        return CompletableFuture.supplyAsync(() -> {
            String response;
            while (socket != null && socket.isConnected()) {
                try {
                    response = in.readLine();  //block
                    if (response != null) {
                        return response;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    closeEverything(socket, in, out);
                    return null;
                }
            }

            return null;
        });
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
