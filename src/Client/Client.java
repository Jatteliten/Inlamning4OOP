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
    static final String NEW_GAME_DENIED = "NEW_GAME_DENIED_FROM_SERVER_XXX";
    static final String NEW_GAME_START = "NEW_GAME_START_FROM_SERVER_XXX";
    static final String PLAYER_HAS_QUIT_GAME = "PLAYER_HAS_QUIT_GAME_XXX";
    int numberOfQuestions = 0;
    int numberOfRounds = 0;

    public Client() throws IOException {
        try (Socket socketToServer = new Socket(IP, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream())) {

            GameGraphics g = new GameGraphics();
            g.setOutPutStream(out);
            while (true) {
                while (true) {
                    try {
                        obj = in.readObject();
                        if (obj == null || obj.equals(END_GAME)) {
                            break;
                        } else if (obj instanceof Question q) {
                            g.addQuestions(q);
                            if (g.getQuestions().size() == numberOfQuestions) {
                                g.displayQuestions(g.getQuestions());
                            }
                        } else if (obj instanceof Category c) {
                            g.displayCategoryChoice(c, (Category) in.readObject(), (Category) in.readObject());
                        } else if (obj.equals(WELCOME)) {
                            numberOfQuestions = Integer.parseInt((String) in.readObject());
                            numberOfRounds = Integer.parseInt((String) in.readObject());
                            g.setNumberOfRounds(numberOfRounds);
                            g.nameAndAvatarEntry();
                        } else if (obj instanceof Integer s) {
                            g.addPointsToOpponent(s);
                            g.waiting();
                        } else if (obj instanceof AvatarProperties a) {
                            initializeOpponentAvatar(a, g);
                            g.waiting();
                        }else if(obj.equals(PLAYER_HAS_QUIT_GAME)){
                            JOptionPane.showMessageDialog(null,
                                    "Your opponent has quit the game\nYou win!");
                            System.exit(0);
                        }
                    } catch (EOFException e) {
                        break;
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (g.opponentPoints.size() == numberOfRounds) {
                    g.waiting();
                    g.clearAllPointArrays();
                }
                while (true) {
                    try {
                        obj = in.readObject();
                        if (obj == null) {
                            break;
                        }
                        if (obj instanceof Integer s) {
                            g.addPointsToOpponent(s);
                            g.waiting();
                        }
                        if (obj.equals(NEW_GAME_REQUEST)) {
                            int result = g.showCustomYesNoOptionDialog(
                                    "Motståndare frågar om du vill spela igen?",
                                    "Spela igen?",
                                    "Ja, starta spel",
                                    "Nej och avsluta"
                            );
                            if (result == JOptionPane.YES_OPTION) {
                                out.writeObject(NEW_GAME_REQUEST);
                            } else {
                                out.writeObject(NEW_GAME_DENIED);
                                System.exit(0);
                            }
                        }
                        if (obj.equals(NEW_GAME_START)) {
                            g.clearAllPointArrays();
                            g.displayWaitingMessage();
                            break;
                        }
                        if (obj.equals(NEW_GAME_DENIED)) {
                            g.showCustomOptionDialog("Din förfrågan blev nekad", "Spela igen svar", "Avsluta");
                            System.exit(0);
                        }
                    } catch (EOFException e) {
                        System.exit(0);
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

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
