package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class OptionsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        setUpBoardSizeRadioButtons();
        setUpEraseTimesPlayedButton();
        setUpMinesRadioButtons();
    }

    private void setUpBoardSizeRadioButtons() {
        RadioButton radio4x6 = findViewById(R.id.radio4x6);
        RadioButton radio5x10 = findViewById(R.id.radio5x10);
        RadioButton radio6x15 = findViewById(R.id.radio6x15);

        radio4x6.setOnClickListener((v) -> Toast.makeText(this, "4x6", Toast.LENGTH_SHORT).show());
        radio5x10.setOnClickListener((v) -> Toast.makeText(this, "5x10", Toast.LENGTH_SHORT).show());
        radio6x15.setOnClickListener((v) -> Toast.makeText(this, "6x15", Toast.LENGTH_SHORT).show());
    }

    private void setUpMinesRadioButtons() {
        RadioButton radio6Mines = findViewById(R.id.radio6Mines);
        RadioButton radio10Mines = findViewById(R.id.radio10Mines);
        RadioButton radio15Mines = findViewById(R.id.radio15Mines);
        RadioButton radio20Mines = findViewById(R.id.radio20Mines);

        radio6Mines.setOnClickListener((v) -> Toast.makeText(this, "6 Mines", Toast.LENGTH_SHORT).show());
        radio10Mines.setOnClickListener((v) -> Toast.makeText(this, "10 Mines", Toast.LENGTH_SHORT).show());
        radio15Mines.setOnClickListener((v) -> Toast.makeText(this, "15 Mines", Toast.LENGTH_SHORT).show());
        radio20Mines.setOnClickListener((v) -> Toast.makeText(this, "20 Mines", Toast.LENGTH_SHORT).show());
    }

    private void setUpEraseTimesPlayedButton() {
        Button btnEraseTimesPlayed = findViewById(R.id.btnEraseTimesPlayed);
        btnEraseTimesPlayed.setOnClickListener((v)-> Toast.makeText(this, "ERASED TIMES PLAYED", Toast.LENGTH_SHORT).show());
    }

    /**
     * Creates the intent to start this activity
     * @param context Context of the current activity
     * @return The intent to start this activity
     */
    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionsScreen.class);
    }
}