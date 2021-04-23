package com.ungabunga.model.utilities;

import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.Skill;

import java.util.concurrent.CopyOnWriteArrayList;

public class ResourceProvider {
    CopyOnWriteArrayList<? extends Engimon> engimon;
    CopyOnWriteArrayList<Skill> skills;

    public ResourceProvider(){
        this.skills = fileUtil.readSkillCSV();
        this.engimon = fileUtil.readEngimonCSV(this);
    }

    public Skill getSkill(String name){
        for(Skill skill : this.skills){
            if(skill.getSkillName().equals(name)){
                return skill;
            }
        }
        return null;
    }
}
