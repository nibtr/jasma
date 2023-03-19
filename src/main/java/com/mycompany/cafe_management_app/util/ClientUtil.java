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

public class ClientUtil {
    public static ClientUtil instance;
    private SSLSocket socket;

    private TrustManagerFactory tmf;
    private KeyStore ks;
    private char[] password = "password".toCharArray();
    private FileInputStream fis;
    private SSLContext sslContext;




    private BufferedWriter out;
    private BufferedReader in;

    private ClientUtil() throws IOException {
        try {
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            ks = KeyStore.getInstance("JKS");
            fis = new FileInputStream("src/main/java/com/mycompany/ssl/client_truststore.jks");
            ks.load(fis, password);
            tmf.init(ks);
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            this.socket = (SSLSocket) sslContext.getSocketFactory().createSocket("localhost", 8080);
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
