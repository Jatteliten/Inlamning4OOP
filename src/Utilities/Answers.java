package Utilities;

import java.io.Serializable;

public class Answers implements Serializable {

    boolean isCorrectAnswer;
    String answerText;

    public Answers(boolean correct, String text){
        isCorrectAnswer = correct;
        answerText = text;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}