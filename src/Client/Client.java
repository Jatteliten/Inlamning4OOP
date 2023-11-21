package Client;

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
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream());
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            GameGraphics g = new GameGraphics();

            while (true) {
                obj = in.readObject();
                if (obj.equals("results")) {

                    System.exit(0);
                } else if (obj instanceof Question s) {
                    System.out.println(s.getQuestonText());
                    for (int i = 0; i < s.getAnswersList().size(); i++) {
                        System.out.println(s.getAnswer(i).getAnswerText());
                    }
                    if (playerAnswer.answerText.equalsIgnoreCase(s.getAnswer(0).getAnswerText())) {
                        points.add(1);
                        out.writeObject(points);
                    }
                    out.writeObject(points);
                } else if (obj instanceof Category c) {
                    g.categoryChoice(c, (Category) in.readObject(), (Category) in.readObject(), out);
                } else if (obj.equals("welcome")) {

                    out.writeObject("playerName");
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