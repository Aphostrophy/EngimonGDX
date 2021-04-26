package com.ungabunga.model.entities;

import com.badlogic.gdx.Game;
import com.ungabunga.model.GameState;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.utilities.Pair;
import org.lwjgl.Sys;

import java.util.ArrayList;

public class Breeder {
    Breeder() {

    }
    // Mengurangi level parent
    public static void reduceLevel(Engimon A, Engimon B) {
        A.setLevel(A.getLevel() - 3);
        B.setLevel(B.getLevel() - 3);
    }
    // Mengembalikan index skill yang sama di vector
    public static int getSameSkillIdx(ArrayList<Skill> skills, Skill s) {
        int idx = 0;
        boolean found = false;
        for (int i = 0; i < skills.size(); i++)
        {
            if (!(skills.get(i) != s))
            { // if v[i] == skill
                idx = i;
                found = true;
                break;
            }
        }

        if (found)
        {
            return idx;
        }
        return -1;
    }
    // Mengembalikan index skill yang mastery level lebih kecil di vector
    public static int getLowSkillIdx(ArrayList<Skill> skills) {
        int idx = 0, lvl = skills.get(0).getMasteryLevel();
        for (int i = 1; i < skills.size(); i++)
        {
            if (lvl > skills.get(i).getMasteryLevel())
            {
                lvl = skills.get(i).getMasteryLevel();
                idx = i;
            }
        }

        return idx;
    }

    // Mengembalikan mastery level terkecil di dalam list
    public static int getLowestMastery(ArrayList<Skill> skills) {
        int lvl = skills.get(0).getMasteryLevel();
        for (int i = 1; i < skills.size(); i++)
        {
            if (lvl > skills.get(i).getMasteryLevel())
            {
                lvl = skills.get(i).getMasteryLevel();
            }
        }
        return lvl;
    }


    public static ArrayList<Pair<String,String>> getParentDetails(Engimon parentA, Engimon parentB)
    {
        ArrayList<Pair<String,String>> result = new ArrayList<>();
        Pair<String,String> parentName = new Pair<>(parentA.getName(),parentB.getName());
        Pair<String,String> parentSpecies = new Pair<>(parentA.getSpecies(),parentB.getSpecies());
        result.add(parentName);
        result.add(parentSpecies);
        return result;
    }

    public static ArrayList<Skill> getChildSkill(Engimon parentA, Engimon parentB)
    {
        ArrayList<Skill> result = new ArrayList<>();
        int idx, parentMastery;

        for (int i = 0; i < parentA.getSkills().size(); i++)
        {
            result.add(parentA.getSkills().get(i));

        }

        int k = 0;

        while (result.size() < 4 && k < parentB.getSkills().size()) {
            result.add(parentB.getSkills().get(k));
            k++;
        }


        for (int i = 0; i < parentB.getSkills().size(); i++)
        {

            idx = getSameSkillIdx(result, parentB.getSkills().get(i));
            if (idx != -1)
            {
                if (result.get(idx).getMasteryLevel() < 3) {
                    result.get(idx).addMasteryExp(101);
                }
            }
            else
            {
                parentMastery = parentB.getSkills().get(i).getMasteryLevel();
                if (parentMastery > getLowestMastery(result))
                {
                    result.set(getLowSkillIdx(result), parentB.getSkills().get(i));
                }

            }
        }
        return result;
    }

    public static Engimon BreedFinishing(Engimon ParentA, Engimon ParentB, Engimon child, Bag bag) {

        try{
            // insert to bag here
            PlayerEngimon playableChild = new PlayerEngimon(child);
            bag.insertToBag(playableChild);

            reduceLevel(ParentA, ParentB);

            return child;

        } catch(Exception e){
            System.out.println(e);
        }

        return null;
    }


