package Server;

import java.io.ObjectOutputStream;

public class Player {
    String name;
    int score = 0;
    ObjectOutputStream objectOutputStream;

    public Player(ObjectOutputStream os, String name){
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

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }
}
