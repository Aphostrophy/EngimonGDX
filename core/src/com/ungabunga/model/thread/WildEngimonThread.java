package com.ungabunga.model.thread;

import com.ungabunga.model.GameState;
import com.ungabunga.model.entities.WildEngimon;

public class WildEngimonThread extends Thread{

    WildEngimon wildEngimon;
    GameState gameState;

    public WildEngimonThread(WildEngimon wildEngimon, GameState gameState){
        this.wildEngimon = wildEngimon;
        this.gameState = gameState;
    }
    public void run(){
        while(!wildEngimon.isDead()){
            System.out.println(Thread.currentThread().getName() + " HAHAHAHAHA AKU HIDUP");
            wildEngimon.randomizeMove();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameState.reduceWildEngimon();
    }
}
