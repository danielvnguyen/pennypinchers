package com.example.assignment3.GameUI;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Intent intent = new Intent(this, MainMenu.class);

        Handler loadingHandler = new Handler();
        TextView mineSeekerTitle = findViewById(R.id.mineSeekerTitle);
        fadeIn(mineSeekerTitle);

        loadingHandler.postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 2000);

    }

    private void fadeIn(View view) {
        view.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        view.animate();
    }
}