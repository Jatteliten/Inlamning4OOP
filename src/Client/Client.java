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
    static final String NEW_GAME_REQUEST = "NEW_GAME_REQUEST_FROM_SERVER_XXX";
    static final String NEW_GAME_START = "NEW_GAME_START_FROM_SERVER_XXX";
    int numberOfQuestions = 0;
    int numberOfRounds = 0;

    public Client() {
        try (Socket socketToServer = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream())) {

            GameGraphics g = new GameGraphics();
            while (true) {
                System.out.println("tillbaka i starten");
                while (true) {
                    try {
                        System.out.println("obj nu " + obj);
                        obj = in.readObject();
                        System.out.println("obj sen " + obj);
                        if (obj == null || obj.equals(END_GAME)) {
                            break;
                        } else if (obj instanceof Question q) {
                            g.addQuestions(q);
                            if (g.getQuestions().size() == numberOfQuestions) {
                                g.displayQuestions(g.getQuestions(), out);
                            }
                        } else if (obj instanceof Category c) {
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
                if (g.opponentPoints.size() == numberOfRounds) {
                    System.out.println("är här");
                    g.finalResult(out);
                    out.writeObject(NEW_GAME_REQUEST);
                    System.out.println("skickade new game");
                    g.clearAllPointArrays();
                }
                while (true) {
                    try {
                    obj = in.readObject();
                        if (obj == null) {
                            System.out.println("EOFException fångad eller sista poängen. Avslutar loopen.");
                            break;
                        }
                        if (obj instanceof Integer s) {
                            System.out.println("är här 2");
                            g.addPointsToOpponent(s);
                            g.finalResult(out);
                        }
                        if (obj.equals(NEW_GAME_REQUEST)) {
                            System.out.println("tog emot new game");
                            int result = JOptionPane.showConfirmDialog(
                                    null,
                                    "Motståndare frågar om du vill spela igen?",
                                    "Spela igen?",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (result == JOptionPane.YES_OPTION) {
                                out.writeObject(NEW_GAME_REQUEST);
                                g.clearAllPointArrays();
                            } else {
                                System.out.println("User clicked No");
                            }
                        }
                        if (obj.equals(NEW_GAME_START)){
                            System.out.println("tog emot NEW_GAME_START");
                            g.waiting();
                            break;
                        }
                    } catch (EOFException e) {
                        System.out.println("EOFException fångad. Avslutar loopen.");
                        break;
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public static void main(String[] args) {
        new Client();
    }
}
