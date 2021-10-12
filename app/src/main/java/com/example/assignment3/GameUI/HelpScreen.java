package com.example.assignment3.GameUI;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class HelpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
    }

    //make sure to finish when done

    /**
     * Creates the intent to start this activity
     * @param context Context of the current activity
     * @return The intent to start this activity
     */
    public static Intent makeIntent(Context context) {
        return new Intent(context, HelpScreen.class);
    }
}