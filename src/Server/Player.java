package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Player {
    String name;
    int score = 0;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    boolean playerOne;


    //ObjectOutputStream används för att skicka poäng från spelaren till servern via sendScoreToServer-metoden.
   // ObjectInputStream används för att ta emot poäng från servern via receiveScoreFromServer-metoden.
    public Player(ObjectOutputStream os, ObjectInputStream is, String name) {
        this.objectOutputStream = os;
        this.objectInputStream = is;
        this.name = name;
    }

    public void sendScoreToServer() {
        try {
            objectOutputStream.writeObject(score);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metod för att ta emot poäng från servern
    public void receiveScoreFromServer() {
        try {
            int receivedScore = (int) objectInputStream.readObject();
            // Här kan man hantera de mottagna poängen
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
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

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }
}
