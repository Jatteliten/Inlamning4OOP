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
    static final String NEW_GAME = "NEW_GAME_FROM_SERVER_XXX";
    int numberOfQuestions = 0;
    int numberOfRounds = 0;

    public Client() {
        try (Socket socketToServer = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream())) {

            GameGraphics g = new GameGraphics();
            while (true) {
                while (true) {
                    try {
                        obj = in.readObject();
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
                    out.writeObject(NEW_GAME);
                    System.out.println("skickade new game");
                    g.clearAllPointArrays();
                    obj = 0;

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
                        if (obj.equals(NEW_GAME)) {
                            System.out.println("tog emot new game");
                            int result = JOptionPane.showConfirmDialog(
                                    null,
                                    "Do you want to proceed?",
                                    "Confirmation",
                                    JOptionPane.YES_NO_OPTION
                            );
                            if (result == JOptionPane.YES_OPTION) {
                                System.out.println("User clicked Yes");
                                g.clearAllPointArrays();
                                obj = 0;
                                g.waiting();
                                break;
                            } else {
                                System.out.println("User clicked No or closed the dialog");
                            }
                        }
                        if (obj.equals("NEW_GAME_START")){
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
