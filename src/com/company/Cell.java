package com.company;

import com.googlecode.lanterna.terminal.Terminal;

public class Cell {

    private int x;
    private int y;
    private boolean alive;
    private char displaychar;
    private Terminal.Color color;

    public Cell(){}


    public Cell(int x, int y, boolean alive,char displaychar, Terminal.Color color) {
        this.x = x;
        this.y = y;
        this.alive = alive;
        this.displaychar=displaychar;
        this.color=color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getStatus() {
        return alive;
    }

    public void setStatus(boolean status) {
        this.alive = status;
    }

    public char getDisplaychar() {
        return displaychar;
    }

    public void setDisplaychar(char displaychar) {
        this.displaychar = displaychar;
    }

    public Terminal.Color getColor() {
        return color;
    }

    public void setColor(Terminal.Color color) {
        this.color = color;
    }


}
