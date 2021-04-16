package com.ungabunga.model.entities;

public class Players {
    private int x;
    private int y;

    public Players(int x,int y) {
        this.x = x;
        this.y = y;
    }
    public void move(int dx,int dy) {
        x+=dx;
        y+=dy;
    }
    public int getX() {
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}
