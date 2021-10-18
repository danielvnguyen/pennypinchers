package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.GameModel.BoardOptions;
import com.example.assignment3.GameModel.GameLogic;
import com.example.assignment3.GameModel.MoneyBag;
import com.example.assignment3.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    private Integer tableHeight;
    private Integer tableWidth;
    private Integer numOfMines;
    private Integer numMinesFound;
    private GameLogic gameLogic;

    MoneyBag[][] moneyBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        addTimesPlayed();
        setUpBoardOptions();
        populateButtons();
        addInMines();
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
                tableRow.addView(button);
                button.setBackgroundResource(R.drawable.money_bag);

                moneyBags[row][col] = new MoneyBag(button, false, false, row, col, moneyBags);
                int finalRow = row;
                int finalCol = col;
                moneyBags[row][col].getButton().setOnClickListener((v)-> {
                    if (moneyBags[finalRow][finalCol].isPenny()) {
                        moneyBags[finalRow][finalCol].getButton().setBackgroundResource(R.drawable.penny);
                        System.out.println("hello");
                    }
                    else {
                        //moneyBags[finalRow][finalCol].getButton().setBackgroundColor(context.getResources().getColor(R.color.fadedWhite, context.getTheme()));
                    }
                });
            }
        }
    }

    private void addInMines() {
        ArrayList<MoneyBag> currentMines = new ArrayList<>();

        for (int i = 0; i < numOfMines; i++) {
            Random rand = new Random();
            boolean validMine = false;
            int randomRow = rand.nextInt(moneyBags.length);
            int randomCol = rand.nextInt(moneyBags[randomRow].length);
            MoneyBag bag = moneyBags[randomRow][randomCol];

            //make sure random positions are not chosen twice
            while (!validMine) {
                if (checkMines(bag, currentMines)) {
                    randomRow = rand.nextInt(moneyBags.length);
                    randomCol = rand.nextInt(moneyBags[randomRow].length);
                    bag = moneyBags[randomRow][randomCol];
                }
                else {
                    currentMines.add(bag);
                    validMine = true;
                    bag.getButton().setBackgroundResource(R.drawable.penny);
                    //gameLogic.setPennies(randomRow, randomCol);
                }
            }
        }
    }

    private boolean checkMines(MoneyBag bag, ArrayList<MoneyBag> lst) {
        return lst.contains(bag);
    }

    private void setUpBoardOptions() {
        BoardOptions boardOptions = BoardOptions.getInstance();
        tableWidth = boardOptions.getBoardWidth();
        tableHeight = boardOptions.getBoardHeight();
        numOfMines = boardOptions.getNumOfMines();

        moneyBags = new MoneyBag[tableHeight][tableWidth];
        gameLogic = new GameLogic(tableHeight, tableWidth);
    }

    private void addTimesPlayed() {
        OptionsScreen.setTimesPlayed(this, OptionsScreen.getTimesPlayed(this)+1);
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