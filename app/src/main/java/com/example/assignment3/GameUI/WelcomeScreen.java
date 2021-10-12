package com.example.assignment3.GameUI;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Intent intent = new Intent(this, MainMenu.class);

        Handler loadingHandler = new Handler();

        loadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}