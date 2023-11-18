package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    int port = 12344;
    String ip = "127.0.0.1";
    PrintWriter out;
    BufferedReader in;
    String textFromServer;
    int numberOfAlternatives = 4;

    public Client() {
        GameGraphics g = new GameGraphics();
        try(Socket socketToServer = new Socket(ip, port)) {
            out = new PrintWriter(socketToServer.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            while(true){
                textFromServer = in.readLine();
                if(textFromServer.equals("You win!")){
                    System.out.println(textFromServer);
                    System.exit(0);
                }
                else {
                    System.out.println(textFromServer);
                    for (int i = 0; i < numberOfAlternatives; i++) {
                        System.out.println(in.readLine());
                    }
                    out.println(userInput.readLine());
                }
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}