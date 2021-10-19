package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.GameModel.BoardOptions;
import com.example.assignment3.GameModel.GameManager;
import com.example.assignment3.GameModel.MoneyBag;
import com.example.assignment3.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {

    private Integer tableHeight;
    private Integer tableWidth;
    private Integer numOfMines;
    private Integer scansUsed = 0;
    private Integer minesFound = 0;
    private GameManager gameManager;

    MoneyBag[][] moneyBags;

    //high scores. 3 board sizes, 4 mine options each. so 12 high scores?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        addTimesPlayed();
        setUpBoardOptions();
        populateButtons();
        addInMines();
        setUpStartButton();
    }

    @SuppressLint("SetTextI18n")
    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableLayout);
        TableRow.LayoutParams tableLayout = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );
        tableLayout.setMargins(0,0,1,0);

        for (int row = 0; row < tableHeight; row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            tableRow.setLayoutParams(tableLayout);

            for (int col = 0; col < tableWidth; col++) {
                Button button = new Button(this);
                button.setVisibility(View.INVISIBLE);
                button.setLayoutParams(tableLayout);
                tableRow.addView(button);

                TextView scansUsedTV = findViewById(R.id.tvScansUsed);
                TextView minesFoundTV = findViewById(R.id.tvPenniesFound);

                //create MoneyBag object for each button in gameManager class
                gameManager.addMoneyBag(button, false, false, row, col, moneyBags);

                //still need to have onClickListener for the buttons, then call gameManager function
                int finalRow = row;
                int finalCol = col;
                button.setOnClickListener((v)-> {
                    int clickResult = gameManager.moneyBagClicked(finalRow, finalCol);

                    if (clickResult == 0) {
                        setScaledBackground(button, R.drawable.penny);
                        minesFound++;
                        minesFoundTV.setText(minesFound + " pennies found of " + numOfMines);

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
                    else if (clickResult == 1) {
                        scansUsed++;
                        scansUsedTV.setText(scansUsed + " scans used");
                    }
                    else if (clickResult == 2) {
                        scansUsed++;
                        scansUsedTV.setText(scansUsed + " scans used");
                    }
                    //do nothing for clickResult == 3
                });
            }
        }
    }

    private void setScaledBackground(Button button, int drawable) {
        int width = button.getWidth();
        int height = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), drawable);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true);
        button.setBackground(new BitmapDrawable(getResources(), scaledBitmap));
    }

    private void setUpStartButton() {
        Button startButton = findViewById(R.id.startButton);
        View coverView = findViewById(R.id.coverView);
        coverView.bringToFront();
        startButton.setOnClickListener((v)-> {

            // Setting the fade in effect for the money bags
            fadeOut(coverView);
            fadeOut(startButton);

            for (int col = 0; col < tableWidth; col++) {
                for (int row = 0; row < tableHeight; row++) {

                    // Locking the button sizes
                    Button button = moneyBags[row][col].getButton();
                    int width = button.getWidth();
                    button.setMaxWidth(width);
                    button.setMinWidth(width);
                    int height = button.getHeight();
                    button.setMaxHeight(height);
                    button.setMinHeight(width);
                    button.setVisibility(View.VISIBLE);

                    setScaledBackground(button, R.drawable.money_bag);
                }
            }
        });
    }

    private void fadeOut(View view) {
        view.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        view.animate();
        view.setVisibility(View.INVISIBLE);
    }

    private void addInMines() {
        gameManager.addInMines();
    }

    private void setUpBoardOptions() {
        BoardOptions boardOptions = BoardOptions.getInstance();
        tableWidth = boardOptions.getBoardWidth();
        tableHeight = boardOptions.getBoardHeight();
        numOfMines = boardOptions.getNumOfMines();

        moneyBags = new MoneyBag[tableHeight][tableWidth];
        gameManager = new GameManager(moneyBags, tableHeight, tableWidth, numOfMines);
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
