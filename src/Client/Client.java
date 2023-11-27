package Client;

import java.io.*;
import java.net.Socket;

import Utilities.AvatarProperties;
import Utilities.Category;
import Utilities.Question;

import javax.swing.*;

public class Client {
    private static final int PORT = 12344;
    private static final String IP = "127.0.0.1";
    Object obj;
    static final String WELCOME = "START_GAME_FROM_CLIENT_XXX";
    static final String END_GAME = "END_GAME_FROM_SERVER_XXX";
    static final String NEW_GAME_REQUEST = "NEW_GAME_REQUEST_FROM_SERVER_XXX";
    static final String NEW_GAME_START = "NEW_GAME_START_FROM_SERVER_XXX";
    int numberOfQuestions = 0;
    int numberOfRounds = 0;

    public Client() throws IOException {
        try (Socket socketToServer = new Socket(IP, PORT);
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
                            //g.nameAndAvatarEntry(out);
                            g.loginOrRegister(out);
                        } else if (obj instanceof Integer s) {
                            g.addPointsToOpponent(s);
                        } else if (obj instanceof AvatarProperties a) {
                            initializeOpponentAvatar(a, g);
                        }
                    } catch (EOFException e) {
                        System.out.println("EOFException fångad. Avslutar loopen.");
                        break;
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (g.opponentPoints.size() == numberOfRounds) {
                    g.finalResult(out);
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
                            g.addPointsToOpponent(s);
                            g.finalResult(out);
                        }
                        if (obj.equals(NEW_GAME_REQUEST)) {
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
                        if (obj.equals(NEW_GAME_START)) {
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
            }
        }

    private static void initializeOpponentAvatar(AvatarProperties avatarProperties, GameGraphics g) {
        Avatar opponentAvatar = new Avatar();
        opponentAvatar.setCat(avatarProperties.cat());
        opponentAvatar.setAccessory(avatarProperties.accessory());
        opponentAvatar.setEyes(avatarProperties.eyes());
        opponentAvatar.setPattern(avatarProperties.pattern());
        opponentAvatar.setMouth(avatarProperties.mouth());
        opponentAvatar.setHeadWear(avatarProperties.headWear());
        opponentAvatar.shrinkImage();
        g.setOpponentAvatar(opponentAvatar);
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
