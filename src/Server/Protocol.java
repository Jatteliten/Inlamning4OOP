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
                                 ObjectOutputStream out, Player p, GameCoordinator gameCoordinator) throws IOException, ClassNotFoundException {
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
            for (Player pl : gameCoordinator.getPlayers()) {
                if (p != pl) {
                    pl.getObjectOutputStream().writeObject(i);
                    for (Question q : currentQuestions) {
                        pl.getObjectOutputStream().writeObject(q);
                    }
                    currentQuestions.clear();
                } else {
                    p.setPicksCurrentCategory(!p.isPicksCurrentCategory());
                    p.addScore(i);
                    System.out.println(p.getName());
                    System.out.println(p.getScore());
                    if (p.isPicksCurrentCategory()) {
                        Collections.shuffle(categories);
                        for (int j = 0; j < 3; j++) {
                            out.writeObject(categories.get(j));
                        }
                    }
                }
            }
        }
    }

    public Properties getProperties() {
        return properties;
    }
}