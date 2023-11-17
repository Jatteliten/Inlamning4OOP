package Server;

import java.util.ArrayList;

public class Questions {

    ArrayList<Answers> answersList = new ArrayList<>();

    public Questions(Answers answer1, Answers answer2, Answers answer3, Answers answer4){
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