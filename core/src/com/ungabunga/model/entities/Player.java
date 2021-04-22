package com.ungabunga.model.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.ungabunga.model.utilities.Pair;

public class Player {

    public Texture avatar;

    public AVATAR_STATE state;

    public String name;

    Pair<Integer,Integer> position;

    private ActiveEngimon activeEngimon;

    private float srcX,srcY;
    private float destX,destY;
    private float worldX,worldY;
    private float animTimer;
    private float ANIM_TIMER = 0.5f;

    public enum AVATAR_STATE{
        WALKING,
        STANDING,
        SWIMMING,
        HIKING,
        SLIDING,
    }

    public Player(String name){
        this.name = name;
        this.position = new Pair<Integer, Integer>(0,0);
        this.worldX = 0;
        this.worldY = 0;
        this.avatar = new Texture("Avatar/brendan_stand_south.png");

        this.state = AVATAR_STATE.STANDING;
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
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public void update(float delta){
        if(state == AVATAR_STATE.WALKING) {
            this.animTimer += delta;
            this.worldX = Interpolation.pow2.apply(this.srcX,this.destX,animTimer/ANIM_TIMER);
            this.worldY = Interpolation.pow2.apply(this.srcY,this.destY,animTimer/ANIM_TIMER);

            if(animTimer > ANIM_TIMER){
                finishMove();
            }
        }
    }

    private void move(int dx,int dy){
        initializeMove(dx,dy);
        this.position.setFirst(this.getX()+dx);
        this.position.setSecond(this.getY()+dy);
    }

    public void moveUp() {
        if(state == AVATAR_STATE.STANDING){
            move(0,1);
        }
    }

    public void moveDown() {
        if(state == AVATAR_STATE.STANDING){
            move(0,-1);
        }
    }

    public void moveLeft() {
        if(state == AVATAR_STATE.STANDING){
            move(-1,0);
        }
    }

    public void moveRight() {
        if(state == AVATAR_STATE.STANDING){
            move(1,0);
        }
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
