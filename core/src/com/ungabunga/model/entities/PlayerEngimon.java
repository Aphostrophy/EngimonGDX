package com.ungabunga.model.entities;

import com.ungabunga.model.enums.CONSTANTS;
import com.ungabunga.model.exceptions.FeatureNotImplementedException;

/*
Player Engimon merepresentasikan Engimon yang dimiliki pemain pada Inventory
 */

public class PlayerEngimon extends Engimon{
    int remainingLives;

    public PlayerEngimon(){

    }

    public PlayerEngimon(Engimon E){
//        super(E.name, E.species, E.slogan, E.level, E.elements, E.skills, E.parentName, E.parentSpecies);
        super(E);
        this.remainingLives = CONSTANTS.PLAYERENGIMONDEFAULTLIVES;
    }

    public PlayerEngimon(PlayerEngimon PE){
        super(PE);
        this.remainingLives = PE.getRemainingLives();
    }

    public PlayerEngimon(ActiveEngimon AE){
        this.remainingLives = AE.remainingLives;
        this.id = AE.id;
        this.name = AE.name;
        this.species = AE.species;
        this.slogan = AE.slogan;
        this.elements = AE.elements;
        this.skills = AE.skills;
        this.level = AE.level;
        this.parentName = AE.parentName;
        this.parentSpecies = AE.parentSpecies;
        this.exp = AE.exp;
        this.cumulativeExp = AE.cumulativeExp;
    }

    public int getRemainingLives() {
        return remainingLives;
    }
}
