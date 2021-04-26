package com.ungabunga.model.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.ungabunga.model.GameState;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.exceptions.EngimonConflictException;
import com.ungabunga.model.save.Save;
import com.ungabunga.model.utilities.AnimationSet;
import com.ungabunga.model.utilities.Pair;

import static com.ungabunga.Settings.ANIM_TIMER;
import static com.ungabunga.Settings.RUN_ANIM_TIMER;

public class Player {
    private ActiveEngimon activeEngimon;

    public AVATAR_STATE state;
    public boolean isRunning;

    public String name;

    Pair<Integer,Integer> position;

    private DIRECTION direction;

    private float srcX,srcY;
    private float destX,destY;
    private float worldX,worldY;
    private float animTimer;

    private float stateTimer;
    private boolean moveFrameRequest;

    private AnimationSet animations;

    public Player(String name, AnimationSet animations,int x,int y){
        this.name = name;
        this.position = new Pair<>(x, y);
        this.worldX = x;
        this.worldY = y;

        this.state = AVATAR_STATE.STANDING;
        this.direction = DIRECTION.DOWN;

        this.animTimer = 0f;
        this.stateTimer = 0f;
        this.animations = animations;
    }

    public void loadSave(Save save, GameState gameState){
        this.name = save.playerName;
        this.position.setFirst(save.playerPosX);
        this.position.setSecond(save.playerPosY);
        this.worldX = save.playerPosX;
        this.worldY = save.playerPosY;

        this.activeEngimon = new ActiveEngimon(save.engimon,this, save.engimonX, save.engimonY,gameState,gameState.app.getResourceProvider());
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

    public void update(float delta) throws EngimonConflictException {
        if(activeEngimon!=null){
            activeEngimon.update(delta);
        }
        float anime_time = 0f;
        animTimer += delta;
        stateTimer += delta;

        if(state == AVATAR_STATE.WALKING && !isRunning) {
            anime_time = ANIM_TIMER;
        } else if (state == AVATAR_STATE.WALKING && isRunning) {
            anime_time = RUN_ANIM_TIMER;
        }
        worldX = Interpolation.pow2.apply(this.srcX,this.destX,animTimer/anime_time);
        worldY = Interpolation.pow2.apply(this.srcY,this.destY,animTimer/anime_time);
        if(animTimer > anime_time){
            stateTimer -= (animTimer - anime_time);
            finishMove();
            if(moveFrameRequest){
                if(direction == DIRECTION.UP){
                    moveUp();
                }
                if(direction == DIRECTION.DOWN){
                    moveDown();
                }
                if(direction == DIRECTION.LEFT){
                    moveLeft();
                }
                if(direction == DIRECTION.RIGHT){
                    moveRight();
                }
            } else{
                this.isRunning = false;
                stateTimer = 0f;
            }
        }

        moveFrameRequest = false;
    }

    private void move(int dx,int dy) throws EngimonConflictException {
        initializeMove(dx,dy);
        if(activeEngimon!=null){
            activeEngimon.followPlayer();
        }

        this.position.setFirst(this.getX()+dx);
        this.position.setSecond(this.getY()+dy);
    }

    public void moveUp() throws EngimonConflictException {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.UP;
            move(0,1);
        } else{
            if(direction == DIRECTION.UP){
                moveFrameRequest = true;
            }
        }
    }

    public void moveDown() throws EngimonConflictException {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.DOWN;
            move(0,-1);
        } else{
            if(direction == DIRECTION.DOWN){
                moveFrameRequest = true;
            }
        }
    }

    public void moveLeft() throws EngimonConflictException {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.LEFT;
            move(-1,0);
        } else{
            if(direction == DIRECTION.LEFT){
                moveFrameRequest = true;
            }
        }
    }

    public void moveRight() throws EngimonConflictException {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.RIGHT;
            move(1,0);
        } else{
            if(direction == DIRECTION.RIGHT){
                moveFrameRequest = true;
            }
        }
    }

    public TextureRegion getSprite(){
        if(state == AVATAR_STATE.WALKING && !isRunning){
            return animations.getWalking(direction).getKeyFrame(stateTimer);
        } else if (state == AVATAR_STATE.WALKING && isRunning) {
            return animations.getRunning(direction).getKeyFrame(stateTimer);
        }
        return animations.getStanding(direction);
    }

    public ActiveEngimon getActiveEngimon(){
        try{
            return this.activeEngimon;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void setActiveEngimon(ActiveEngimon AE){
        this.activeEngimon = AE;
    }

    public void removeActiveEngimon(){
        this.activeEngimon = null;
    }

    public Pair<Integer,Integer> getPosition(){
        return this.position;
    }

    public DIRECTION getDirection(){
        return this.direction;
    }

    public AVATAR_STATE getState(){
        return this.state;
    }

    public boolean getMoveFrameRequest(){
        return this.moveFrameRequest;
    }

    public void setPosition(int x,int y){
        this.position.setFirst(x);
        this.position.setSecond(y);
    }

    public void setDirection(DIRECTION direction){
        this.direction = direction;
    }

}