package Server;

import Utilities.AvatarProperties;

import java.io.ObjectOutputStream;

public class Player {
    String name;
    int score = 0;
    ObjectOutputStream objectOutputStream;
    boolean picksCurrentCategory;
    AvatarProperties avatar;

    public Player(ObjectOutputStream os, AvatarProperties avatar, String name){
        this.objectOutputStream = os;
        this.avatar = avatar;
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
    public boolean isPickingCurrentCategory() {
        return picksCurrentCategory;
    }
    public void setPicksCurrentCategory(boolean picksCurrentCategory) {
        this.picksCurrentCategory = picksCurrentCategory;
    }
    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public AvatarProperties getAvatar() {
        return avatar;
    }
}
