package Server;

import java.io.ObjectOutputStream;

public class Player {
    String name;
    int score = 0;
    ObjectOutputStream objectOutputStream;
    boolean picksCurrentCategory;

    public Player(ObjectOutputStream os, String name){
        this.objectOutputStream = os;
        this.name = name;
        this.picksCurrentCategory = false;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
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
    public boolean isPicksCurrentCategory() {
        return picksCurrentCategory;
    }
    public void setPicksCurrentCategory(boolean picksCurrentCategory) {
        this.picksCurrentCategory = picksCurrentCategory;
    }
    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }
}
