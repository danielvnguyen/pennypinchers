package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.GameModel.BoardOptions;
import com.example.assignment3.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class GameScreen extends AppCompatActivity {

    private Integer tableHeight;
    private Integer tableWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        setUpBoardOptions();
        populateButtons();
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableLayout);
        for (int row = 0; row < tableHeight; row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            for (int col = 0; col < tableWidth; col++) {
                Button button = new Button(this);
                tableRow.addView(button);
            }
        }
    }

    private void setUpBoardOptions() {
        BoardOptions boardOptions = BoardOptions.getInstance();
        tableWidth = boardOptions.getBoardWidth();
        tableHeight = boardOptions.getBoardHeight();
    }


    /**
     * Creates the intent to start this activity
     * @param context Context of the current activity
     * @return The intent to start this activity
     */
    public static Intent makeIntent(Context context) {
        return new Intent(context, GameScreen.class);
    }
}