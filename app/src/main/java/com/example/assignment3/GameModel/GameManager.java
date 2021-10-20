package com.example.assignment3.GameModel;

import android.annotation.SuppressLint;
import android.widget.Button;
import com.example.assignment3.R;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class manages the functionality of the GameScreen.
 * Data includes table configuration, collection of all the
 * money bags, and collections of the scan values. Supports
 * adding money bags, on click events, and scanning.
 */
public class GameManager {
    private static final int HIDDEN_MINE_FOUND = 0;
    private static final int MINE_USED_FOR_SCAN = 1;
    private static final int BAG_USED_FOR_SCAN = 2;
    private static final int SCAN_CLICKED = 3;

    Integer[] rowValues;
    Integer[] colValues;
    MoneyBag[][] moneyBags;
    private final int numOfMines;
    private final int tableHeight;
    private final int tableWidth;

    public GameManager(MoneyBag[][] parent, Integer height, Integer width, int mines) {
        moneyBags = parent;
        rowValues = new Integer[height];
        colValues = new Integer[width];
        numOfMines = mines;
        tableHeight = height;
        tableWidth = width;
    }

    public void addMoneyBag(Button btn, boolean isPenny, boolean isClicked, int row, int col) {
        moneyBags[row][col] = new MoneyBag(btn, isPenny, isClicked);
    }

    @SuppressLint("SetTextI18n")
    public int moneyBagClicked(int row, int col) {

        //hidden penny clicked/found
        if (moneyBags[row][col].isPenny() && !moneyBags[row][col].isClicked()) {
            moneyBags[row][col].setClicked(true);

            updateRowValues();
            updateColValues();
            updateScanValues();

            return HIDDEN_MINE_FOUND;
        }
        //revealed penny clicked (trigger scan)
        else if (moneyBags[row][col].isPenny() && moneyBags[row][col].isClicked()
                && !moneyBags[row][col].isScan()) {
            moneyBags[row][col].setScan(true);

            updateRowValues();
            updateColValues();

            int nearbyMines = rowValues[row] + colValues[col];
            moneyBags[row][col].getButton().setText(Integer.toString(nearbyMines));
            return MINE_USED_FOR_SCAN;
        }
        //empty bag clicked (trigger scan)
        else if (!moneyBags[row][col].isPenny() && !moneyBags[row][col].isClicked()) {
            moneyBags[row][col].getButton().setBackgroundResource(R.color.transparent);
            moneyBags[row][col].setClicked(true);
            moneyBags[row][col].setScan(true);

            updateRowValues();
            updateColValues();

            int nearbyMines = rowValues[row] + colValues[col];
            moneyBags[row][col].getButton().setText(Integer.toString(nearbyMines));
            return BAG_USED_FOR_SCAN;
        }
        //do nothing if user is clicking on a penny/empty bag that's been scanned already.
        return SCAN_CLICKED;
    }

    public void addInMines() {
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

    @SuppressLint("SetTextI18n")
    public void updateScanValues() {
        for (int i = 0; i < moneyBags.length; i++) {
            for (int k = 0; k < moneyBags[i].length; k++) {
                if (moneyBags[i][k].isScan()) {
                    int newValue = rowValues[i] + colValues[k];
                    moneyBags[i][k].getButton().setText(Integer.toString(newValue));
                }
            }
        }
    }

    public void updateRowValues() {
        for (int i = 0; i < tableHeight; i++) {
            int count = 0;
            for (int k = 0; k < tableWidth; k++) {
                if (moneyBags[i][k].isPenny() && !moneyBags[i][k].isClicked()) {
                    count++;
                }
            }
            rowValues[i] = count;
        }
    }

    public void updateColValues() {
        for (int i = 0; i < tableWidth; i++) {
            int count = 0;
            for (int k = 0; k < tableHeight; k++) {
                if (moneyBags[k][i].isPenny() && !moneyBags[k][i].isClicked()) {
                    count++;
                }
            }
            colValues[i] = count;
        }
    }
}
