package com.example.assignment3.GameModel;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

public class GameLogic {

    private int scansUsed = 0;
    private int minesFound = 0;
    private MoneyBag[][] moneyBags;

    private Integer height;
    private Integer width;

    public GameLogic(Integer tableHeight, Integer tableWidth) {
        height = tableHeight;
        width = tableWidth;
        moneyBags = new MoneyBag[height][width];
    }

    public void addMoneyBags(Button btn, boolean isPenny, boolean isClicked, int row, int col) {
        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                moneyBags[i][k] = new MoneyBag(btn, isPenny, isClicked, row, col);
            }
        }
    }

    public void setPennies(int row, int col) {
        moneyBags[row][col].setPenny(true);
    }

    public void moneyBagClicked(int row, int col) {
        if (moneyBags[row][col].isPenny()) {
            System.out.println("hello, am penny");
        }
        else {
            System.out.println("am not penny");
        }
    }

}

class MoneyBag {
    private Button btn;
    private boolean isPenny;
    private boolean isClicked;
    private int row;
    private int col;

    public MoneyBag(Button btn, boolean isPenny, boolean isClicked, int row, int col) {
        this.btn = btn;
        this.isPenny = isPenny;
        this.isClicked = isClicked;
        this.row = row;
        this.col = col;
    }

    public boolean isPenny() {
        return isPenny;
    }

    public void setPenny(boolean penny) {
        isPenny = penny;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}