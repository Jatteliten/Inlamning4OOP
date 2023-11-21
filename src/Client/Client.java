package Client;

import Server.Answers;
import Server.Questions;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client {
    int port = 12344;
    String ip = "127.0.0.1";
    PrintWriter out;
    BufferedReader in;
    String textFromServer;
    int numberOfAlternatives = 4;
    Object obj;

    public Client() {
        try (Socket socketToServer = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream());
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                obj = in.readObject();
                if (obj.equals("You win!")) {
                    System.out.println(obj);
                    System.exit(0);
                } else if (obj instanceof Questions s) {
                    System.out.println(s.getQuestonText());
                    for (int i = 0; i < s.getAnswersList().size(); i++) {
                        System.out.println(s.getAnswer(i).getAnswerText());
                    }
                    out.writeObject(userInput.readLine());
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}