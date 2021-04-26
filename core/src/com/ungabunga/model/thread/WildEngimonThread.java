package com.ungabunga.model.thread;

import com.ungabunga.model.GameState;
import com.ungabunga.model.entities.WildEngimon;

import java.util.concurrent.ThreadLocalRandom;

public class WildEngimonThread extends Thread{

    WildEngimon wildEngimon;
    GameState gameState;
    int turns;

    public WildEngimonThread(WildEngimon wildEngimon, GameState gameState){
        this.wildEngimon = wildEngimon;
        this.gameState = gameState;

        this.turns = 0;
    }
    public void run(){
        while(!wildEngimon.isDead()){
            if(!this.wildEngimon.isInBattle) {
                wildEngimon.randomizeMove();
            }
            this.turns++;
            if(turns % 3 ==0){
                wildEngimon.addExp(25);
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(2000,5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wildEngimon.killEngimon();
        gameState.reduceWildEngimon();
    }
}
