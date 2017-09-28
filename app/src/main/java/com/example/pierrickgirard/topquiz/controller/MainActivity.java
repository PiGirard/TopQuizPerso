package com.example.pierrickgirard.topquiz.controller;

import com.example.pierrickgirard.topquiz.R;
import com.example.pierrickgirard.topquiz.model.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;


    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String EDITOR_KEY = "firstname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("MainActivity::onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String firstName = getPreferences(MODE_PRIVATE).getString(EDITOR_KEY, null);
        int score = getPreferences(MODE_PRIVATE).getInt("SCORE",0);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mPlayButton.setEnabled(false);

        mUser = new User();

        if (firstName != null)
        {
            mGreetingText.setText("Welcome back, "+firstName+"!\nYour last score "+Integer.toString(score)+", will you do better this time ?");
            mNameInput.setText(firstName);
            mNameInput.setSelection(firstName.length());
            mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
            mPlayButton.setEnabled(true);
        }

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // The Play button will stay disabled as long as the input field is empty
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user just clicked
                mUser.setFirstName(mNameInput.getText().toString());
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(EDITOR_KEY, mUser.getFirstName());
            editor.putInt("SCORE", score);
            editor.apply();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("Mainctivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("MainActivity::onDestroy()");
    }
}
