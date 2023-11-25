package Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import Utilities.Category;
import Utilities.Question;

public class Client {
    int port = 12344;
    String ip = "127.0.0.1";
    Object obj;
    static final String WELCOME = "START_GAME_FROM_CLIENT_XXX";
    static final String END_GAME = "END_GAME_FROM_SERVER_XXX";
    int numberOfQuestions = 0;
    int numberOfRounds = 0;

    public Client() {
        try (Socket socketToServer = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream())) {

            GameGraphics g = new GameGraphics();

            while (true) {
                try {
                    obj = in.readObject();
                    if (obj == null || obj.equals(END_GAME)) {
                        System.out.println("EOFException fångad eller slutet av spelet. Avslutar loopen.");
                        break;
                    } else if (obj instanceof Question q) {
                        g.addQuestions(q);
                        if (g.getQuestions().size() == numberOfQuestions) {
                            g.displayQuestions(g.getQuestions(), out);
                        }
                    } else if (obj instanceof Category c) {
                        System.out.println("Fick kategorierna!");
                        g.displayCategoryChoice(c, (Category) in.readObject(), (Category) in.readObject(), out);
                    } else if (obj.equals(WELCOME)) {
                        numberOfQuestions = Integer.parseInt((String) in.readObject());
                        numberOfRounds = Integer.parseInt((String) in.readObject());
                        out.writeObject(JOptionPane.showInputDialog(null, "What is your name?"));
                    } else if (obj instanceof Integer s) {
                        g.addPointsToOpponent(s);
                    }
                } catch (EOFException e) {
                    System.out.println("EOFException fångad. Avslutar loopen.");
                    break;
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }

            while (true) {
                try {
                    obj = in.readObject();
                    if (obj == null || obj instanceof Integer s) {
                        System.out.println("EOFException fångad eller sista poängen. Avslutar loopen.");
                        break;
                    }
                    if (obj instanceof Integer s) {
                        System.out.println("Sista poängen");
                        g.addPointsToOpponent(s);
                        g.waiting();
                    }
                } catch (EOFException e) {
                    System.out.println("EOFException fångad. Avslutar loopen.");
                    break;
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
