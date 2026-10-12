package utb.fai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class anotherOne {

    private Socket socket;

    public anotherOne(){
        try{
            socket = new Socket("localhost",9999);

            System.out.println("Klient se úspěšně připojil k serveru!");

            Thread readerThread = new Thread(() -> readFromServer());
            Thread writerThread = new Thread(() -> writeToServer());


            readerThread.start();
            writerThread.start();

            try {
                writerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Klient byl ukončen.");

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void readFromServer(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToServer(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);


            String line;
            while ((line = in.readLine()) != null) {
                // if začíná exitem
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
        }
    }

    public static void main(String[] args){
        anotherOne client = new anotherOne();
    }
}
