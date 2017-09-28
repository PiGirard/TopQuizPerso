package com.example.pierrickgirard.topquiz.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by pierrick.girard on 21/09/2017.
 */

public class QuestionBank {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;

        // Shuffle the question list before storing it
        Collections.shuffle(mQuestionList);

        mNextQuestionIndex = 0;
    }

    public Question getQuestion() {
        // Loop over the questions and return a new one at each call

        if (mNextQuestionIndex == mQuestionList.size())
        {
            mNextQuestionIndex = 0;
        }

        return mQuestionList.get(mNextQuestionIndex++);
    }
}
