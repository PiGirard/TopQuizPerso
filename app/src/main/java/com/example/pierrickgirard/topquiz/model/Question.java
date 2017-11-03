package com.example.pierrickgirard.topquiz.model;

import java.util.List;

/**
 * Created by pierrick.girard on 21/09/2017.
 */

public class Question {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;


    public Question(String question, List<String> choiceList, int answerIndex)
    {
        this.setQuestion(question);
        this.setChoiceList(choiceList);
        this.setAnswerIndex(answerIndex);
    }

    /* Getter & Setter for the mQuestion attribute */
    public String getQuestion() {
        return mQuestion;
    }
    public void setQuestion(String question) {
        mQuestion = question;
    }


    /* Getter & Setter for the mChoiceListe attribute */
    public List<String> getChoiceList() {
        return mChoiceList;
    }
    public void setChoiceList(List<String> choiceList) {
        if (choiceList == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        mChoiceList = choiceList;
    }

    /* Getter & Setter for the mAnswerIndex attribute */
    public int getAnswerIndex() {
        return mAnswerIndex;
    }
    public void setAnswerIndex(int answerIndex) {
        if (answerIndex < 0 || answerIndex >= mChoiceList.size()) {
            throw new IllegalArgumentException("Answer index is out of bound");
        }
        mAnswerIndex = answerIndex;
    }

    /* toString() */
    @Override
    public String toString() {
        return "Question{" +
                "mQuestion='" + mQuestion + '\'' +
                ", mChoiceList=" + mChoiceList +
                ", mAnswerIndex=" + mAnswerIndex +
                '}';
    }
}
