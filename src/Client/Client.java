package Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client {
    int port = 12344;
    String ip = "127.0.0.1";
    List<Integer> points;
    Object obj;
    static final String WELCOME = "STARTGAMEFROMCLIENTXXX";
    static final String END_GAME = "ENDGAMEFROMCLIENTXXX";
    Answers playerAnswer;

    public Client() {
        try (Socket socketToServer = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream())) {

            GameGraphics g = new GameGraphics();

            while (true) {
                obj = in.readObject();
                if (obj.equals(END_GAME)) {
                    System.exit(0);
                } else if (obj instanceof Question q) {
                    g.questions(q, out);
                } else if (obj instanceof Category c) {
                    System.out.println("fick categorierna!");
                    g.categoryChoice(c, (Category) in.readObject(), (Category) in.readObject(), out);
                } else if (obj.equals(WELCOME)) {
                    out.writeObject(JOptionPane.showInputDialog(null, "What is your name?"));
                } else if (obj instanceof Integer s) {
                    System.out.println(s);
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