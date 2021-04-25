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
        System.out.println(Thread.currentThread().getName() + " HAHAHAHAHA AKU HIDUP");
        while(!wildEngimon.isDead()){
            wildEngimon.randomizeMove();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " YAH AKU MATI");
        wildEngimon.removeFromMap();
        gameState.reduceWildEngimon();
    }
}
