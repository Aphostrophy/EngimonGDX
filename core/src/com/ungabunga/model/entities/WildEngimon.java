package com.ungabunga.model.entities;

import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.LivingEngimon;
import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.enums.CONSTANTS;
import com.ungabunga.model.utilities.Pair;

public class WildEngimon extends Engimon implements LivingEngimon {
    Pair<Integer,Integer> position;
    int remainingLives;
    int turnsBeforeMoving;

    public WildEngimon(Engimon E){
//        super(E.name, E.species, E.slogan, E.level, E.elements, E.skills, E.parentName, E.parentSpecies);
        super(E);
        this.remainingLives = CONSTANTS.WILDENGIMONDEFAULTLIVES;
        this.turnsBeforeMoving = CONSTANTS.TURNSTOMOVE;
    }

    @Override
    public void moveUp() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Wild Pokemon move feature is not ready");
    }

    @Override
    public void moveDown() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Wild Pokemon move feature is not ready");
    }

    @Override
    public void moveLeft() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Wild Pokemon move feature is not ready");
    }

    @Override
    public void moveRight() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Wild Pokemon move feature is not ready");
    }


    @Override
    public void repositionOnCellConflict() throws FeatureNotImplementedException {
        throw new FeatureNotImplementedException("Wild Pokemon repositioning is not ready");
    }

    @Override
    public Pair<Integer, Integer> getPosition() throws FeatureNotImplementedException {
        return this.position;
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

    public int getTurnsBeforeMoving(){
        return turnsBeforeMoving;
    }
}
