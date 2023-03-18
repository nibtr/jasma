package com.mycompany.payment_system;

import com.mycompany.cafe_management_app.model.Bill;
import org.json.simple.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            closeEverything(clientSocket, in, out);
        }
    }

    @Override
    public void run() {
        while (clientSocket.isConnected()) {
            try {
                String request = in.readLine(); // block
                if (request == null) {
                    continue;
                }
                System.out.println("Server: Request received: " + request);
                handleRequest(request);

                // out.write("Server: Connection established!");
                // out.newLine();
                // out.flush();
            } catch (IOException e) {
                closeEverything(clientSocket, in, out);
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

    private void handleRequest(String request) throws IOException {
        try {
            String cmd = (request == null) ? "UNKNOWN" : JSONObjUtil.getHeader(request);
            System.out.println("Server: Command received: " + cmd);

            switch (cmd) {
                case "END":
                    System.out.println("Server: Client " + clientSocket.getInetAddress() + " has disconnected");
                    closeEverything(clientSocket, in, out);
                    break;

                case "TRANSACTION":
                    System.out.println("Server: Transaction request received");

                    // delay for 3s
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // TODO: Implement transaction logic here
                    String res = handleTransaction(JSONObjUtil.getBody(request)) ? "SUCCESS" : "FAILED";

                    // out.write("Server: Transaction made successfully!");
                    // out.newLine();
                    // out.flush();
                    // break;

                    out.write(JSONObjUtil.toJson(res, "RESPONSE"));
                    out.newLine();
                    out.flush();

                    System.out.println("Server: Transaction response sent");
                    break;

                default:
                    System.out.println("Server: Unknown command!");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean handleTransaction(Object x) throws NoSuchFieldException {
        System.out.println("Body: " + x);
        JSONObject bill = (JSONObject) x;
        String cardNumber = bill.get("card_number").toString();
        Double billTotalPrice = Double.parseDouble(bill.get("total").toString());

        if (cardNumber.length() != 16 ||
        // cardNumber.matches("[0-9]+") ||
                cardNumber.charAt(0) == '0') {
            return false;
        }

        return true;
    }

}
