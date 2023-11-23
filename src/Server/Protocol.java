package Server;

import Utilities.Category;
import Utilities.Question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Protocol {

    ArrayList<Category> categories;
    ArrayList<Question> currentQuestions;

    public Protocol(ArrayList<Category> categories){
        this.categories = categories;
    }
    private int questionCounter = 0;

    public void processUserInput(Object userInput, ObjectInputStream in,
                                 ObjectOutputStream out, Player p, GameCoordinator gameCoordinator) throws IOException, ClassNotFoundException {

        if(userInput instanceof Category q){
            Collections.shuffle(q.getQuestionsList());
            currentQuestions = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    currentQuestions.add(q.getQuestionsList().get(i));
                    gameCoordinator.getPlayers().get(0).getObjectOutputStream().writeObject(q.getQuestionsList().get(i));
                }

        }else if(userInput instanceof Integer i){
            for(Player pl: gameCoordinator.getPlayers()){
                if(p != pl){
                    pl.getObjectOutputStream().writeObject(i);
                    for(Question q: currentQuestions){
                        pl.getObjectOutputStream().writeObject(q);
                    }
                }else{
                    p.addScore(i);
                }
            }
        }

    }

}
