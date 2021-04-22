package com.ungabunga.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.ungabunga.model.utilities.Pair;

public class Player {

    public Texture avatar;

    public AVATAR_STATE state;

    public String name;

    Pair<Integer,Integer> position;

    private ActiveEngimon activeEngimon;

    public enum AVATAR_STATE{
        WALKING,
        STANDING,
        SWIMMING,
        HIKING,
        SLIDING,
    }

    public Player(String name){
        this.name = name;
        this.position = new Pair<Integer, Integer>(0,0);
        this.avatar = new Texture("Avatar/brendan_stand_south.png");
    }

    public void setName(String name){
        this.name = name;
    }

    public int getX(){
        return this.position.getFirst();
    }

    public int getY(){
        return this.position.getSecond();
    }

    public void moveUp() {
        int y = this.position.getSecond();
        this.position.setSecond(y+1);
    }

    public void moveDown() {
        int y = this.position.getSecond();
        this.position.setSecond(y-1);
    }

    public void moveLeft() {
        int x = this.position.getFirst();
        this.position.setFirst(x-1);
    }

    public void moveRight() {
        int x = this.position.getFirst();
        this.position.setFirst(x+1);
    }

    public Engimon getActiveEngimon(){
        try{
            return (Engimon) this.activeEngimon;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void setActiveEngimon(PlayerEngimon PE){
        this.activeEngimon = new ActiveEngimon(PE, this);
    }

    public Pair<Integer,Integer> getPosition(){
        return this.position;
    }
}
