package Server;

import java.util.ArrayList;

public class GameCoordinator {
    ArrayList<Player> players = new ArrayList<>();
    boolean isTwoPlayers = true;

    public boolean playNewGame() {
        return playNewGame;
    }

    public void setPlayNewGame(boolean playNewGame) {
        this.playNewGame = playNewGame;
    }

    boolean playNewGame = false;
    public GameCoordinator() {
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public boolean isTwoPlayers() {
        return isTwoPlayers;
    }

    public void setTwoPlayers(boolean twoPlayers) {
        isTwoPlayers = twoPlayers;
    }

    private boolean roundComplete = false;

    public boolean isRoundComplete() {
        return roundComplete;
    }

    public void setRoundComplete(boolean roundComplete) {
        this.roundComplete = roundComplete;
    }

    public void playerScored() {
    }
}
