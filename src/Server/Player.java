package Server;

import java.io.ObjectOutputStream;

public class Player {
    String name;
    int score = 0;
    ObjectOutputStream objectOutputStream;
    boolean playerOne;

    public Player(ObjectOutputStream os, String name) {
        this.objectOutputStream = os;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int scoreToAdd) {
        this.score = score + scoreToAdd;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public int getPlayerNumber() {
        return playerOne ? 1 : 2;
    }
}