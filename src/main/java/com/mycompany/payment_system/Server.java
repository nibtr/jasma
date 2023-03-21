
package com.mycompany.payment_system;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class Server {
//    private ServerSocket serverSocket;

    private KeyManagerFactory kmf;
    private KeyStore ks;
    private KeyStore trustStore;
    private char[] password = "password".toCharArray();
    private FileInputStream fis;
    private FileInputStream fis2;
    private TrustManagerFactory tmf;
    private SSLContext sslContext;
    private SSLServerSocketFactory ssf;
    private SSLServerSocket serverSocket;

    public Server(InetAddress ip, int port ) throws
            KeyStoreException,
            IOException,
            NoSuchAlgorithmException,
            CertificateException,
            UnrecoverableKeyException,
            KeyManagementException {
//        this.serverSocket = serverSocket;

        // Set up key manager to authenticate the server
        this.kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        this.ks = KeyStore.getInstance("JKS");
        this.fis = new FileInputStream("src/main/java/com/mycompany/ssl/server_keystore.jks");
        ks.load(fis, password);
        kmf.init(ks, password);

        tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustStore = KeyStore.getInstance("JKS");
        fis2 = new FileInputStream("src/main/java/com/mycompany/ssl/client_truststore.jks");
        trustStore.load(fis2, password);
        tmf.init(trustStore);

        // Set up SSL context
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        // Create SSL server socket
        ssf = sslContext.getServerSocketFactory();
        this.serverSocket = (SSLServerSocket) ssf.createServerSocket(port, 50, ip);
//        this.serverSocket.setNeedClientAuth(true);
    }


    public void start() {
        System.out.println("Server is running on " + serverSocket.getInetAddress() +
                " at port " +
                serverSocket.getLocalPort() + "...");
        while (!serverSocket.isClosed()) {
            try {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept(); //block
                System.out.println("Server: A new client " + clientSocket.getInetAddress() + " has connected");

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
        try {
//            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//            while (interfaces.hasMoreElements()) {
//                NetworkInterface iface = interfaces.nextElement();
//                Enumeration<InetAddress> addresses = iface.getInetAddresses();
//                while (addresses.hasMoreElements()) {
//                    InetAddress addr = addresses.nextElement();
//                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && addr.getHostAddress().contains(".")) {
//                        InetAddress ip = InetAddress.getByName(addr.getHostAddress());
//                        int port = 8080;
//                        Server server = new Server(ip, port);
//                        server.start();
//                    }
//                }
//            }
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            int port = 8080;
            Server server = new Server(ip, port);
            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
