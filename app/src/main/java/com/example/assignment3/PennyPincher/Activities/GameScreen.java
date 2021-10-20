package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.GameModel.BoardOptions;
import com.example.assignment3.GameModel.GameManager;
import com.example.assignment3.GameModel.MoneyBag;
import com.example.assignment3.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * This class displays the game and vital
 * information to the user playing. Data includes
 * # of mines found, # of scans used, game manager
 * object, table configuration. Supports adding in
 * all of the buttons, and handling animations.
 */
public class GameScreen extends AppCompatActivity {
    private static final int HIDDEN_MINE_FOUND = 0;
    private static final int MINE_USED_FOR_SCAN = 1;
    private static final int BAG_USED_FOR_SCAN = 2;

    private Integer tableHeight;
    private Integer tableWidth;
    private Integer numOfMines;
    private Integer scansUsed = 0;
    private Integer minesFound = 0;
    private GameManager gameManager;
    private MediaPlayer pennySound;
    private MediaPlayer shakeSound;
    MoneyBag[][] moneyBags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        setUpSounds();
        addTimesPlayed();
        setUpBoardOptions();
        populateButtons();
        addInMines();
        setUpStartButton();
    }

    @SuppressLint({"SetTextI18n"})
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

                button.setTypeface(Typeface.create("sans-serif-bold", Typeface.NORMAL));
                button.setTextSize(20);
                button.setVisibility(View.INVISIBLE);
                button.setLayoutParams(tableLayout);
                tableRow.addView(button);

                TextView scansUsedTV = findViewById(R.id.tvScansUsed);
                TextView minesFoundTV = findViewById(R.id.tvPenniesFound);

                //create MoneyBag object for each button in gameManager class
                gameManager.addMoneyBag(button, false, false, row, col);

                int finalRow = row;
                int finalCol = col;
                button.setOnClickListener((v)-> {

                    int clickResult = gameManager.moneyBagClicked(finalRow, finalCol);

                    if (clickResult == HIDDEN_MINE_FOUND) {
                        pennySound.start();
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
                    else if (clickResult == MINE_USED_FOR_SCAN) {
                        shakeSound.start();
                        scansUsed++;
                        scansUsedTV.setText(scansUsed + " scans used");
                        shakeBagsInRowsCols(finalRow, finalCol);

                    }
                    else if (clickResult == BAG_USED_FOR_SCAN) {
                        shakeSound.start();
                        scansUsed++;
                        scansUsedTV.setText(scansUsed + " scans used");
                        shakeBagsInRowsCols(finalRow, finalCol);
                    }
                });
            }
        }
    }

    /**
     * Sets the given button's background to a locked size
     * @param button Button to have background changed
     * @param resource Resource id
     */
    private void setScaledBackground(Button button, int resource) {
        int width = button.getWidth();
        int height = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), resource);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true);
        button.setBackground(new BitmapDrawable(getResources(), scaledBitmap));
    }

    private void shakeBagsInRowsCols(int row, int col) {
        for (int i = 0; i < tableHeight; i++) {
            MoneyBag moneyBag = moneyBags[i][col];
            if (!moneyBag.isClicked()) shakeBagAnimation(moneyBag.getButton());
        }
        for (int k = 0; k < tableWidth; k++) {
            MoneyBag moneyBag = moneyBags[row][k];
            if (!moneyBag.isClicked()) shakeBagAnimation(moneyBag.getButton());
        }
    }

    /**
     * Doing this animation with an xml does not work properly with the pennies for refreshing reasons
     * This work-around animates the object programmatically
     * @param button Button to be shook
     */
    private void shakeBagAnimation(Button button) {
        button.animate()
                .rotationXBy(0.5f)
                .rotationYBy(0.5f)
                .rotationBy(20)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        button.animate().rotation(0);
                    }
                });
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

    private void setUpSounds() {
        pennySound = MediaPlayer.create(getApplicationContext(), R.raw.penny_sound);
        shakeSound = MediaPlayer.create(getApplicationContext(), R.raw.shorter_shake);
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
