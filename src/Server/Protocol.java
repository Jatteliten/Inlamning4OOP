package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Protocol {

    ArrayList<Category> categories;

    public Protocol(ArrayList<Category> categories){
        this.categories = categories;
    }
    private enum GameState {
        CATEGORY_SELECTION,
        QUESTION,
        QUESTION2,
        WAITINNGFORRESPONCE,
        FINISHED,

    }

    private GameState state = GameState.INITIAL;
    private List<String> currentQuestions;
    private int questionCounter = 0;
    private String player1Name;
    private String player2Name;
    static final String WELCOME = "STARTGAMEFROMCLIENTXXX";
    static final String END_GAME = "ENDGAMEFROMCLIENTXXX";
    private int player1Score = 0;
    private int player2Score = 0;

    public void processUserInput(Object userInput, ObjectOutputStream out) throws IOException {
        switch (state) {
            case CATEGORY_SELECTION:
                state = GameState.QUESTION;
                currentQuestions = getQuestionsForCategory((String)userInput);
            case QUESTION:
                state = GameState.WAITINNGFORRESPONCE;

            case QUESTION2:
                state = GameState.WAITINNGFORRESPONCE;
            case FINISHED:
                // + logik för att beräkna resultatet
                String resultMessage = "results Resultatet är klart.\n" +
                        player1Name + ": " + player1Score + " poäng\n" +
                        player2Name + ": " + player2Score + " poäng";
            default:
        }
    }

    private List<String> getQuestionsForCategory(String category) {
        // implementera logik för att hämta fråggor baserat på kategorin

        return List.of("Fråga 1", "Fråga 2", "Fråga 3");
    }
}
