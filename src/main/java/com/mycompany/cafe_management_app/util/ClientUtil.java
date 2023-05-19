package com.mycompany.cafe_management_app.util;

import com.mysql.cj.xdevapi.Client;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ClientUtil {
    public static ClientUtil instance;
    private final SSLSocket socket;

    private final TrustManagerFactory tmf;
    private final KeyStore ks;
    private char[] password = "password".toCharArray();
    private final FileInputStream fis;
    private final SSLContext sslContext;

    private final BufferedWriter out;
    private final BufferedReader in;
    private final String HOST = "127.0.0.1";
    private final int PORT = 8080;

    private ClientUtil() throws IOException {
        try {
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            ks = KeyStore.getInstance("JKS");
            fis = new FileInputStream("src/main/java/com/mycompany/ssl/client_truststore.jks");
            ks.load(fis, password);
            tmf.init(ks);
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
<<<<<<< Updated upstream

            this.socket = (SSLSocket) sslContext.getSocketFactory().createSocket(HOST, PORT);
=======
            this.socket = (SSLSocket) sslContext.getSocketFactory().createSocket("192.168.1.4", 8080);
>>>>>>> Stashed changes
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client: Connection established!");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public static ClientUtil getInstance() throws IOException {
        if (instance == null) {
            instance = new ClientUtil();
        }

        return instance;
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
