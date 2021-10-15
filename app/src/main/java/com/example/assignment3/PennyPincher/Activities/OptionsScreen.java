package com.example.assignment3.PennyPincher.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.GameModel.BoardOptions;
import com.example.assignment3.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Screen that allows user to change the number of mines and the size of the game board
 */
public class OptionsScreen extends AppCompatActivity {

    private BoardOptions boardOptions;
    private static final String SHARED_PREFS = "PENNY_PINCHER_SHARED_PREFERENCES";
    private final int[] MINE_AMOUNTS = new int[]{6, 10, 15, 20};
    private final int[] BOARD_WIDTHS = new int[]{4, 5, 6};
    private final int[] BOARD_HEIGHTS = new int[]{6, 10, 15};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        boardOptions = BoardOptions.getInstance();

        setUpTimesPlayedText();
        setUpBoardSizeRadioButtons();
        setUpEraseTimesPlayedButton();
        setUpMinesRadioButtons();
    }

    /**
     * Toggles the correct button based on their previous board size
     * Allows them to change the board size
     */
    private void setUpBoardSizeRadioButtons() {
        RadioButton[] radioButtonList = new RadioButton[]{
                findViewById(R.id.radio4x6),
                findViewById(R.id.radio5x10),
                findViewById(R.id.radio6x15),
        };

        // Show saved board sizes
        switch (boardOptions.getBoardWidth()) {
            case 5: {
                radioButtonList[1].toggle();
                break;
            }
            case 6: {
                radioButtonList[2].toggle();
                break;
            }
            default: {
                radioButtonList[0].toggle();
                break;
            }
        }

        // Onclick for each of the buttons defined above
        for (int i = 0; i < radioButtonList.length; i++) {
            int index = i;
            radioButtonList[i].setOnClickListener((v) -> {
                Integer width = BOARD_WIDTHS[index];
                Integer height = BOARD_HEIGHTS[index];
                boardOptions.setBoardSize(width, height);
                Log.d("Options", width + " x " + height +" Sized Board");
            });
        }
    }

    /**
     * Toggles the correct button based on their previous selection
     * Allows them to change the num of mines
     */
    private void setUpMinesRadioButtons() {
        RadioButton[] radioButtonList = new RadioButton[]{
                findViewById(R.id.radio6Mines),
                findViewById(R.id.radio10Mines),
                findViewById(R.id.radio15Mines),
                findViewById(R.id.radio20Mines)
        };

        // Shows saved num of mines
        switch (boardOptions.getNumOfMines()) {
            case 10: {
                radioButtonList[1].toggle();
                break;
            }
            case 15: {
                radioButtonList[2].toggle();
                break;
            }
            case 20: {
                radioButtonList[3].toggle();
                break;
            }
            default: {
                radioButtonList[0].toggle();
                break;
            }
        }

        // Onclick for each of the buttons defined above
        for (int i = 0; i < radioButtonList.length; i++) {
            int index = i;
            radioButtonList[i].setOnClickListener((v) -> {
                Integer numOfMines = MINE_AMOUNTS[index];
                boardOptions.setNumOfMines(numOfMines);
                Log.d("Options",numOfMines +" Mine Game");
            });
        }
    }

    /** Sets the number of times played to zero in shared prefs */
    private void setUpEraseTimesPlayedButton() {
        Button btnEraseTimesPlayed = findViewById(R.id.btnEraseTimesPlayed);

        // Shared prefs to set the saved number of times played to 0
        btnEraseTimesPlayed.setOnClickListener((v)-> {
            TextView tvTimesPlayed = findViewById(R.id.tvTimesPlayed);
            SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.times_played_shared_pref), 0);
            editor.apply();
            tvTimesPlayed.setText("0");
        });
    }

    /**
     * Sets the number of times played view to the saved value in shared prefs
     */
    private void setUpTimesPlayedText() {
        TextView tvTimesPlayed = findViewById(R.id.tvTimesPlayed);
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String timesPlayed = Integer.toString(sharedPreferences.getInt(getString(R.string.times_played_shared_pref), 0));
        tvTimesPlayed.setText(timesPlayed);
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