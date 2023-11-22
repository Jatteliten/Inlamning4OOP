package Utilities;

import Utilities.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
    List<Question> questionList;
    String categoryText;

    public Category(String text) {
        this.categoryText = text;
        this.questionList = new ArrayList<>();
    }

    public List<Question> getQuestionsList() {
        return questionList;
    }

    public void setQuestionsList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public void addQuestionToList(Question q){
        questionList.add(q);
    }

}