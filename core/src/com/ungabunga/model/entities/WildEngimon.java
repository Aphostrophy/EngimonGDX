package com.ungabunga.model.entities;

import com.ungabunga.model.GameState;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.enums.CONSTANTS;
import com.ungabunga.model.utilities.Pair;

public class WildEngimon extends Engimon implements LivingEngimon {
    Pair<Integer,Integer> position;
    int remainingLives;

    GameState gameState;

    DIRECTION direction;
    AVATAR_STATE state;

    public WildEngimon(){

    }

    public WildEngimon(Engimon E, GameState gameState){
//        super(E.name, E.species, E.slogan, E.level, E.elements, E.skills, E.parentName, E.parentSpecies);
        super(E);
        this.remainingLives = CONSTANTS.WILDENGIMONDEFAULTLIVES;

        this.gameState = gameState;

        this.direction = DIRECTION.DOWN;
        this.state = AVATAR_STATE.STANDING;
    }

    @Override
    public void moveUp()  {
        int y = position.getSecond();
        position.setSecond(y+1);
    }

    @Override
    public void moveDown() {
        int y = position.getSecond();
        position.setSecond(y-1);
    }

    @Override
    public void moveLeft() {
        int x = position.getFirst();
        position.setFirst(x-1);
    }

    @Override
    public void moveRight() {
        int x = position.getFirst();
        position.setFirst(x+1);
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
    public DIRECTION getDirection() {
        return this.direction;
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

    @Override
    public String getEngimonSpecies(){return this.getSpecies();}

    @Override
    public AVATAR_STATE getState(){return this.state;}
}
