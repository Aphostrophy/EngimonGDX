package com.ungabunga.model.entities;

import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.utilities.Pair;

public interface LivingEngimon {

    public void moveUp() throws FeatureNotImplementedException;
    public void moveDown() throws FeatureNotImplementedException;
    public void moveLeft() throws FeatureNotImplementedException;
    public void moveRight() throws FeatureNotImplementedException;

    public void repositionOnCellConflict() throws FeatureNotImplementedException;

    public Pair<Integer,Integer> getPosition() throws FeatureNotImplementedException;
    public int getRemainingLives();
    public boolean isDead();
    public void reduceLives();
}
