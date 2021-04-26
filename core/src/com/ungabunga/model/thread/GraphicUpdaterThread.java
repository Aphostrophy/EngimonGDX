package com.ungabunga.model.thread;

import com.badlogic.gdx.Gdx;
import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.WildEngimon;
import com.ungabunga.model.exceptions.EngimonConflictException;

public class GraphicUpdaterThread extends Thread{

    WildEngimon wildEngimon;
    public GraphicUpdaterThread(WildEngimon wildEngimon){
        this.wildEngimon = wildEngimon;
    }

    public void run(){
        while(!wildEngimon.isDead()){
            try{
                wildEngimon.update(Gdx.graphics.getDeltaTime());
                Thread.sleep(10);
            } catch (EngimonConflictException | InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
