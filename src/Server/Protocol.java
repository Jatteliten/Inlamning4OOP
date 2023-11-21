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
        INITIAL,
        PLAYER_1_ENTRY,
        PLAYER_2_ENTRY,
        CATEGORY_SELECTION,
        QUESTION,
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
            case INITIAL:
                state = GameState.PLAYER_1_ENTRY;
                out.writeObject(WELCOME);

            case PLAYER_1_ENTRY:
                player1Name = (String) userInput;
                state = GameState.CATEGORY_SELECTION;

            case PLAYER_2_ENTRY:
                player2Name = (String) userInput;
                state = GameState.CATEGORY_SELECTION;

            case CATEGORY_SELECTION:
                state = GameState.QUESTION;
                currentQuestions = getQuestionsForCategory((String)userInput);

            case QUESTION:
                if (questionCounter < 3) {
                    String currentQuestion = currentQuestions.get(questionCounter);
                    questionCounter++;
                } else {
                    state = GameState.FINISHED;
                }

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
