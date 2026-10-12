package utb.fai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    
    @Override 
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);


            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Klient (" + socket.getInetAddress() + ") napsal: " + line);
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Klient (" + this.socket.getInetAddress() + ") byl odpojen!");
        }

    }
    
}
