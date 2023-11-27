package Client;

import java.io.*;
import java.net.Socket;

import Utilities.AvatarProperties;
import Utilities.Category;
import Utilities.Question;

public class Client {
    private static final int PORT = 12344;
    private static final String IP = "127.0.0.1";
    private static final String WELCOME = "START_GAME_FROM_CLIENT_XXX";
    private static final String END_GAME = "END_GAME_FROM_SERVER_XXX";
    Object obj;
    int numberOfQuestions;
    int numberOfRounds;

    public Client() {
        try (Socket socketToServer = new Socket(IP, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream())) {

            GameGraphics g = new GameGraphics();

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
                        g.nameAndAvatarEntry(out);
                    } else if (obj instanceof Integer s) {
                        g.addPointsToOpponent(s);
                    } else if(obj instanceof AvatarProperties a){
                        initializeOpponentAvatar(a, g);
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
                    if (obj == null) {
                        System.out.println("EOFException fångad eller sista poängen. Avslutar loopen.");
                        break;
                    }
                    if (obj instanceof Integer s) {
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

    public static void main(String[] args) {
        new Client();
    }
}
