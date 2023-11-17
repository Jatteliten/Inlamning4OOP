package Server;

public class Answers {

    boolean isCorrectAnswer;
    String answerText;

    Answers(boolean correct, String text){
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