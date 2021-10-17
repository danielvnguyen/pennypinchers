package com.example.assignment3.GameModel;

public class BoardOptions {
    private Integer boardWidth;
    private Integer boardHeight;
    private Integer numOfMines;
    private Boolean isPlaying = false;
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

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }
}
