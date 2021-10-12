package com.example.assignment3.GameUI;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Activity for activity_welcome_screen
 */
public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        fadeInInterface();
        setUpSkipButton();

        // Waits 4 seconds before ending the startMenu
        Handler loadingHandler = new Handler();
        loadingHandler.postDelayed(this::startMainMenu, 4000);

    }

    /** Sets up the button o skip the loading animations */
    private void setUpSkipButton() {
        Button btnSkip;
        btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener((v)-> startMainMenu());
    }

    /** Starts the main menu activity */
    private void startMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    /** Fades in all views on the activity_welcome_screen except button */
    private void fadeInInterface() {
        TextView tvMineSeekerTitle = findViewById(R.id.tvMineSeekerTitle);
        TextView tvAuthors = findViewById(R.id.tvAuthors);
        ProgressBar pbLoading = findViewById(R.id.pbLoading);
        fadeIn(pbLoading);
        fadeIn(tvMineSeekerTitle);
        fadeIn(tvAuthors);
    }

    /**
     * Fade in individual views to the screen
     * @param view The view to be faded in
     */
    private void fadeIn(View view) {
        view.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        view.animate();
    }
}