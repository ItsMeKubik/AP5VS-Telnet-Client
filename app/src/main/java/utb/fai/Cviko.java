package utb.fai;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cviko {
    public static void main(String[] args){
        try (ServerSocket serverSocket = new ServerSocket(9999)) {

            System.out.println("Server běží na portu: " + serverSocket.getLocalPort());

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Připojil se nový klSient: " + socket.getInetAddress());
                
                ClientHandler handler = new ClientHandler(socket);

                handler.start();

            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}