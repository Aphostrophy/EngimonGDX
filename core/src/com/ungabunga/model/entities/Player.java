package com.ungabunga.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTIONS;
import com.ungabunga.model.utilities.AnimationSet;
import com.ungabunga.model.utilities.Pair;

import static com.ungabunga.Settings.ANIM_TIMER;

public class Player {
    private ActiveEngimon activeEngimon;

    public AVATAR_STATE state;

    public String name;

    Pair<Integer,Integer> position;

    private DIRECTIONS direction;

    private float srcX,srcY;
    private float destX,destY;
    private float worldX,worldY;
    private float animTimer;

    private float stateTimer;
    private boolean moveFrameRequest;

    private AnimationSet animations;

    public Player(String name, AnimationSet animations){
        this.name = name;
        this.position = new Pair<Integer, Integer>(0,0);
        this.worldX = 0;
        this.worldY = 0;

        this.state = AVATAR_STATE.STANDING;
        this.direction = DIRECTIONS.DOWN;

        this.animTimer = 0f;
        this.stateTimer = 0f;
        this.animations = animations;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getX(){
        return this.position.getFirst();
    }

    public int getY(){
        return this.position.getSecond();
    }

    public float getWorldX(){
        return this.worldX;
    }

    public float getWorldY(){
        return this.worldY;
    }

    private void initializeMove(int dx,int dy){
        this.srcX = this.getX();
        this.srcY = this.getY();
        this.destX = this.getX() + dx;
        this.destY = this.getY() + dy;
        this.worldX = this.getX();
        this.worldY = this.getY();
        this.animTimer = 0f;
        this.state = AVATAR_STATE.WALKING;
    }

    private void finishMove(){
        this.state = AVATAR_STATE.STANDING;
        this.worldX = this.getX();
        this.worldY = this.getY();
    }

    public void update(float delta){
        if(state == AVATAR_STATE.WALKING) {
            animTimer += delta;
            stateTimer += delta;
            worldX = Interpolation.pow2.apply(this.srcX,this.destX,animTimer/ANIM_TIMER);
            worldY = Interpolation.pow2.apply(this.srcY,this.destY,animTimer/ANIM_TIMER);

            if(animTimer > ANIM_TIMER){
                stateTimer -= (animTimer - ANIM_TIMER);
                finishMove();
                if(moveFrameRequest){
                    if(direction == DIRECTIONS.UP){
                        moveUp();
                    }
                    if(direction == DIRECTIONS.DOWN){
                        moveDown();
                    }
                    if(direction == DIRECTIONS.LEFT){
                        moveLeft();
                    }
                    if(direction == DIRECTIONS.RIGHT){
                        moveRight();
                    }
                } else{
                    stateTimer = 0f;
                }
            }
        }
        moveFrameRequest = false;
    }

    private void move(int dx,int dy){
        initializeMove(dx,dy);
        this.position.setFirst(this.getX()+dx);
        this.position.setSecond(this.getY()+dy);
    }

    public void moveUp() {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTIONS.UP;
            move(0,1);
        } else{
            if(direction == DIRECTIONS.UP){
                moveFrameRequest = true;
            }
        }
    }

    public void moveDown() {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTIONS.DOWN;
            move(0,-1);
        } else{
            if(direction == DIRECTIONS.DOWN){
                moveFrameRequest = true;
            }
        }
    }

    public void moveLeft() {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTIONS.LEFT;
            move(-1,0);
        } else{
            if(direction == DIRECTIONS.LEFT){
                moveFrameRequest = true;
            }
        }
    }

    public void moveRight() {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTIONS.RIGHT;
            move(1,0);
        } else{
            if(direction == DIRECTIONS.RIGHT){
                moveFrameRequest = true;
            }
        }
    }

    public TextureRegion getSprite(){
        if(state == AVATAR_STATE.WALKING){
            return animations.getWalking(direction).getKeyFrame(stateTimer);
        }
        return animations.getStanding(direction);
    }

    public Engimon getActiveEngimon(){
        try{
            return (Engimon) this.activeEngimon;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void setActiveEngimon(PlayerEngimon PE){
        this.activeEngimon = new ActiveEngimon(PE, this);
    }

    public Pair<Integer,Integer> getPosition(){
        return this.position;
    }
}
