package Server;

import java.io.ObjectOutputStream;

public class Player {
    int score = 0;
    ObjectOutputStream objectOutputStream;

    public Player(ObjectOutputStream os){
        this.objectOutputStream = os;
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

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }
}
