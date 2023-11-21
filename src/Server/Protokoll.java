package Server;

import java.util.ArrayList;
import java.util.List;

public class Protokoll {
    static final String WELCOME = "STARTGAMEFROMCLIENTXXX";
    static final String END_GAME = "ENDGAMEFROMCLIENTXXX";

    public enum GameState {
        INITIAL,
        PLAYER_ENTRY,
        CATEGORY_SELECTION,
        ROUND_1,
        ROUND_2,
        ROUND_3,
        SCORE_RESULT,
    }

    public enum RoundState {
        NOT_IN_ROUND,
        QUESTION,
    }

    protected GameState state = GameState.INITIAL;
    protected RoundState roundState = RoundState.NOT_IN_ROUND;
    protected String playerName;
    protected String selectedCategory;
    protected int playerScore = 0;
    protected int roundNumber = 1;

    private List<String> categories = new ArrayList<>();
    private List<List<String>> roundsQuestions = new ArrayList<>();

    public Protokoll() {
        categories.add("Kategori 1");
        categories.add("Kategori 2");

        List<String> round1Questions = new ArrayList<>();
        round1Questions.add("Fråga 1, Runda 1?");
        round1Questions.add("Fråga 2, Runda 1?");
        roundsQuestions.add(round1Questions);

        List<String> round2Questions = new ArrayList<>();
        round2Questions.add("Fråga 1, Runda 2?");
        round2Questions.add("Fråga 2, Runda 2?");
        roundsQuestions.add(round2Questions);

        List<String> round3Questions = new ArrayList<>();
        round3Questions.add("Fråga 1, Runda 3?");
        round3Questions.add("Fråga 2, Runda 3?");
        roundsQuestions.add(round3Questions);
    }

    public String getOutPut(String fromClient) {
        if (state == GameState.INITIAL) {
            state = GameState.PLAYER_ENTRY;
            return "Välkommen till Quizkampen, vänligen skriv in ditt namn.";
        } else if (state == GameState.PLAYER_ENTRY) {
            playerName = fromClient;
            state = GameState.CATEGORY_SELECTION;
            return "Hej " + playerName + "! Nu kan du välja en kategori.";
        } else if (state == GameState.CATEGORY_SELECTION) {
            int categoryIndex = Integer.parseInt(fromClient);
            if (categoryIndex >= 0 && categoryIndex < categories.size()) {
                selectedCategory = categories.get(categoryIndex);
                state = GameState.ROUND_1;
                return "Du har valt kategori: " + selectedCategory + ". Nu börjar runda 1!";
            } else {
                return "Ogiltigt kategori. Vänligen välj igen.";
            }
        } else if (state == GameState.ROUND_1 || state == GameState.ROUND_2 || state == GameState.ROUND_3) {
            if (roundsQuestions.get(state.ordinal() - GameState.ROUND_1.ordinal()).isEmpty()) {
                state = (state == GameState.ROUND_3) ? GameState.SCORE_RESULT : GameState.values()[state.ordinal() + 1];
                return "Runda " + roundNumber + " avklarad. Nu väntar vi på resultatet.";
            } else if(roundState == RoundState.NOT_IN_ROUND) {
                roundState = RoundState.QUESTION;
                return "Fråga: " + roundsQuestions.get(state.ordinal() - GameState.ROUND_1.ordinal()).remove(0);
            } else if (roundState == RoundState.QUESTION) {
                // Logik för att hantera svaret
                // Exempelvis, om svaret är korrekt, öka poängen
                playerScore++;
                state = (state == GameState.ROUND_3) ? GameState.SCORE_RESULT : state; // Gå till nästa fråga eller resultat
                // Kolla med Danne ifall vi ska hantera logiken här eller via frontend angående hantera ronder.
                return "Rätt svar! Poäng: " + playerScore;
            }
        } else if (state == GameState.SCORE_RESULT) {
            // Logik för att generera resultatet
            String resultMessage = "Spelet är avslutat, " + playerName + "!";
            resultMessage += "\nDitt slutresultat är: " + playerScore + " poäng.";
            return resultMessage;
        }

        return "Standardrespons om tillståndet inte känns igen.";
    }
}