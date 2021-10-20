package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Activity for that shows the splash for the application
 */
public class WelcomeScreen extends AppCompatActivity {

    // Stops the handler from starting opening the activity again
    private Boolean screenEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        fadeInInterface();
        setUpSkipButton();
        spinPenny();

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

    private void spinPenny() {
        ImageView imgPenny = findViewById(R.id.imgPenny);
        imgPenny.setAnimation(AnimationUtils.loadAnimation(this, R.anim.spin));
        imgPenny.animate();
    }

    /** Starts the main menu activity */
    private void startMainMenu() {
        if (!screenEnded) {
            screenEnded = true;
            startActivity(MainMenuScreen.makeIntent(this));
            finish();
        }
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