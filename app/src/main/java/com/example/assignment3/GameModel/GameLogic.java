package com.example.assignment3.GameModel;

import android.widget.Button;
import android.widget.TableLayout;

public class GameLogic {
    /*
    This class should handle:
    - when a button is clicked (setOnClickListener?)
    - when user wins?
    - implementing how scans work (saving mines as 2D array, updating when hidden mine found + decreasing #'s, etc.)
        - how to get make 2d array with mines? should addInMines() be here?
    - updating # mines found and # scans used?
    - also do that display and save # games started feature.
     */

    //will need some getters/setters for these
    private static GameLogic instance;
    private int scansUsed = 0;
    private int minesFound = 0;
    public MoneyBags[][] moneyBags;

    public static GameLogic getInstance() {
        if (instance == null) instance = new GameLogic();
        return instance;
    }

    public int getScansUsed() {
        return scansUsed;
    }

    public void setScansUsed(int scansUsed) {
        this.scansUsed = scansUsed;
    }

    public int getMinesFound() {
        return minesFound;
    }

    public void setMinesFound(int minesFound) {
        this.minesFound = minesFound;
    }
}

class MoneyBags {
    private Button btn;
    private boolean hasPenny;
    private int row;
    private int col;

    public boolean isHasPenny() {
        return hasPenny;
    }
}