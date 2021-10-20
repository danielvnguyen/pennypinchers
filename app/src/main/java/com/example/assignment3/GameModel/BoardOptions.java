package com.example.assignment3.GameModel;

/**
 * This class handles the board option configurations.
 * Provides Singleton support to send the options data
 * to other classes. Data includes board height, width,
 * number of mines.
 */
public class BoardOptions {
    private Integer boardWidth;
    private Integer boardHeight;
    private Integer numOfMines;
    private static BoardOptions instance;

    public static BoardOptions getInstance() {
        if (instance == null) instance = new BoardOptions();
        return instance;
    }

    private BoardOptions() {
        setDefaultBoard();
    }

    private void setDefaultBoard() {
        this.boardWidth = 6;
        this.boardHeight = 4;
        this.numOfMines = 6;
    }

    public void setBoardSize(Integer boardWidth, Integer boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public void setNumOfMines(Integer numOfMines) {
        this.numOfMines = numOfMines;
    }

    public Integer getBoardWidth() {
        return boardWidth;
    }

    public Integer getBoardHeight() {
        return boardHeight;
    }

    public Integer getNumOfMines() {
        return numOfMines;
    }
}
