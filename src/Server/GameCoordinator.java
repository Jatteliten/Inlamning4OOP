package Server;

import java.util.ArrayList;

public class GameCoordinator {
    ArrayList<Player> players = new ArrayList<>();
    boolean isTwoPlayers = true;
    boolean playNewGame = false;
    public GameCoordinator() {}

    public ArrayList<Player> getPlayers() {
        return players;
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

    public void setPlayNewGame(boolean playNewGame) {
        this.playNewGame = playNewGame;
    }
    public boolean playNewGame() {
        return playNewGame;
    }

}