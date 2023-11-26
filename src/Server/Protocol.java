package Server;

import Utilities.Category;
import Utilities.Question;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

    static final String END_GAME = "END_GAME_FROM_SERVER_XXX";
    static final String NEW_GAME = "NEW_GAME_FROM_SERVER_XXX";

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

    public void processUserInput(Object userInput, ObjectInputStream in,
                                 ObjectOutputStream out, Player p, GameCoordinator gameCoordinator)
                                    throws IOException, ClassNotFoundException {

        Player secondPlayer = null;

        if(gameCoordinator.getPlayers().size() % 2 == 0) {
            secondPlayer = checkForSecondPlayer(p, gameCoordinator);
        }

        if (userInput instanceof Category q) {
            Collections.shuffle(q.getQuestionsList());
            currentQuestions = new ArrayList<>();
            for (int i = 0; i < numberOfQuestions; i++) {
                currentQuestions.add(q.getQuestionsList().get(i));
                if (p.isPicksCurrentCategory()) {
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
            for (Question q : currentQuestions) {
                secondPlayer.getObjectOutputStream().writeObject(q);
            }
            currentQuestions.clear();

            p.setPicksCurrentCategory(!p.isPicksCurrentCategory());
            p.addScore(i);
            if (counter == numberOfRounds){
                    p.getObjectOutputStream().writeObject(END_GAME);
                } else if (p.isPicksCurrentCategory()) {
                Collections.shuffle(categories);
                for (int j = 0; j < 3; j++) {
                    out.writeObject(categories.get(j));
                }

            }

        } else if (userInput.equals(NEW_GAME)) {
            if (!gameCoordinator.playNewGame()){
                secondPlayer.getObjectOutputStream().writeObject(NEW_GAME);
                System.out.println("skickade new game");
                gameCoordinator.setPlayNewGame(true);
                counter = 0;
            }
            p.getObjectOutputStream().writeObject("NEW_GAME_START");
            Collections.shuffle(categories);
            for(int i = 0; i < 3; i++){
                out.writeObject(categories.get(i));
                gameCoordinator.setPlayNewGame(false);
                System.out.println("skickade kategorier");
            }
        }
    }

    public Properties getProperties() {
        return properties;
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