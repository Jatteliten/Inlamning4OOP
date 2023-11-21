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
        INITIAL,
        CATEGORY_SELECTION,
        QUESTION1,
        QUESTION2,
        WAITING,
        FINISHED,
    }

    private GameState state = GameState.INITIAL;
    private List<Question> currentQuestions;
    private int questionCounter = 0;
    private Integer score;
    private String playerName;

    public void processUserInput(Object userInput, ObjectOutputStream out) throws IOException {
        switch (state) {
            case CATEGORY_SELECTION:
                out.writeObject(categories);
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
    }

}
