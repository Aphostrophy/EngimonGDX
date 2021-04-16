package com.ungabunga.model.entities;

import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.utilities.Pair;

/*
Active Engimon merepresentasikan Player Engimon yang sedang aktif merupakan turunan dari PlayerEngimon
yang memiliki posisi
 */

public class ActiveEngimon extends PlayerEngimon implements LivingEngimon{
    Pair<Integer,Integer> position;

    public ActiveEngimon(PlayerEngimon PE, Player P){
        super(PE);
        Pair<Integer,Integer> playerPosition = P.getPosition();
        this.position = new Pair<Integer,Integer>(0,0);
    }

    @Override
    public void moveUp() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Feature Not Available");
    }

    @Override
    public void moveDown() throws FeatureNotImplementedException{
        throw new FeatureNotImplementedException("Feature Not Available");
    }

    @Override
    public void moveLeft() throws FeatureNotImplementedException{
        throw new FeatureNotImplementedException("Feature Not Available");
    }

    @Override
    public void moveRight() throws FeatureNotImplementedException{
        throw new FeatureNotImplementedException("Feature Not Available");
    }

    @Override
    public Pair<Integer,Integer> getPosition() {
        return this.position;
    }

    @Override
    public void repositionOnCellConflict() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Feature Not Available");
    }

    @Override
    public int getRemainingLives() {
        return this.remainingLives;
    }

    @Override
    public boolean isDead() {
        return this.remainingLives<=0;
    }

    @Override
    public void reduceLives() {
        this.remainingLives--;
    }
}