    //Fire,Water,Electric,Ground,Ice,Fire/Electric, WaterGround,WaterIce
    public static Engimon Breed(Engimon ParentA, Engimon ParentB, String name, Bag bag)
    {
        if (ParentA.getElements().size() == 1) {
            if (ParentA.getElements().get(0) == IElements.GROUND)
            {
                if (ParentB.getElements().size() == 1) {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        // Ground + Water = WaterGround
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        elmt.add(IElements.GROUND);
                        Engimon child = new Engimon(name, "Psyduck", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    } else if (ParentB.getElements().get(0) == IElements.ICE) {
                        // Ground + Ice = Ice
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ICE);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    } else {
                        // Ground + Fire = Ground
                        // Ground + Electric = Ground
                        // Ground + Ground = Ground
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.GROUND);
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(1) == IElements.GROUND) {
                            // Ground + WaterGround = Ground + Ground = Ground
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        } else {
                            // Ground + WaterIce = Ground + Water = WaterGround
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, "Psyduck", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    } else {
                        // Ground + FireElectric = Ground + Fire = Ground
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.GROUND);
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                }

            }
            else if (ParentA.getElements().get(0) == IElements.FIRE)
            {
                if (ParentB.getElements().size() == 1) {
                    if (ParentB.getElements().get(0) == IElements.ELECTRIC)
                    {
                        // Fire + Electric = FireElectric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        elmt.add(IElements.ELECTRIC);
                        Engimon child = new Engimon(name, "Charmander", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else if (ParentB.getElements().get(0) == IElements.WATER)
                    {
                        // Fire + Water = Water
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    } else if (ParentB.getElements().get(0) == IElements.GROUND)
                    {
                        // Fire + Ground = Ground
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.GROUND);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    } else
                    {
                        // Fire + Ice = Fire
                        // Fire + Fire = Fire
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(1) == IElements.GROUND) {
                            // Fire + WaterGround = Fire + Ground = Ground
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, "Diglett", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        } else {
                            // Fire + WaterIce = Fire + Water = Water
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon child = new Engimon(name, "Wartotle", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    } else {
                        // Fire + FireElectric = Fire + Fire
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                }
            }
            else if (ParentA.getElements().get(0) == IElements.WATER)
            {
                if (ParentB.getElements().size() == 1) {
                    if (ParentB.getElements().get(0) == IElements.GROUND)
                    {
                        // Water + Ground = WaterGround
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        elmt.add(IElements.GROUND);
                        Engimon child = new Engimon(name, "Psyduck", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else if (ParentB.getElements().get(0) == IElements.ICE)
                    {
                        // Water + Ice = WaterIce
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        elmt.add(IElements.ICE);
                        Engimon child = new Engimon(name, "Poliwag", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else if (ParentB.getElements().get(0) == IElements.ELECTRIC)
                    {
                        // Water + Electric = Electric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ELECTRIC);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else {
                        // Water + Fire = Water
                        // Water + Water = Water
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(1) == IElements.GROUND) {
                            // Water + WaterGround = Water + Ground = WaterGround
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, "Psyduck", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        } else {
                            // Water + WaterIce = Water + Water = Water
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    } else {
                        // Water + FireElectric = Water + Fire = Water
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                }
            }
            else if (ParentA.getElements().get(0) == IElements.ELECTRIC)
            {
                System.out.println(ParentB.displayInfoToString());
                if (ParentB.getElements().size() < 2) {
                    if (ParentB.getElements().get(0) == IElements.WATER)
                    {
                        // Electric + Water = Water
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else if (ParentB.getElements().get(0) == IElements.GROUND)
                    {
                        // Electric + Ground = Ground
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.GROUND);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    } else if (ParentB.getElements().get(0) == IElements.FIRE)
                    {
                        // Electric + Fire = FireElectric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        elmt.add(IElements.ELECTRIC);
                        Engimon child = new Engimon(name, "Charmander", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else
                    {
                        // Electric + Water = Electric
                        // Electric + Electric = Electric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ELECTRIC);
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(1) ==  IElements.GROUND) {
                            // Electric + Ground = Ground
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, "Diglett", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        } else {
                            // Electric + WaterIce = Electric + Water = Electric
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.ELECTRIC);
                            Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    } else {
                        // Electric + FireElectric = FireElectric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        elmt.add(IElements.ELECTRIC);
                        Engimon child = new Engimon(name, "Charmander", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                }

            }
            else if (ParentA.getElements().get(0) == IElements.ICE)
            {
                if (ParentB.getElements().size() == 1) {
                    if (ParentB.getElements().get(0) == IElements.WATER)
                    {
                        // Ice + Water = WaterIce
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        elmt.add(IElements.ICE);
                        Engimon child = new Engimon(name, "Poliwag", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else if (ParentB.getElements().get(0) == IElements.FIRE)
                    {
                        // Ice + Fire = Fire
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else if (ParentB.getElements().get(0) == IElements.ELECTRIC)
                    {
                        // Ice + Electric = Electric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ELECTRIC);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    } else {
                        // Ice + Ground = Ice
                        // Ice + Ice = Ice
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ICE);
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(0) == IElements.GROUND) {
                            // Ice + WaterGround = Ice + Ground = Ice
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.ICE);
                            Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        } else {
                            // Ice + WaterIce = Ice + Water = WaterIce
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon newParentB = new Engimon(ParentB.getName(), ParentB.getSpecies(), ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                            return Breed(ParentA, newParentB, name, bag);
                        }
                    } else {
                        // Ice + FireElectric = Ice + Fire = Fire
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon child = new Engimon(name, "Ifrit", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                }

            }
        } else {
            if (ParentA.getElements().get(0) == IElements.WATER) {
                if (ParentA.getElements().get(1) == IElements.GROUND) {
                    if (ParentB.getElements().size() == 1) {
                        if (ParentB.getElements().get(0) == IElements.WATER) {
                            // Ground + Water = WaterGround
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, "Psyduck", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        } else if (ParentB.getElements().get(0) == IElements.ICE) {
                            // Ground + Ice = Ice
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.ICE);
                            Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        } else {
                            // Ground + Fire = Ground
                            // Ground + Electric = Ground
                            // Ground + Ground = Ground
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, "Diglett", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    } else {
                        if (ParentB.getElements().get(0) == IElements.WATER) {
                            if (ParentB.getElements().get(1) == IElements.GROUND) {
                                // Ground + WaterGround = Ground + Ground = Ground
                                ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                                ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                                ArrayList<IElements> elmt = new ArrayList<>();
                                elmt.add(IElements.GROUND);
                                Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                                return BreedFinishing(ParentA, ParentB, child, bag);
                            } else {
                                // Ground + WaterIce = Ground + Water = WaterGround
                                ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                                ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                                ArrayList<IElements> elmt = new ArrayList<>();
                                elmt.add(IElements.WATER);
                                elmt.add(IElements.GROUND);
                                Engimon child = new Engimon(name, "Psyduck", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                                return BreedFinishing(ParentA, ParentB, child, bag);
                            }
                        } else {
                            // Ground + FireElectric = Ground + Fire = Ground
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    }
                } else {
                    // WaterIce engimon jadi Water engimon
                    if (ParentB.getElements().size() == 1) {
                        if (ParentB.getElements().get(0) == IElements.GROUND)
                        {
                            // Water + Ground = WaterGround
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, "Psyduck", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                        else if (ParentB.getElements().get(0) == IElements.ICE)
                        {
                            // Water + Ice = WaterIce
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            elmt.add(IElements.ICE);
                            Engimon child = new Engimon(name, "Poliwag", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                        else if (ParentB.getElements().get(0) == IElements.ELECTRIC)
                        {
                            // Water + Electric = Electric
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.ELECTRIC);
                            Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                        else {
                            // Water + Fire = Water
                            // Water + Water = Water
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    } else {
                        if (ParentB.getElements().get(0) == IElements.WATER) {
                            if (ParentB.getElements().get(1) == IElements.GROUND) {
                                // Water + WaterGround = Water + Ground = WaterGround
                                ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                                ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                                ArrayList<IElements> elmt = new ArrayList<>();
                                elmt.add(IElements.WATER);
                                elmt.add(IElements.GROUND);
                                Engimon child = new Engimon(name, "Psyduck", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                                return BreedFinishing(ParentA, ParentB, child, bag);
                            } else {
                                // Water + WaterIce = Water + Water = Water
                                ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                                ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                                ArrayList<IElements> elmt = new ArrayList<>();
                                elmt.add(IElements.WATER);
                                Engimon child = new Engimon(name, "Wartotle", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                                return BreedFinishing(ParentA, ParentB, child, bag);
                            }
                        } else {
                            // Water + FireElectric = Water + Fire = Water
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon child = new Engimon(name, "Wartotle", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    }
                }
            } else {
                // FireElectric engimon jadi Fire engimon
                if (ParentB.getElements().size() == 1) {
                    if (ParentB.getElements().get(0) == IElements.ELECTRIC)
                    {
                        // Fire + Electric = FireElectric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        elmt.add(IElements.ELECTRIC);
                        Engimon child = new Engimon(name, "Charmander", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                    else if (ParentB.getElements().get(0) == IElements.WATER)
                    {
                        // Fire + Water = Water
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    } else if (ParentB.getElements().get(0) == IElements.GROUND)
                    {
                        // Fire + Ground = Ground
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.GROUND);
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    } else
                    {
                        // Fire + Ice = Fire
                        // Fire + Fire = Fire
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon child = new Engimon(name, "Ifrit", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(1) == IElements.GROUND) {
                            // Fire + WaterGround = Fire + Ground = Ground
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon child = new Engimon(name, "Diglett", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        } else {
                            // Fire + WaterIce = Fire + Water = Water
                            ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                            ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon child = new Engimon(name, "Wartotle", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                            return BreedFinishing(ParentA, ParentB, child, bag);
                        }
                    } else {
                        // Fire + FireElectric = Fire + Fire
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon child = new Engimon(name, "Ifrit", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(0), parentDetails.get(1));
                        return BreedFinishing(ParentA, ParentB, child, bag);
                    }
                }
            }
        }

        return null;
    }

}
