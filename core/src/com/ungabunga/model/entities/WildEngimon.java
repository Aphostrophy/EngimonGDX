package com.ungabunga.model.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ungabunga.model.GameState;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.exceptions.EngimonConflictException;
import com.ungabunga.model.exceptions.FeatureNotImplementedException;
import com.ungabunga.model.enums.CONSTANTS;
import com.ungabunga.model.utilities.Pair;
import com.ungabunga.model.utilities.ResourceProvider;

import java.util.concurrent.ThreadLocalRandom;

public class WildEngimon extends Engimon implements LivingEngimon {
    Pair<Integer,Integer> position;

    ResourceProvider resourceProvider;
    GameState gameState;

    int remainingLives;

    public DIRECTION direction;
    AVATAR_STATE state;

    private float srcX,srcY;
    private float destX,destY;
    private float worldX,worldY;
    private float animTimer;

    private float stateTimer;
    public boolean isInBattle = false;
    public WildEngimon(){

    }

    public WildEngimon(Engimon E, int spawnX, int spawnY, ResourceProvider resourceProvider, GameState gameState){
//        super(E.name, E.species, E.slogan, E.level, E.elements, E.skills, E.parentName, E.parentSpecies);
        super(E);
        this.remainingLives = CONSTANTS.WILDENGIMONDEFAULTLIVES;

        this.direction = DIRECTION.DOWN;
        this.state = AVATAR_STATE.STANDING;

        this.resourceProvider = resourceProvider;
        this.gameState = gameState;

        Pair<Integer,Integer> position = new Pair<>();
        position.setFirst(spawnX);
        position.setSecond(spawnY);

        this.position = position;

        this.worldX =  this.position.getFirst();
        this.worldY = this.position.getSecond();
    }

//    private void initializeMove(int dx,int dy){
//        this.srcX = this.getX();
//        this.srcY = this.getY();
//        this.destX = this.getX() + dx;
//        this.destY = this.getY() + dy;
//        this.worldX = this.getX();
//        this.worldY = this.getY();
//        this.animTimer = 0f;
//        this.state = AVATAR_STATE.WALKING;
//    }

    // I.S. Movement bisa dilakukan dengan valid
    // F.S Engimon langsung berpindah ke tujuan secara memori namun secara visual seolah-olah berjalan
    @Override
    public void move(int dx, int dy){
//        initializeMove(dx,dy);
        gameState.map.get(this.getY()).get(this.getX()).occupier=null;
        this.position.setFirst(this.getX()+dx);
        this.position.setSecond(this.getY()+dy);
        gameState.map.get(this.getY()).get(this.getX()).occupier=this;

        finishMove();
    }

    public void finishMove(){
        this.state = AVATAR_STATE.STANDING;
        this.worldX = this.position.getFirst();
        this.worldY = this.position.getSecond();
    }

    public void moveUp() throws EngimonConflictException {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.UP;
            if(this.getY()+1>=gameState.map.length() || gameState.map.get(this.getY()+1).get(this.getX()).occupier!=null || gameState.map.get(this.getY()+1).get(this.getX()).cellType == CellType.BLOCKED ||  (this.getX()==gameState.player.getX() && this.getY()+1==gameState.player.getY()) || !resourceProvider.isCompatible(this.elements,gameState.map.get(this.getY()+1).get(this.getX()).cellType)){
                repositionOnCellConflict();
            }
            move(0,1);
        }
    }

    public void moveDown() throws EngimonConflictException {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.DOWN;
            if(this.getY()-1<0 || gameState.map.get(this.getY()-1).get(this.getX()).occupier!=null || gameState.map.get(this.getY()-1).get(this.getX()).cellType== CellType.BLOCKED ||  (this.getX()==gameState.player.getX() && this.getY()-1==gameState.player.getY()) || !resourceProvider.isCompatible(this.elements,gameState.map.get(this.getY()-1).get(this.getX()).cellType)){
                repositionOnCellConflict();
            }
            move(0,-1);
        }
    }

    public void moveLeft() throws EngimonConflictException {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.LEFT;
            if(this.getX()-1<0 || gameState.map.get(this.getY()).get(this.getX()-1).occupier!=null || gameState.map.get(this.getY()).get(this.getX()-1).cellType==CellType.BLOCKED || (this.getX()-1==gameState.player.getX() && this.getY()==gameState.player.getY()) || !resourceProvider.isCompatible(this.elements,gameState.map.get(this.getY()).get(this.getX()-1).cellType)){
                repositionOnCellConflict();
            }
            move(-1,0);
        }
    }

    public void moveRight() throws EngimonConflictException {
        if(state == AVATAR_STATE.STANDING){
            direction = DIRECTION.RIGHT;
            if(this.getX()+1 >= gameState.map.get(0).length() || gameState.map.get(this.getY()).get(this.getX()+1).occupier!=null || gameState.map.get(this.getY()).get(this.getX()+1).cellType == CellType.BLOCKED ||  (this.getX()+1==gameState.player.getX() && this.getY()==gameState.player.getY()) || !resourceProvider.isCompatible(this.elements,gameState.map.get(this.getY()).get(this.getX()+1).cellType)){
                repositionOnCellConflict();
            }
            move(1,0);
        }
    }

    public synchronized void randomizeMove(){
        int x = ThreadLocalRandom.current().nextInt(0,4);
        if(x==0){
            try{
                moveUp();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(x==1){
            try{
                moveRight();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(x==2){
            try{
                moveDown();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(x==3){
            try{
                moveLeft();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void repositionOnCellConflict() throws EngimonConflictException {
        throw new EngimonConflictException("Abort wildEngimonMovement");
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
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
    public TextureRegion getSpriteAura() {
        return resourceProvider.getSpriteAura(this);
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

    public int getX(){
        return this.position.getFirst();
    }

    public int getY(){
        return this.position.getSecond();
    }

    public int getLevel(){
        return this.level;
    }

    public void killEngimon(){
        gameState.map.get(this.getY()).get(this.getX()).occupier=null;
        reduceLives();
    }
}
