package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionServer {
    private static int port = 4000;
    private static String inFromTheSocket;

    public static void main(String[] args) {
        serverSocketHandler();
    }

    private static void serverSocketHandler() {
        System.out.println("Starting ConnectionServer....");
        try (ServerSocket server = new ServerSocket(port, 100000);
        ) {
            System.out.println("Server is listening on port " + port);

            Socket socket = server.accept();
            System.out.println("client connected");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int number = (int) (Math.random() * 9);

            while (true) {
                BufferedOutputStream out = new BufferedOutputStream(new DataOutputStream(socket.getOutputStream()));
                out.write(number);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
