package com.example.assignment3.GameModel;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment3.R;

public class MoneyBag {
    private Button btn;
    private boolean isPenny;
    private boolean isClicked;
    private int row;
    private int col;
    private MoneyBag[][] parent; //parent array, access to all other MoneyBags
    private boolean isScan;

    public MoneyBag(Button btn, boolean isPenny, boolean isClicked, int row, int col, MoneyBag[][] parent) {
        this.btn = btn;
        this.isPenny = isPenny;
        this.isClicked = isClicked;
        this.row = row;
        this.col = col;
        this.parent = parent;
        this.isScan = false;
    }

    public Button getButton() {
        return btn;
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

    public boolean isScan() {
        return isScan;
    }

    public void setScan(boolean scan) {
        isScan = scan;
    }
}
