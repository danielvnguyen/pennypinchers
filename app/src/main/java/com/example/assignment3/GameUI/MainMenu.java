package com.example.assignment3.GameUI;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setUpGameBtn();
        setUpOptionsBtn();
        setUpHelpBtn();
    }

    private void setUpOptionsBtn() {
        Button helpButton = findViewById(R.id.optionsButton);
        helpButton.setOnClickListener(view -> {
            Intent intent = OptionsScreen.makeIntent(this);
            startActivity(intent);
        });
    }

    private void setUpGameBtn() {
        Button helpButton = findViewById(R.id.playButton);
        helpButton.setOnClickListener(view -> {
            Intent intent = GameScreen.makeIntent(this);
            startActivity(intent);
        });
    }

    private void setUpHelpBtn() {
        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(view -> {
            Intent intent = HelpScreen.makeIntent(this);
            startActivity(intent);
        });
    }

    /**
     * Creates the intent to start this activity
     * @param context Context of the current activity
     * @return The intent to start this activity
     */
    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenu.class);
    }
}