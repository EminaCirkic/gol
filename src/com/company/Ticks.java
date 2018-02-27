package com.company;

public class Ticks {

    int ticks;
    GameBoard games;

    public Ticks(){

    }

    public Ticks(int ticks, GameBoard games) {
        this.ticks = ticks;
        this.games = games;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public GameBoard getGames() {
        return games;
    }

    public void setGames(GameBoard games) {
        this.games = games;
    }
}
