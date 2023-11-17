package Server;

import java.util.ArrayList;

public class Categories {
    ArrayList<Questions> questionsList = new ArrayList<>();
    String categoryText;
    public Categories(String text){
        this.categoryText = text;
    }

    public ArrayList<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public void addQuestionToList(Questions q){
        questionsList.add(q);
    }

    public void removeQuestionFromList(Questions q){
        questionsList.remove(q);
    }
}
