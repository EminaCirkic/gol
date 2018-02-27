package com.company;

import java.util.List;

public class GameBoard {

    int column;
    int row;
    List<Cell> cells;

    public GameBoard(){}

    public GameBoard(int column, int row, List<Cell> cells) {
        this.column = column;
        this.row = row;
        this.cells = cells;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
