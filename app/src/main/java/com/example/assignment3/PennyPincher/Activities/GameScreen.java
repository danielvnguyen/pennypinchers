package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.GameModel.BoardOptions;
import com.example.assignment3.GameModel.GameLogic;
import com.example.assignment3.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    private Integer tableHeight;
    private Integer tableWidth;
    private Integer numOfMines;
    private Integer numMinesFound = 0;
    private GameLogic gameLogic;

    Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        addTimesPlayed();
        setUpBoardOptions();
        setUpGameLogic();
        populateButtons();
        addInMines(); //should also be in gamelogic? besides the pictures of the mines
        //updateBoardScans(); <-- to update scan numbers whenever a hidden coin is found
    }

    private void setUpGameLogic() {
        gameLogic = GameLogic.getInstance();
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableLayout);
        TableRow.LayoutParams tableLayout = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );

        for (int row = 0; row < tableHeight; row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            tableRow.setLayoutParams(tableLayout);

            for (int col = 0; col < tableWidth; col++) {
                Button button = new Button(this);
                button.setLayoutParams(tableLayout);

                button.setOnClickListener(view -> gridButtonClicked(button));

                tableRow.addView(button);
                buttons[row][col] = button;
                button.setBackgroundResource(R.drawable.money_bag);
            }
        }
    }

    private void addInMines() {
        ArrayList<Button> currentMines = new ArrayList<>();

        for (int i = 0; i < numOfMines; i++) {
            Random rand = new Random();
            boolean validMine = false;
            int randomRow = rand.nextInt(buttons.length);
            int randomCol = rand.nextInt(buttons[randomRow].length);
            Button btn = buttons[randomRow][randomCol];

            //make sure random positions are not chosen twice
            while (!validMine) {
                if (checkMines(btn, currentMines)) {
                    randomRow = rand.nextInt(buttons.length);
                    randomCol = rand.nextInt(buttons[randomRow].length);
                    btn = buttons[randomRow][randomCol];
                }
                else {
                    currentMines.add(btn);
                    btn.setBackgroundResource(R.drawable.penny);
                    validMine = true;
                }
            }
        }
    }

    private boolean checkMines(Button btn, ArrayList<Button> lst) {
        return lst.contains(btn);
    }

    private void gridButtonClicked(Button btn) {
        TextView minesFound = findViewById(R.id.tvPenniesFound);
        TextView scansUsed = findViewById(R.id.tvScansUsed);

        //if user found a mine
        numMinesFound++;
        minesFound.setText("Found " + numMinesFound + " of " + numOfMines + " pennies!");

        //if user did not find a mine
        //activate scan. also activate if user clicks on non-hidden mine.
    }

    private void setUpBoardOptions() {
        BoardOptions boardOptions = BoardOptions.getInstance();
        tableWidth = boardOptions.getBoardWidth();
        tableHeight = boardOptions.getBoardHeight();
        numOfMines = boardOptions.getNumOfMines();
        buttons = new Button[tableHeight][tableWidth];
    }

    private void addTimesPlayed() {
        BoardOptions boardOptions = BoardOptions.getInstance();
        if (!boardOptions.getPlaying()) OptionsScreen.setTimesPlayed(this, OptionsScreen.getTimesPlayed(this)+1);
        boardOptions.setPlaying(true);
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