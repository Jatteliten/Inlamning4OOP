package Server;

import Utilities.Category;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Protocol {

    private ArrayList<Category> categories;

    public Protocol(ArrayList<Category> categories) {
        this.categories = categories;
    }

    private List<String> currentQuestions;
    private int questionCounter = 0;
    private int player1Score = 0;
    private int player2Score = 0;

    public void processUserInput(Object userInput, ObjectInputStream in,
                                 ObjectOutputStream out, Player p, GameCoordinator gameCoordinator) throws IOException, ClassNotFoundException {

        if (userInput instanceof Category q) {
            Collections.shuffle(q.getQuestionsList());
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    gameCoordinator.getPlayers().get(i).getObjectOutputStream().writeObject(q.getQuestionsList().get(j));
                }
            }
        } else if (userInput instanceof Integer) {
            int playerScore = (Integer) userInput;
            int playerNumber = p.getPlayerNumber();

            if (playerNumber == 1) {
                player1Score += playerScore;
            } else if (playerNumber == 2) {
                player2Score += playerScore;
            }

            gameCoordinator.playerScored();

            if (gameCoordinator.isRoundComplete()) {
                for (Player pl : gameCoordinator.getPlayers()) {
                    pl.getObjectOutputStream().writeObject(player1Score);
                    pl.getObjectOutputStream().writeObject(player2Score);
                }
                player1Score = 0;
                player2Score = 0;
                gameCoordinator.setRoundComplete(false);
            }
        }
    }
}