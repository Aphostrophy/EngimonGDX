package com.ungabunga.model.utilities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ungabunga.model.enums.DIRECTION;

import java.util.HashMap;

public class AnimationSet {

    private HashMap<DIRECTION, Animation<TextureRegion>> walking;
    private HashMap<DIRECTION, TextureRegion> standing;

    public AnimationSet(Animation walkDown, Animation walkUp, Animation walkLeft, Animation walkRight, TextureRegion standDown, TextureRegion standUp, TextureRegion standLeft, TextureRegion standRight){
        walking = new HashMap<DIRECTION,Animation<TextureRegion>>();
        walking.put(DIRECTION.UP,walkUp);
        walking.put(DIRECTION.DOWN,walkDown);
        walking.put(DIRECTION.LEFT,walkLeft);
        walking.put(DIRECTION.RIGHT,walkRight);

        standing = new HashMap<DIRECTION, TextureRegion>();
        standing.put(DIRECTION.UP,standUp);
        standing.put(DIRECTION.DOWN, standDown);
        standing.put(DIRECTION.LEFT, standLeft);
        standing.put(DIRECTION.RIGHT, standRight);
    }


    public Animation<TextureRegion> getWalking(DIRECTION dir){
        return walking.get(dir);
    }

    public TextureRegion getStanding(DIRECTION dir){
        return standing.get(dir);
    }
}
