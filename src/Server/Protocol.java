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
        PLAYER_NAME_ENTRY,
        CATEGORY_SELECTION,
        QUESTION,
        WAITING,
        FINISHED,
    }

    private GameState state = GameState.INITIAL;
    private List<String> currentQuestions;
    private int questionCounter = 0;
    private Integer score;
    private String playerName;
    static final String WELCOME = "STARTGAMEFROMCLIENTXXX";
    static final String END_GAME = "ENDGAMEFROMCLIENTXXX";

    public void processUserInput(Object userInput, ObjectOutputStream out) throws IOException {
        switch (state) {
            case INITIAL:
                state = GameState.PLAYER_NAME_ENTRY;
                out.writeObject(WELCOME);
                break;

            case PLAYER_NAME_ENTRY:
                if(!gameCoordinator.isTwoPlayers) {
                    state = GameState.CATEGORY_SELECTION;
                }else{
                    state = GameState.WAITING;
                }
                playerName = (String) userInput;
                Player player = new Player(out, playerName);
                gameCoordinator.addPlayer(player);
                gameCoordinator.setTwoPlayers(!gameCoordinator.isTwoPlayers);
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
                // + logik för att beräkna resultatet
                break;
            default:
        }
    }

}
