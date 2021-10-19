package com.example.assignment3.GameModel;

import android.widget.Button;

/**
 * This class represents the money bag objects
 * that spread through the grid. Data includes
 * each button, booleans depending on the
 * money bag's status, and info of it's position.
 */
public class MoneyBag {
    private final Button btn;
    private boolean isPenny;
    private boolean isClicked;
    private boolean isScan;

    public MoneyBag(Button btn, boolean isPenny, boolean isClicked) {
        this.btn = btn;
        this.isPenny = isPenny;
        this.isClicked = isClicked;
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
