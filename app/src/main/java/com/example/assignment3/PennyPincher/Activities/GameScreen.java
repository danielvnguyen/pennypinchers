package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.GameModel.BoardOptions;
import com.example.assignment3.GameModel.MoneyBag;
import com.example.assignment3.R;

import android.content.Context;
import android.content.Intent;
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
    private Integer scansUsed = 0;
    private Integer minesFound = 0;

    Integer[] rowValues;
    Integer[] colValues;
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
                TextView scansUsedTV = findViewById(R.id.tvScansUsed);
                TextView minesFoundTV = findViewById(R.id.tvPenniesFound);
                button.setOnClickListener((v)-> {

                    //hidden penny clicked/found
                    if (moneyBags[finalRow][finalCol].isPenny() && !moneyBags[finalRow][finalCol].isClicked()) {
                        button.setBackgroundResource(R.drawable.penny);
                        moneyBags[finalRow][finalCol].setClicked(true);

                        minesFound++;
                        minesFoundTV.setText(minesFound + " pennies found of " + numOfMines);

                        //update grid (decrease 1 for each scanned block in current row/col)
                        //go through row/col that hidden mine was found in, update texts for each block that was scanned.
                        updateRowValues();
                        updateColValues();
                        updateScanValues(finalRow, finalCol);

                        //when user hits max pennies found, calculate score and announce win.
                        if (minesFound.equals(numOfMines)) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setTitle("You won!");
                            alert.setCancelable(false);
                            alert.setPositiveButton("Go back", (dialogInterface, i) -> finish());
                            alert.setIcon(R.drawable.pennypincherlogo);
                            alert.show();
                        }
                    }
                    //revealed penny clicked (trigger scan)
                    else if (moneyBags[finalRow][finalCol].isPenny() && moneyBags[finalRow][finalCol].isClicked()){
                        button.setBackgroundColor(getResources().getColor(R.color.fadedWhite, getTheme()));
                        moneyBags[finalRow][finalCol].setScan(true);

                        scansUsed++;
                        scansUsedTV.setText(scansUsed + "scans used");

                        updateRowValues();
                        updateColValues();
                        Integer nearbyMines = rowValues[finalRow] + colValues[finalCol];
                        button.setText(Integer.toString(nearbyMines));
                    }
                    //empty bag clicked (trigger scan)
                    else if (!moneyBags[finalRow][finalCol].isPenny() && !moneyBags[finalRow][finalCol].isClicked()) {
                        button.setBackgroundColor(getResources().getColor(R.color.fadedWhite, getTheme()));
                        moneyBags[finalRow][finalCol].setClicked(true);
                        moneyBags[finalRow][finalCol].setScan(true);

                        scansUsed++;
                        scansUsedTV.setText(scansUsed + " scans used");

                        updateRowValues();
                        updateColValues();
                        Integer nearbyMines = rowValues[finalRow] + colValues[finalCol];
                        button.setText(Integer.toString(nearbyMines));
                    }
                    //do nothing if user is clicking on a penny/empty bag that's been scanned already.
                });
            }
        }
    }

    //decrease by 1 for each scanner in current row/col.
    private void updateScanValues(int row, int col) {
        //for each column in the row, and for each row in the column
        //if current block is a scanner, update text (-1).
        for (int i = 0; i < row; i++) {
            if (moneyBags[i][col].isScan()) {
                int newValue = rowValues[i] + colValues[col];
                moneyBags[i][col].getButton().setText(Integer.toString(newValue));
            }
        }

        for (int k = 0; k < col; k++) {
            if (moneyBags[row][k].isScan()) {
                int newValue = rowValues[row] + colValues[k];
                moneyBags[row][k].getButton().setText(Integer.toString(newValue));
            }
        }
    }

    //Update rowValues
    private void updateRowValues() {
        //for each row
        for (int i = 0; i < tableHeight; i++) {
            int count = 0;
            //check each column, +1 if there's a penny in [row][column].
            for (int k = 0; k < tableWidth; k++) {
                //only increment count for hidden pennies.
                if (moneyBags[i][k].isPenny() && !moneyBags[i][k].isClicked()) {
                    count++;
                }
            }
            rowValues[i] = count;
        }
    }

    //Update colValues
    private void updateColValues() {
        //for each column
        for (int i = 0; i < tableWidth; i++) {
            int count = 0;
            //check each row
            for (int k = 0; k < tableHeight; k++) {
                if (moneyBags[k][i].isPenny() && !moneyBags[k][i].isClicked()) {
                    count++;
                }
            }
            colValues[i] = count;
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
                    moneyBags[randomRow][randomCol].setPenny(true);
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
        rowValues = new Integer[tableHeight];
        colValues = new Integer[tableWidth];
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