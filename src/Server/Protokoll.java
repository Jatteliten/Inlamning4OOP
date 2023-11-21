package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Protokoll {
    private enum GameState {
        INITIAL,
        PLAYER_ENTRY,
        NEW_GAME,
        CATEGORY_SELECTION,
        QUESTION,
        FINISHED,
    }

    private GameState state = GameState.INITIAL;
    private String currentPlayerName;
    private List<String> players = new ArrayList<>();
    private String currentCategory;
    private List<String> categories = List.of("Category1", "Category2", "Category3");
    private List<String> currentQuestions;
    private int questionCounter = 0;
    private int currentPlayerIndex = 0; // Håller koll på vilken spelare som är aktiv
    private List<Integer> playerScores = new ArrayList<>();

    public Protokoll() {
        players.add("Player1");
        players.add("Player2");
        playerScores.add(0);
        playerScores.add(0);
    }

    public String processUserInput(String userInput) {
        switch (state) {
            case INITIAL:
                state = GameState.PLAYER_ENTRY;
                return "Welcome [String]";

            case PLAYER_ENTRY:
                if (currentPlayerIndex < players.size()) {
                    currentPlayerName = userInput;
                    state = GameState.NEW_GAME;
                    return "name " + currentPlayerName;
                } else {
                    state = GameState.CATEGORY_SELECTION;
                    currentPlayerIndex = 0;
                    return "new game [String]";
                }

            case NEW_GAME:
                state = GameState.CATEGORY_SELECTION;
                return "new game [String]";

            case CATEGORY_SELECTION:
                state = GameState.QUESTION;
                setCurrentQuestions(userInput);
                return "Questions " + currentQuestions.get(questionCounter);

            case QUESTION:
                if (questionCounter < 4) {
                    String currentQuestion = currentQuestions.get(questionCounter);
                    questionCounter++;
                    return "Questions " + currentQuestion;
                } else {
                    state = GameState.FINISHED;
                    return "points " + playerScores.get(currentPlayerIndex);
                }

            case FINISHED:

                playerScores.set(currentPlayerIndex, playerScores.get(currentPlayerIndex) + Integer.parseInt(userInput));

                currentPlayerIndex++;
                if (currentPlayerIndex < players.size()) {
                    currentPlayerName = players.get(currentPlayerIndex);
                    state = GameState.CATEGORY_SELECTION;
                    questionCounter = 0;
                    return "new game [String]";
                } else {
                    String resultMessage = "results Resultatet är klart.\n";
                    for (int i = 0; i < players.size(); i++) {
                        resultMessage += players.get(i) + ": " + playerScores.get(i) + " poäng\n";
                    }
                    return resultMessage;
                }

            default:
                return "Ogiltigt tillstånd.";
        }
    }

    private void setCurrentQuestions(String selectedCategory) {
        // Implementera logik för att hämta frågor baserat på vald kategori
        // Exempel: this.currentQuestions = getQuestionsForCategory(selectedCategory);
        if (questionCounter == 0) {
            // Vid första frågan i varje rond, sätt ny kategori och frågor
            currentCategory = selectedCategory;
            this.currentQuestions = getQuestionsForCategory(currentCategory);
        }
    }
    private List<String> getQuestionsForCategory(String category) {
        List<String> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("CategoriesAndQuestions.txt"))) {
            String line;
            boolean inDesiredCategory = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Category") && line.contains(category)) {
                    inDesiredCategory = true;
                } else if (line.startsWith("Category")) {
                    inDesiredCategory = false;
                } else if (inDesiredCategory && line.startsWith("Fråga")) {
                    questions.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }


    public void handlePoints(String userInput) {
        if (state == GameState.QUESTION) {
            int points = Integer.parseInt(userInput);
            playerScores.set(currentPlayerIndex, playerScores.get(currentPlayerIndex) + points);
            state = GameState.CATEGORY_SELECTION;
        }
    }
}
