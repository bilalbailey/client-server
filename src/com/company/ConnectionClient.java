package com.company;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class ConnectionClient {
    private static String localHost = "127.0.0.1";
    private static int port = 4000;
    private static ExecutorService service = Executors.newFixedThreadPool(5);
    private static AtomicInteger count = new AtomicInteger(0);
    private static BlockingQueue<Integer> blockQueue;
    private static Pattern pattern = Pattern.compile("\\b\\w{1,9}\\b");
    private static Socket client;

    public static void main(String[] args) throws IOException {
        startUp();
    }

    private static void startUp() throws IOException {
        File file = new File("numbers.log");
        if (file.exists()) {
            file.delete();
        }

        ManageQueue queue = new ManageQueue(blockQueue);
        new Thread(queue).start();
        while (true) {
            service.execute(clientSocketHandler());
        }
    }

    private static Runnable clientSocketHandler() {
        return () -> {
            try {
                client = new Socket(localHost, port);
                int howManyClients = count.incrementAndGet();

                System.out.println("client " + howManyClients + " connected to port " + client.getPort());

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

                DataOutputStream out = new DataOutputStream(new FileOutputStream("numbers.log"));

                do {

                    int line = in.read();
                    int lineTest = stdIn.read();

                    String inputBeingReadInFromServer = String.valueOf(line);
                    boolean check = pattern.matcher(inputBeingReadInFromServer).matches();
                    //testing if anything would write to file
                    out.writeBytes(String.format("%09d", lineTest));

                    if (!check) {
                        terminate();
                    } else if (inputBeingReadInFromServer.equals("terminate")) {
                        terminate();
                    }

                    out.writeBytes(String.format("%09d", line));

                } while (in.read() != -1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private static void terminate() throws IOException {
        System.out.println("Client is Shutting down now.");
        client.close();
        service.shutdownNow();
    }
}
