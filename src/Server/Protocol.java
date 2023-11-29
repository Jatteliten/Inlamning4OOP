package Server;

import Client.GameGraphics;
import Utilities.Category;
import Utilities.Question;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class Protocol {

    int numberOfRounds;
    int numberOfQuestions;
    ArrayList<Category> categories;
    ArrayList<Question> currentQuestions = new ArrayList<>();
    Properties properties;
    int counter = 0;
    boolean avatarSent = false;

    static final String END_GAME = "END_GAME_FROM_SERVER_XXX";
    static final String NEW_GAME_REQUEST = "NEW_GAME_REQUEST_FROM_SERVER_XXX";
    static final String NEW_GAME_START = "NEW_GAME_START_FROM_SERVER_XXX";
    static final String NEW_GAME_DENIED = "NEW_GAME_DENIED_FROM_SERVER_XXX";
    static final String PLAYER_HAS_QUIT_GAME = "PLAYER_HAS_QUIT_GAME_XXX";

    public Protocol(ArrayList<Category> categories){
        this.properties = new Properties();
        try {
            properties.load(new FileInputStream("src/Server/Properties.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        this.numberOfRounds = Integer.parseInt(properties.getProperty("numberOfRounds","3"));
        this.numberOfQuestions = Integer.parseInt(properties.getProperty("numberOfQuestions","2"));
        this.categories = categories;
    }

    public Properties getProperties() {
        return properties;
    }

    public void processUserInput(Object userInput, ObjectOutputStream out, Player p, GameCoordinator gameCoordinator)
                                    throws IOException, ClassNotFoundException {

        Player secondPlayer = null;

        if (gameCoordinator.getPlayers().size() % 2 == 0) {
            secondPlayer = checkForSecondPlayer(p, gameCoordinator);
        }

        if (userInput instanceof Category q) {
            Collections.shuffle(q.getQuestionsList());
            currentQuestions = new ArrayList<>();
            for (int i = 0; i < numberOfQuestions; i++) {
                currentQuestions.add(q.getQuestionsList().get(i));
                if (p.isPickingCurrentCategory()) {
                    p.getObjectOutputStream().writeObject(q.getQuestionsList().get(i));
                }

            }

        } else if (userInput instanceof Integer i) {
            while(secondPlayer == null){
                if(gameCoordinator.getPlayers().size() % 2 == 0) {
                    secondPlayer = checkForSecondPlayer(p, gameCoordinator);
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            counter++;
            secondPlayer.getObjectOutputStream().writeObject(i);
            if(!avatarSent) {
                secondPlayer.getObjectOutputStream().writeObject(p.getAvatar());
                avatarSent = true;
            }
            for (Question q : currentQuestions) {
                secondPlayer.getObjectOutputStream().writeObject(q);
            }
            currentQuestions.clear();

            p.setPicksCurrentCategory(!p.isPickingCurrentCategory());
            p.addScore(i);
            if (counter == numberOfRounds){
                    p.getObjectOutputStream().writeObject(END_GAME);
                } else if (p.isPickingCurrentCategory()) {
                Collections.shuffle(categories);
                for (int j = 0; j < 3; j++) {
                    out.writeObject(categories.get(j));
                }

            }

        } else if (userInput.equals(NEW_GAME_REQUEST)) {
            counter = 0;
            if (gameCoordinator.playNewGame()) {
                secondPlayer.setScore(0);
                p.setScore(0);
                out.writeObject(NEW_GAME_START);
                secondPlayer.getObjectOutputStream().writeObject(NEW_GAME_START);
                gameCoordinator.setPlayNewGame(false);
                p.setPicksCurrentCategory(true);
                secondPlayer.setPicksCurrentCategory(false);
                Collections.shuffle(categories);
                for (int i = 0; i < 3; i++) {
                    out.writeObject(categories.get(i));
                }
            } else if (!gameCoordinator.playNewGame()) {
                secondPlayer.getObjectOutputStream().writeObject(NEW_GAME_REQUEST);
                gameCoordinator.setPlayNewGame(true);
            }
        } else if (userInput.equals(NEW_GAME_DENIED)) {
            secondPlayer.getObjectOutputStream().writeObject(NEW_GAME_DENIED);
        }
    }


    public void userHasQuitGame(GameCoordinator gameCoordinator, Player p) throws IOException {
        if (gameCoordinator.getPlayers().size() % 2 == 0) {
            Player secondPlayer = checkForSecondPlayer(p, gameCoordinator);
            secondPlayer.getObjectOutputStream().writeObject(PLAYER_HAS_QUIT_GAME);
            gameCoordinator.getPlayers().remove(secondPlayer);
        }
        gameCoordinator.getPlayers().remove(p);
    }

    private Player checkForSecondPlayer(Player firstPlayer, GameCoordinator gameCoordinator){
        int playerNumberCheck = 0;
        Player secondPlayer;
        for(Player pl: gameCoordinator.getPlayers()){
            if (firstPlayer == pl){
                break;
            }
            else{
                playerNumberCheck++;
            }
        }
        if(playerNumberCheck % 2 != 0){
            secondPlayer = gameCoordinator.getPlayers().get(playerNumberCheck - 1);
        }else{
            secondPlayer = gameCoordinator.getPlayers().get(playerNumberCheck + 1);
        }
        return secondPlayer;
    }
}