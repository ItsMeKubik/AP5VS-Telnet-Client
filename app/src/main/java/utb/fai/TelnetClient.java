package utb.fai;

import java.io.*;
import java.net.*;

public class TelnetClient {

    private String serverIp;
    private int port;
    private Socket socket;

    public TelnetClient(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public void run() {
        try {
            socket = new Socket(serverIp, port);
            Thread readerThread = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {}
            });

            Thread writerThread = new Thread(() -> {
                try {
                    BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    
                    String line;
                    while ((line = consoleIn.readLine()) != null) {
                        out.println(line);
                        
                        if ("/QUIT".equalsIgnoreCase(line.trim())) {
                            break; 
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readerThread.start();
            writerThread.start();

            writerThread.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


