package com.ungabunga.model.utilities;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.Skill;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.enums.IElements;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResourceProvider {
    CopyOnWriteArrayList<? extends Engimon> engimon;
    CopyOnWriteArrayList<Skill> skills;

    HashMap<IElements, CellType> mapElementToBiome;

    public ResourceProvider(){
        this.skills = fileUtil.readSkillCSV();
        this.engimon = fileUtil.readEngimonCSV(this);

        HashMap<IElements, CellType> elementBiomeMap = new HashMap<>();
        elementBiomeMap.put(IElements.FIRE,CellType.MOUNTAIN);
        elementBiomeMap.put(IElements.WATER,CellType.SEA);
        elementBiomeMap.put(IElements.GROUND,CellType.GRASSLAND);
        elementBiomeMap.put(IElements.ELECTRIC,CellType.GRASSLAND);
        elementBiomeMap.put(IElements.ICE,CellType.TUNDRA);
        this.mapElementToBiome = elementBiomeMap;
    }

    public Skill getSkill(String name){
        for(Skill skill : this.skills){
            if(skill.getSkillName().equals(name)){
                return skill;
            }
        }
        return null;
    }

    public Engimon randomizeEngimon(CellType biomes){
        ArrayList<Engimon> candidates = new ArrayList<>();
        for(Engimon engimon: this.engimon){
            for(IElements engimonElement: engimon.getElements()){
                if(mapElementToBiome.get(engimonElement).equals(biomes)){
                    candidates.add(engimon);
                }
            }
        }
        if(candidates.size()==0){
            return null;
        }
        int x = ThreadLocalRandom.current().nextInt(0,candidates.size());
        return candidates.get(x);
    }
}
