package com.example.pierrickgirard.topquiz.controller;

import com.example.pierrickgirard.topquiz.R;
import com.example.pierrickgirard.topquiz.model.Question;
import com.example.pierrickgirard.topquiz.model.QuestionBank;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.os.Handler;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    /* Variables for the layout */
    private TextView mQuestion;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;

    /* Other variables */
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mNumberOfQuestions;
    private int mScore;

    private boolean mEnableTouchEvents;

    /* Static variables */
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    /* Set up the QuestionBanl */
    public QuestionBank generateQuestion() {

        Question question1 = new Question("Who is the creator of Android ?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon ?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons ?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        Question question4 = new Question("Question Debug / Rep = 1 ?",
                Arrays.asList("1",
                        "2",
                        "3",
                        "4"),
                0);

        Question question5 = new Question("Question Debug / Rep = 2 ?",
                Arrays.asList("1",
                        "2",
                        "3",
                        "4"),
                1);

        Question question6 = new Question("Question Debug / Rep = 3 ?",
                Arrays.asList("1",
                        "2",
                        "3",
                        "4"),
                2);

        Question question7 = new Question("Question Debug / Rep = 4 ?",
                Arrays.asList("1",
                        "2",
                        "3",
                        "4"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7));
    }

    /* Setter & Getter QuestionBank */
    public QuestionBank getQuestionBank()
    {
        return mQuestionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        mQuestionBank = questionBank;
    }

    /* Diplay the question at the screen */
    private void displayQuestion(final Question question) {
        // Set the text for the question text view and the four button
        mQuestion.setText(question.getQuestion());
        mAnswer1.setText(question.getChoiceList().get(0));
        mAnswer2.setText(question.getChoiceList().get(1));
        mAnswer3.setText(question.getChoiceList().get(2));
        mAnswer4.setText(question.getChoiceList().get(3));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* For debug purpose */
        System.out.println("GameActivity::onCreate()");

        super.onCreate(savedInstanceState);

        /* Check the context */
        if (savedInstanceState != null)
        {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        /* By default, touch screes is On */
        mEnableTouchEvents = true;

        /* Set up the layout */
        setContentView(R.layout.activity_game);

        mQuestion = (TextView) findViewById(R.id.activity_game_question_text);
        mAnswer1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswer2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswer3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswer4 = (Button) findViewById(R.id.activity_game_answer4_btn);

        // Use the tag property to 'name' the buttons
        mAnswer1.setTag(0);
        mAnswer2.setTag(1);
        mAnswer3.setTag(2);
        mAnswer4.setTag(3);

        // Use the same listener for the four buttons
        // The tag value will be used to distinguish the button triggered
        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);

        mQuestionBank = this.generateQuestion();
        mCurrentQuestion = mQuestionBank.getQuestion();
        displayQuestion(mCurrentQuestion);
    }

    @Override
    public void onClick(View v) {

        mEnableTouchEvents = false;

        int responseIndex = (int) v.getTag();

        /* Check if the answer is correct */
        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            mScore++;
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Faux", Toast.LENGTH_SHORT).show();
        }

        /* */
        mNumberOfQuestions = mNumberOfQuestions-1;

        /* Tempo + Check if this the end of the game */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // If this is the last question, end the game
                // Else, display the next question
                mEnableTouchEvents = true;

                if (mNumberOfQuestions == 0) {
                    // end of the game
                    endGame();
                }
                else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2300); // LENGTH_SHORT is usually 2 second long
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);
        super.onSaveInstanceState(outState);
        System.out.println("GameActivity::onSaveInstanceState()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("GameActivity::onDestroy()");
    }

}
