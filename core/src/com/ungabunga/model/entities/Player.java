package com.ungabunga.model.entities;
import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.utilities.Pair;

public class Player {
    String name;
    Pair<Integer,Integer> position;

    public ActiveEngimon activeEngimon;

    public Player(String name){
        this.name = name;
        this.position = new Pair<Integer, Integer>(0,0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
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
