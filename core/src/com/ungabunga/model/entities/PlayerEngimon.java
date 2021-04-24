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
}
