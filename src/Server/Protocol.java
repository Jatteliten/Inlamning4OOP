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
    private List<String> currentQuestions;
    private int questionCounter = 0;
    private Integer score;

    public void processUserInput(Object userInput, ObjectOutputStream out) throws IOException {
        switch (state) {
            case CATEGORY_SELECTION:
                //skickar kategorier
                //får tillbaka svar
                state = GameState.QUESTION1;
            case QUESTION1:
                //skickar frågorna
                //får tillbaka poäng
                state = GameState.QUESTION2;
                break;
            case QUESTION2:
                //skickar frågorna
                //får tillbaka poäng
                state = GameState.CATEGORY_SELECTION;

            case FINISHED:
                break;
            default:
        }
    }

}
