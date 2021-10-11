package com.example.assignment3.GameUI;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;

import android.content.Intent;
import android.os.Bundle;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}