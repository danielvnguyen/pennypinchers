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

    public void addMoneyBags(MoneyBag bag, int row, int col) {
        moneyBags[row][col] = bag;
    }

    public void setPennies(int row, int col) {
        moneyBags[row][col].setPenny(true);
    }

    public void moneyBagClicked(int row, int col) {
        if (moneyBags[row][col].isPenny() && !moneyBags[row][col].isClicked()) {
            System.out.println("hello, am penny that hasn't been used for scanning");
            minesFound++;
            moneyBags[row][col].setClicked(true);
        }
        else if (moneyBags[row][col].isPenny() && moneyBags[row][col].isClicked()){
            System.out.println("am penny being used for scanning");
            scansUsed++;
            //call scan function. whenever hidden mine found update scan #'s.
            int numScans = performScan(row, col);
            moneyBags[row][col].setNearbyHiddenMines(numScans);
        }
        else if (!moneyBags[row][col].isPenny() && !moneyBags[row][col].isClicked()){
            System.out.println("am an empty bag that hasn't been clicked");
            scansUsed++;
            moneyBags[row][col].setClicked(true);
            //call scan function.
            int numScans = performScan(row, col);
            moneyBags[row][col].setNearbyHiddenMines(numScans);

        }
        //do nothing if user is clicking on a penny/empty bag that's been scanned already.
    }

    //function used to update grid whenever hidden mine found
    private void performScanOnAll() {

    }

    public int performScan(int row, int col) {
        //check # hidden mines in given row and col.
        //return that number and update the text in the gamescreen
        //how to handle updating when finding hidden mines? just call this on the whole 2d array?
        int count = 0;
        for (int i = row; i<=row; i++) {
            for (int k = 0; k < col; k++) {
                if (moneyBags[i][k].isPenny() && !moneyBags[i][k].isClicked()) {
                    count++;
                }
            }
        }

        for (int j = col; j <= col; j++) {
            for (int w = 0; w < row; w++) {
                if (moneyBags[w][j].isPenny() && !moneyBags[w][j].isClicked()) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getScansUsed() {
        return scansUsed;
    }

    public int getMinesFound() {
        return minesFound;
    }
}