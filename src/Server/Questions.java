package Server;

import java.util.ArrayList;

public class Questions {

    String questionText;
    ArrayList<Answers> answersList = new ArrayList<>();

    public Questions(String text, Answers answer1, Answers answer2, Answers answer3, Answers answer4){
        this.questionText = text;
        answersList.add(answer1);
        answersList.add(answer2);
        answersList.add(answer3);
        answersList.add(answer4);
    }

    public ArrayList<Answers> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(ArrayList<Answers> answersList) {
        this.answersList = answersList;
    }

    public Answers getAnswer(int questionNumber){
        return answersList.get(questionNumber);
    }
}