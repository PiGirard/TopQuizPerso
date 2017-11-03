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

    /* Variables for the layout */
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;

    /* Variables for the model */
    private User mUser;

    /* Static variables */
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String FIRST_NAME_KEY = "firstname";
    private static final String SCORE_KEY = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* To debug purpose */
        System.out.println("MainActivity::onCreate()");

        super.onCreate(savedInstanceState);

        /* Set up the layout */
        setContentView(R.layout.activity_main);
        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mPlayButton.setEnabled(false);

        /* Init the mUser variable */
        mUser = new User();

        /* Check if this the first time the game is played. If not, get the Firstname and Score of the last player */
        String firstName = getPreferences(MODE_PRIVATE).getString(FIRST_NAME_KEY, null);
        int score = getPreferences(MODE_PRIVATE).getInt(SCORE_KEY,0);

        if (firstName != null)
        {/* The game was already played. Updates the view for the user */
            mGreetingText.setText("Welcome back, "+firstName+"!\nYour last score "+Integer.toString(score)+", will you do better this time ?");
            mNameInput.setText(firstName);
            mNameInput.setSelection(firstName.length());
            mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
            mPlayButton.setEnabled(true);
        }

        /* Listener for the NameInput */
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

        /* Lister for the Play Button */
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user just clicked
                mUser.setFirstName(mNameInput.getText().toString());
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                // Start Activity For Result because is waiting for the score in return
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /* To debug purpose */
        System.out.println("MainActivity::onActivityResult()");

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            // Saves the last result into the Preferences
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(FIRST_NAME_KEY, mUser.getFirstName());
            editor.putInt(SCORE_KEY, score);
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
