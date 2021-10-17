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
    private int nearbyHiddenMines;

    public MoneyBag(Button btn, boolean isPenny, boolean isClicked, int row, int col) {
        this.btn = btn;
        this.btn.setBackgroundResource(R.drawable.money_bag);
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

    public void setUpOnClick(Context context) {
        btn.setOnClickListener((v)-> {
            if (isPenny) {
                btn.setBackgroundResource(R.drawable.penny);
            }
            else {
                btn.setBackgroundColor(context.getResources().getColor(R.color.fadedWhite, context.getTheme()));
            }
        });
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public int getNearbyHiddenMines() {
        return nearbyHiddenMines;
    }

    public void setNearbyHiddenMines(int nearbyHiddenMines) {
        this.nearbyHiddenMines = nearbyHiddenMines;
    }
}