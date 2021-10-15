package com.example.assignment3.GameModel;

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
        setBoardOptions(4, 6, 6);
    }

    private void setBoardOptions(Integer boardWidth, Integer boardHeight, Integer numOfMines) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.numOfMines = numOfMines;
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
