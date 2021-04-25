package com.ungabunga.model.entities;

import com.badlogic.gdx.math.Interpolation;
import com.ungabunga.model.GameState;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.utilities.Pair;

import static com.ungabunga.Settings.ANIM_TIMER;

/*
Active Engimon merepresentasikan Player Engimon yang sedang aktif merupakan turunan dari PlayerEngimon
yang memiliki posisi
 */

public class ActiveEngimon extends PlayerEngimon implements LivingEngimon{
    Pair<Integer,Integer> position;
    DIRECTION direction;
    AVATAR_STATE state;

    Player player;
    GameState gameState;

    private float srcX,srcY;
    private float destX,destY;
    private float worldX,worldY;
    private float animTimer;

    private float stateTimer;

    public ActiveEngimon(){

    }

    public ActiveEngimon(PlayerEngimon PE, Player P, int x, int y, GameState gameState){
        super(PE);
        this.position = new Pair<Integer,Integer>(x,y);
        this.direction = P.getDirection();
        this.state = P.getState();

        this.player = P;
        this.gameState = gameState;

        this.worldX = x;
        this.worldY = y;

        this.animTimer = 0f;
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
                if(player.getMoveFrameRequest()){
                    followPlayer(player.getX(),player.getY());
                } else{
                    stateTimer = 0f;
                }
            }
        }
    }

    // I.S. Movement bisa dilakukan dengan valid
    // F.S Engimon langsung berpindah ke tujuan secara memori namun secara visual seolah-olah berjalan
    public void move(int dx, int dy){
        initializeMove(dx,dy);
        gameState.map.get(this.getY()).get(this.getX()).occupier=null;
        this.position.setFirst(this.getX()+dx);
        this.position.setSecond(this.getY()+dy);
        gameState.map.get(this.getY()).get(this.getX()).occupier=this;
    }

    public void moveUp() {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.UP;
            move(0,1);
        }
    }

    public void moveDown() {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.DOWN;
            move(0,-1);
        }
    }

    public void moveLeft() {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.LEFT;
            move(-1,0);
        }
    }

    public void moveRight() {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.RIGHT;
            move(1,0);
        }
    }

    public boolean followPlayer(int playerLastX, int playerLastY){
        if(this.getX()<this.player.getWorldX()){
            moveRight();
            return true;
        }
        if(this.getX()>this.player.getWorldX()){
            moveLeft();
            return true;
        }
        if(this.getY()<this.player.getWorldY()){
            moveUp();
            return true;
        }
        if(this.getY()>this.player.getWorldY()){
            moveDown();
            return true;
        }
        return false;
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

    public float getWorldX() {
        return worldX;
    }

    public float getWorldY() {
        return worldY;
    }

    @Override
    public void reduceLives() {
        this.remainingLives--;
    }

    @Override
    public DIRECTION getDirection() {
        return this.direction;
    }

    @Override
    public AVATAR_STATE getState(){return this.state;}

    @Override
    public String getEngimonSpecies(){return this.getSpecies();}
    
    public int getX(){
        return this.position.getFirst();
    }

    public int getY(){
        return this.position.getSecond();
    }
}
