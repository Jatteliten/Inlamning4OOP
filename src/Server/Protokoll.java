package Server;

import java.util.List;

public class Protokoll {
    private enum GameState {
        INITIAL,
        PLAYER_1_ENTRY,
        PLAYER_2_ENTRY,
        CATEGORY_SELECTION,
        QUESTION,
        FINISHED,
    }

    private GameState state = GameState.INITIAL;
    private List<String> categories = List.of("Kategori 1", "Kategori 2", "Kategori 3");
    private List<String> currentQuestions;
    private int questionCounter = 0;
    private String player1Name;
    private String player2Name;
    static final String WELCOME = "STARTGAMEFROMCLIENTXXX";
    static final String END_GAME = "ENDGAMEFROMCLIENTXXX";
    private int player1Score = 0;
    private int player2Score = 0;

    public String processUserInput(String userInput) {
        switch (state) {
            case INITIAL:
                state = GameState.PLAYER_1_ENTRY;
                return "Väntar på spelare 1 att ange sitt namn.";

            case PLAYER_1_ENTRY:
                player1Name = userInput;
                state = GameState.PLAYER_2_ENTRY;
                return "name " + player1Name + " ansluten. Väntar på spelare 2 att ange sitt namn.";

            case PLAYER_2_ENTRY:
                player2Name = userInput;
                state = GameState.CATEGORY_SELECTION;
                return "name " + player2Name + " ansluten. Välj en kategori: " + String.join(", ", categories);

            case CATEGORY_SELECTION:
                state = GameState.QUESTION;
                currentQuestions = getQuestionsForCategory(userInput);
                return "Question " + currentQuestions.get(questionCounter);

            case QUESTION:
                if (questionCounter < 3) {
                    String currentQuestion = currentQuestions.get(questionCounter);
                    questionCounter++;
                    return "Question " + currentQuestion;
                } else {
                    state = GameState.FINISHED;
                    return "Inga fler frågor. Väntar på resultatet...";
                }

            case FINISHED:
                // + logik för att beräkna resultatet
                String resultMessage = "results Resultatet är klart.\n" +
                        player1Name + ": " + player1Score + " poäng\n" +
                        player2Name + ": " + player2Score + " poäng";
                return resultMessage;

            default:
                return "Ogiltigt tillstånd.";
        }
    }

    private List<String> getQuestionsForCategory(String category) {
        // implementera logik för att hämta fråggor baserat på kategorin

        return List.of("Fråga 1", "Fråga 2", "Fråga 3");
    }
}
