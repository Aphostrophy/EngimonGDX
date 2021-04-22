package com.ungabunga.model.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ungabunga.model.enums.DIRECTIONS;

import java.util.HashMap;

public class AnimationSet {

    private HashMap<DIRECTIONS, Animation<TextureRegion>> walking;
    private HashMap<DIRECTIONS, TextureRegion> standing;

    public AnimationSet(Animation walkDown, Animation walkUp, Animation walkLeft, Animation walkRight, TextureRegion standDown, TextureRegion standUp, TextureRegion standLeft, TextureRegion standRight){
        walking = new HashMap<DIRECTIONS,Animation<TextureRegion>>();
        walking.put(DIRECTIONS.UP,walkUp);
        walking.put(DIRECTIONS.DOWN,walkDown);
        walking.put(DIRECTIONS.LEFT,walkLeft);
        walking.put(DIRECTIONS.RIGHT,walkRight);

        standing = new HashMap<DIRECTIONS, TextureRegion>();
        standing.put(DIRECTIONS.UP,standUp);
        standing.put(DIRECTIONS.DOWN, standDown);
        standing.put(DIRECTIONS.LEFT, standLeft);
        standing.put(DIRECTIONS.RIGHT, standRight);
    }


    public Animation<TextureRegion> getWalking(DIRECTIONS dir){
        return walking.get(dir);
    }

    public TextureRegion getStanding(DIRECTIONS dir){
        return standing.get(dir);
    }
}
