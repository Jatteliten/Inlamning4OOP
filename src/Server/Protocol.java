package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Protocol {

    ArrayList<Category> categories;

    public Protocol(ArrayList<Category> categories){
        this.categories = categories;
    }
    private List<String> currentQuestions;
    private int questionCounter = 0;
    private Integer score;

    public void processUserInput(Object userInput, ObjectInputStream in,
                                 ObjectOutputStream out, Player p, GameCoordinator gameCoordinator) throws IOException, ClassNotFoundException {

        if(userInput instanceof Category q){
            Collections.shuffle(q.getQuestionsList());
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 3; j++){
                    gameCoordinator.getPlayers().get(i).getObjectOutputStream().writeObject(q.getQuestionsList().get(j));
                }
            }
        }else if(userInput instanceof Integer){
            for(Player pl: gameCoordinator.getPlayers()){
                pl.getObjectOutputStream().writeObject(userInput);
            }
        }
    }

}
