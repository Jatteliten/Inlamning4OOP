package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Protocol {

    ArrayList<Category> categories;
    GameCoordinator gameCoordinator;

    public Protocol(ArrayList<Category> categories, GameCoordinator gameCoordinator){
        this.categories = categories;
        this.gameCoordinator = gameCoordinator;
    }
    private enum GameState {
        CATEGORY_SELECTION,
        QUESTION1,
        QUESTION2,

    }
    private static final int CATEGORY_SELECTION = 0;
    private static final int QUESTION1 = 1;
    private static final int QUESTION2 = 2;
    private int state = CATEGORY_SELECTION;

   // private GameState state = GameState.CATEGORY_SELECTION;
    private List<Question> currentQuestions;
    private int questionCounter = 0;
    private Integer score;
    private String playerName;

    public void processUserInput(Object userInput, ObjectOutputStream out) throws IOException {
        System.out.println("hej");
    }
        /*if (state == CATEGORY_SELECTION){
            out.writeObject(categories);
            System.out.println("categorier är skickade!");
            if (userInput instanceof Category s){
                currentQuestions = s.questionList;
            }
            state = QUESTION1;
        } else if (state == QUESTION1) {
            out.writeObject(currentQuestions);
            if (userInput instanceof Integer s) {
                score = score + s;
            }
            //state = GameState.QUESTION2;
        }
    }*/
   /* public void processUserInput(Object userInput, ObjectOutputStream out) throws IOException {
        System.out.println("hej");
        switch (state) {
            case CATEGORY_SELECTION:
                out.writeObject(categories);
                System.out.println("categorier är skickade!");
                if (userInput instanceof Category s){
                    currentQuestions = s.questionList;
                }
                state = GameState.QUESTION1;
            case QUESTION1:
                out.writeObject(currentQuestions);
                if (userInput instanceof Integer s) {
                    score = score + s;
                }
                //state = GameState.QUESTION2;
                break;
            case QUESTION2:
                //skickar frågorna
                //får tillbaka poäng
                state = GameState.CATEGORY_SELECTION;
            default:
        }
    }*/


}
