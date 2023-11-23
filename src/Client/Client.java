package Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import Utilities.Category;
import Utilities.Question;
import Utilities.Answers;

public class Client {
    int port = 12344;
    String ip = "127.0.0.1";
    Object obj;
    static final String WELCOME = "START_GAME_FROM_CLIENT_XXX";
    static final String END_GAME = "END_GAME_FROM_CLIENT_XXX";

    public Client() {
        try (Socket socketToServer = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream())) {

            GameGraphics g = new GameGraphics();

            while ((obj = in.readObject()) != null) {
                //System.out.println(obj);
                if (obj.equals(END_GAME)) {
                    System.exit(0);
                } else if (obj instanceof Question q) {
                    g.addQuestions(q);
                    if(g.getQuestions().size() == 3) {
                        g.questions(g.getQuestions(), out);
                    }
                } else if (obj instanceof Category c) {
                    System.out.println("fick categorierna!");
                    g.categoryChoice(c, (Category) in.readObject(), (Category) in.readObject(), out);
                } else if (obj.equals(WELCOME)) {
                    out.writeObject(JOptionPane.showInputDialog(null, "What is your name?"));
                } else if (obj instanceof Integer s) {
                    g.addPointsToOpponent(s);
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