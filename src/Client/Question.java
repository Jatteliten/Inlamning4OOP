package Client;

import Server.Answers;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    String questionText;
    ArrayList<Server.Answers> answersList = new ArrayList<>();

    public Question(String text, Server.Answers answer1, Server.Answers answer2, Server.Answers answer3, Server.Answers answer4){
        this.questionText = text;
        answersList.add(answer1);
        answersList.add(answer2);
        answersList.add(answer3);
        answersList.add(answer4);
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<Server.Answers> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(ArrayList<Server.Answers> answersList) {
        this.answersList = answersList;
    }

    public Answers getAnswer(int questionNumber){
        return answersList.get(questionNumber);
    }
    public String getQuestonText(){
        return this.questionText;
    }

}