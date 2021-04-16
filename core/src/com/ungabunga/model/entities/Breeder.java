package com.ungabunga.model.entities;

import com.ungabunga.model.GameState;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.utilities.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Breeder {
    Breeder() {}

    // Memerima input nama engimon dari user
    public static String childName() {
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Kamu mendapatkan Engimon baru!");
        System.out.print("Masukkan nama Engimon: ");
        String nama = scan.nextLine();
        scan.close();
        return nama;
    }
    // Mengurangi level parent
    public static void reduceLevel(Engimon A, Engimon B) {
        A.setLevel(A.getLevel() - 30);
        B.setLevel(B.getLevel() - 30);
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


    public ArrayList<Pair<String,String>> getParentDetails(Engimon parentA, Engimon parentB)
    {
        ArrayList<Pair<String,String>> result = new ArrayList<>();
        Pair<String,String> parentName = new Pair<>(parentA.getName(),parentB.getName());
        Pair<String,String> parentSpecies = new Pair<>(parentA.getSpecies(),parentB.getSpecies());
        result.add(parentName);
        result.add(parentSpecies);
        return result;
    }

    ArrayList<Skill> getChildSkill(Engimon parentA, Engimon parentB)
    {
        ArrayList<Skill> result = new ArrayList<>();
        int idx, parentMastery;

        for (int i = 0; i < parentA.getSkills().size(); i++)
        {
            result.add(parentA.getSkills().get(i));

        }

        for (int i = 0; i < parentB.getSkills().size(); i++)
        {

            idx = getSameSkillIdx(result, parentB.getSkills().get(i));
            if (idx != -1)
            {
                result.get(idx).addMasteryExp(51);
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

    public void BreedFinishing(Engimon ParentA, Engimon ParentB, Engimon child, GameState gameState) {
        // cuma buat mastiin childnya kebreed
        System.out.println("Nama: " + child.getName());
        System.out.println("Lvl. " + child.getLevel());
        System.out.println("Species: " + child.getSpecies());
        System.out.println("Parent:");
        System.out.println("- " + child.getParentName().getFirst() + "(" + child.getParentSpecies().getSecond() + ")");
        System.out.println("- " + child.getParentName().getFirst() + "(" + child.getParentSpecies().getSecond() + ")");
        System.out.println("Skills: ");
        for (Skill s : child.getSkills()) {
            System.out.println("- " + s.getSkillName());
        }
        // insert to inventory here
        reduceLevel(ParentA, ParentB);
    }


    //Fire,Water,Electric,Ground,Ice,Fire/Electric, WaterGround,WaterIce
    public void Breed(Engimon ParentA, Engimon ParentB, GameState gameState)
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
                        String name = childName();
                        Engimon child = new Engimon(name, "Psyduck", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    } else if (ParentB.getElements().get(0) == IElements.ICE) {
                        // Ground + Ice = Ice
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ICE);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    } else {
                        // Ground + Fire = Ground
                        // Ground + Electric = Ground
                        // Ground + Ground = Ground
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.GROUND);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(1) == IElements.GROUND) {
                            // Ground + WaterGround = Ground + Ground
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon newParentB = new Engimon(ParentA.getName(), "Diglett", ParentA.getSlogan(), ParentA.getLevel(), elmt, ParentA.getSkills(), ParentA.getParentName(), ParentA.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        } else {
                            // Ground + WaterIce = Ground + Water
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon newParentB = new Engimon(ParentB.getName(), "Wartotle", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        }
                    } else {
                        // Ground + FireElectric = Ground + Fire
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon newParentB = new Engimon(ParentB.getName(), "Ifrit", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                        Breed(ParentA, newParentB, gameState);
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
                        String name = childName();
                        Engimon child = new Engimon(name, "Charmander", ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                    else if (ParentB.getElements().get(0) == IElements.WATER)
                    {
                        // Fire + Water = Water
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    } else if (ParentB.getElements().get(0) == IElements.GROUND)
                    {
                        // Fire + Ground = Ground
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.GROUND);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    } else
                    {
                        // Fire + Ice = Fire
                        // Fire + Fire = Fire
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(1) == IElements.GROUND) {
                            // Fire + WaterGround = Fire + Ground
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon newParentB = new Engimon(ParentA.getName(), "Diglett", ParentA.getSlogan(), ParentA.getLevel(), elmt, ParentA.getSkills(), ParentA.getParentName(), ParentA.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        } else {
                            // Fire + WaterIce = Fire + Water
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon newParentB = new Engimon(ParentB.getName(), "Wartotle", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        }
                    } else {
                        // Fire + FireElectric = Fire + Fire
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon newParentB = new Engimon(ParentB.getName(), "Ifrit", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                        Breed(ParentA, newParentB, gameState);
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
                        String name = childName();
                        Engimon child = new Engimon(name, "Psyduck", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                    else if (ParentB.getElements().get(0) == IElements.ICE)
                    {
                        // Water + Ice = WaterIce
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        elmt.add(IElements.ICE);
                        String name = childName();
                        Engimon child = new Engimon(name, "Poliwag", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                    else if (ParentB.getElements().get(0) == IElements.ELECTRIC)
                    {
                        // Water + Electric = Electric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ELECTRIC);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                    else {
                        // Water + Fire = Water
                        // Water + Water = Water
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(1) == IElements.GROUND) {
                            // Water + WaterGround = Water + Ground
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon newParentB = new Engimon(ParentA.getName(), "Diglett", ParentA.getSlogan(), ParentA.getLevel(), elmt, ParentA.getSkills(), ParentA.getParentName(), ParentA.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        } else {
                            // Water + WaterIce = Water + Water
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon newParentB = new Engimon(ParentB.getName(), "Wartotle", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        }
                    } else {
                        // Water + FireElectric = Water + Fire
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon newParentB = new Engimon(ParentB.getName(), "Ifrit", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                        Breed(ParentA, newParentB, gameState);
                    }
                }
            }
            else if (ParentA.getElements().get(0) == IElements.ELECTRIC)
            {
                if (ParentB.getElements().size() == 1) {
                    if (ParentB.getElements().get(0) == IElements.WATER)
                    {
                        // Electric + Water = Water
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.WATER);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                    else if (ParentB.getElements().get(0) == IElements.GROUND)
                    {
                        // Electric + Ground = Ground
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.GROUND);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    } else if (ParentB.getElements().get(0) == IElements.FIRE)
                    {
                        // Electric + Fire = FireElectric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        elmt.add(IElements.ELECTRIC);
                        String name = childName();
                        Engimon child = new Engimon(name, "Charmander", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                    else
                    {
                        // Electric + Water = Electric
                        // Electric + Electric = Electric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ELECTRIC);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(0) ==  IElements.GROUND) {
                            // Electric + WaterGround = Electric + Ground
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon newParentB = new Engimon(ParentA.getName(), "Diglett", ParentA.getSlogan(), ParentA.getLevel(), elmt, ParentA.getSkills(), ParentA.getParentName(), ParentA.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        } else {
                            // Electric + WaterIce = Electric + Water
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon newParentB = new Engimon(ParentB.getName(), "Wartotle", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        }
                    } else {
                        // Electric + FireElectric = Fire
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon newParentB = new Engimon(ParentB.getName(), "Ifrit", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                        Breed(ParentA, newParentB, gameState);
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
                        String name = childName();
                        Engimon child = new Engimon(name, "Poliwag", ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                    else if (ParentB.getElements().get(0) == IElements.FIRE)
                    {
                        // Ice + Fire = Fire
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                    else if (ParentB.getElements().get(0) == IElements.ELECTRIC)
                    {
                        // Ice + Electric = Electric
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ELECTRIC);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentB.getSpecies(), ParentB.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    } else {
                        // Ice + Ground = Ice
                        // Ice + Ice = Ice
                        ArrayList<Skill> skill = getChildSkill(ParentA, ParentB);
                        ArrayList<Pair<String,String>> parentDetails = getParentDetails(ParentA,ParentB);
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.ICE);
                        String name = childName();
                        Engimon child = new Engimon(name, ParentA.getSpecies(), ParentA.getSlogan(), 1, elmt, skill, parentDetails.get(1), parentDetails.get(2));
                        BreedFinishing(ParentA, ParentB, child, gameState);
                    }
                } else {
                    if (ParentB.getElements().get(0) == IElements.WATER) {
                        if (ParentB.getElements().get(0) == IElements.GROUND) {
                            // Ice + WaterGround = Ice + Ground
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.GROUND);
                            Engimon newParentB = new Engimon(ParentA.getName(), "Diglett", ParentA.getSlogan(), ParentA.getLevel(), elmt, ParentA.getSkills(), ParentA.getParentName(), ParentA.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        } else {
                            // Ice + WaterIce = Ice + Water
                            ArrayList<IElements> elmt = new ArrayList<>();
                            elmt.add(IElements.WATER);
                            Engimon newParentB = new Engimon(ParentB.getName(), "Wartotle", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                            Breed(ParentA, newParentB, gameState);
                        }
                    } else {
                        // Ice + FireElectric = Ice + Fire
                        ArrayList<IElements> elmt = new ArrayList<>();
                        elmt.add(IElements.FIRE);
                        Engimon newParentB = new Engimon(ParentB.getName(), "Ifrit", ParentB.getSlogan(), ParentB.getLevel(), elmt, ParentB.getSkills(), ParentB.getParentName(), ParentB.getParentSpecies());
                        Breed(ParentA, newParentB, gameState);
                    }
                }

            }
        } else {
            if (ParentA.getElements().get(0) == IElements.WATER) {
                if (ParentA.getElements().get(1) == IElements.GROUND) {
                    // WaterGround engimon jadi Ground engimon
                    ArrayList<IElements> elmt = new ArrayList<>();
                    elmt.add(IElements.GROUND);
                    Engimon newParentA = new Engimon(ParentA.getName(), "Diglett", ParentA.getSlogan(), ParentA.getLevel(), elmt, ParentA.getSkills(), ParentA.getParentName(), ParentA.getParentSpecies());
                    Breed(newParentA, ParentB, gameState);
                } else {
                    // WaterIce engimon jadi Water engimon
                    ArrayList<IElements> elmt = new ArrayList<>();
                    elmt.add(IElements.WATER);
                    Engimon newParentA = new Engimon(ParentA.getName(), "Wartotle", ParentA.getSlogan(), ParentA.getLevel(), elmt, ParentA.getSkills(), ParentA.getParentName(), ParentA.getParentSpecies());
                    Breed(newParentA, ParentB, gameState);
                }
            } else {
                // FireElectric engimon jadi Fire engimon
                ArrayList<IElements> elmt = new ArrayList<>();
                elmt.add(IElements.FIRE);
                Engimon newParentA = new Engimon(ParentA.getName(), "Ifrit", ParentA.getSlogan(), ParentA.getLevel(), elmt, ParentA.getSkills(), ParentA.getParentName(), ParentA.getParentSpecies());
                Breed(newParentA, ParentB, gameState);
            }
        }
    }

}
