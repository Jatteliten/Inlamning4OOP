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
        QUESTION,
        WAITING,
        FINISHED,
    }

    private GameState state = GameState.INITIAL;
    private List<String> currentQuestions;
    private int questionCounter = 0;
    private Integer score;

    public void processUserInput(Object userInput, ObjectOutputStream out) throws IOException {
        switch (state) {
            case INITIAL:
                state = GameState.CATEGORY_SELECTION;
                break;

            case WAITING:
                state = GameState.CATEGORY_SELECTION;
                break;

            case CATEGORY_SELECTION:
                state = GameState.QUESTION;
                break;

            case QUESTION:
                out.writeObject(score);
                break;

            case FINISHED:
                break;
            default:
        }
    }

}
