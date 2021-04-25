package com.ungabunga.model.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.enums.CONSTANTS;
import com.ungabunga.model.utilities.Pair;
import com.ungabunga.model.utilities.ResourceProvider;

public class WildEngimon extends Engimon implements LivingEngimon {
    Pair<Integer,Integer> position;

    ResourceProvider resourceProvider;

    int remainingLives;

    DIRECTION direction;
    AVATAR_STATE state;

    private float srcX,srcY;
    private float destX,destY;
    private float worldX,worldY;
    private float animTimer;

    private float stateTimer;

    public WildEngimon(){

    }

    public WildEngimon(Engimon E, int spawnX, int spawnY, ResourceProvider resourceProvider){
//        super(E.name, E.species, E.slogan, E.level, E.elements, E.skills, E.parentName, E.parentSpecies);
        super(E);
        this.remainingLives = CONSTANTS.WILDENGIMONDEFAULTLIVES;

        this.direction = DIRECTION.DOWN;
        this.state = AVATAR_STATE.STANDING;

        this.resourceProvider = resourceProvider;

        Pair<Integer,Integer> position = new Pair<>();
        position.setFirst(spawnX);
        position.setSecond(spawnY);

        this.position = position;

        this.worldX =  this.position.getFirst();
        this.worldY = this.position.getSecond();
    }

    @Override
    public void move(int dx, int dy) {

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
    public TextureRegion getSprite() {
        return resourceProvider.getSprite(this);
    }

    @Override
    public float getKeyFrame() {
        return stateTimer;
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
    public float getWorldX() {
        return this.worldX;
    }

    @Override
    public float getWorldY() {
        return this.worldY;
    }

    @Override
    public String getEngimonSpecies(){return this.getSpecies();}

    @Override
    public AVATAR_STATE getState(){return this.state;}
}
