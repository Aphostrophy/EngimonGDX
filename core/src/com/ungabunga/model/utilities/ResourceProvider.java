package com.ungabunga.model.utilities;

import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.Skill;

import java.util.concurrent.CopyOnWriteArrayList;

public class ResourceProvider {
    CopyOnWriteArrayList<? extends Engimon> engimon;
    CopyOnWriteArrayList<Skill> skillItems;

    public ResourceProvider(){
        this.engimon = fileUtil.readEngimonCSV();
    }
}
