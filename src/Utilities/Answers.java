package Utilities;

import java.io.Serializable;

public class Answers implements Serializable {

    String answerText;

    public Answers(String text){
        answerText = text;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}