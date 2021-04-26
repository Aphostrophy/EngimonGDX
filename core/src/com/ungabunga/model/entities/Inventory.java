package com.ungabunga.model.entities;


import java.util.*;

import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.*;
import com.ungabunga.model.enums.CONSTANTS;

import java.lang.Integer;


public class Inventory<T> {
    ArrayList<T> items;
    Integer capacity;
    Integer neff;

    public Inventory(){
        this.capacity = CONSTANTS.INVENTORYCAPACITY;
        this.neff = 0;
        this.items = new ArrayList<T>();
    }
    public int getFilledSlot(){
        return this.neff;
    }
    public int getMaxCapacity(){
        return this.capacity;
    }

    public int getItemIndex(T item){
        if (this.items.contains(item)){
            for (int i =0;i<this.neff;i++){
                if (this.items.get(i).equals(item)){
                    return i;
                }
            }
        }
        return -1;
    }

    public void insertToInventory(SkillItem skillitem, int currCapacity) throws FullInventoryException {
        if (currCapacity < capacity)
        {
            int i;
            for (i = 0; i < this.neff; i++)
            {
                if (((SkillItem) this.items.get(i)).getName().equals(skillitem.getName()))
                {
                    ((SkillItem) this.items.get(i)).setAmount(((SkillItem) this.items.get(i)).getAmount() + 1);
                    return;
                }
            }
            this.items.add(this.neff,((T)skillitem));
            this.neff++;
            this.sortSkillItem();
        }
        else
        {
            throw new FullInventoryException("Inventory Penuh");
        }
    }

    public void insertToInventory(PlayerEngimon engimon, int currCapacity) throws FullInventoryException {
        if (currCapacity < this.capacity)
        {
            this.items.add(this.neff,((T)engimon));
            this.neff += 1;
            this.sortEngimon();
        }
        else
        {
            throw new FullInventoryException("Inventory penuh");
        }
    }

    public void deleteFromInventory(PlayerEngimon engimon) {
        if(this.items.remove(((T)engimon))){
            this.neff--;
        }
    }

    public void deleteFromInventory(SkillItem skillitem){
        int i;

        for (i = 0; i < this.neff; i++)
        {
            if (((SkillItem) this.items.get(i)).equals(skillitem))
            {
                ((SkillItem) this.items.get(i)).setAmount(((SkillItem) this.items.get(i)).getAmount() - 1);
                break;
            }
        }
        if (((SkillItem) this.items.get(i)).getAmount() == 0)
        {
            if(this.items.remove(((T)skillitem))){
                this.neff--;
            }
        }
    }

    public void deleteFromInventory(SkillItem skillitem,int count) throws NotEnoughSkillItemException{
        int i;

        for (i = 0; i < this.neff; i++)
        {
            if (((SkillItem) this.items.get(i)).equals(skillitem))
            {
                if ((((SkillItem) this.items.get(i)).getAmount() - count) >= 0 ){
                    ((SkillItem) this.items.get(i)).setAmount(((SkillItem) this.items.get(i)).getAmount() - count);
                    break;
                } else {
                    throw new NotEnoughSkillItemException("Jumlah item tidak cukup");
                }
            }
        }
        if (((SkillItem) this.items.get(i)).getAmount() == 0)
        {
            this.items.remove(((T)skillitem));
            this.neff--;
        }
    }

    public int getSkillItemAmount() {
        int sum = 0;
        for (int i = 0; i < this.neff; i++)
        {
            sum += ((SkillItem) this.items.get(i)).getAmount();
        }
        return sum;
    }

    public void displayEngimon() {
        // Output : 0. ID:45 Charmander Fire 20
        System.out.println("List Engimon :" );
        int count = 0, i;
        for (i = 0; i < this.neff; i++)
        {
            System.out.printf("%d. ID:%d %s %s %d\n",count+1,((PlayerEngimon) this.items.get(i)).getId(), ((PlayerEngimon) this.items.get(i)).getName(),getEngimonElementInString(((PlayerEngimon) this.items.get(i))) , ((PlayerEngimon) this.items.get(i)).getLevel() );
            count++;
        }
        if (count == 0)
        {
            System.out.println("Anda tidak memiliki Engimon");
        }
    }

    public void displaySkillItem() {
        // Output : 0. Mangga, 1
        System.out.println("List SkillItem :" );
        int i ,count = 0;
        for (i = 0; i < this.neff; i++)
        {
            System.out.printf("%d. %s, %d\n",count+1,((SkillItem) this.items.get(i)).getName(), ((SkillItem) this.items.get(i)).getAmount());
            count++;
        }

        if (i == 0)
        {
            System.out.println("Anda tidak memiliki Skillitem");
        }
    }

    public void displayEngimonAvailableToBreed() throws NoBreedableEngimon {
        System.out.println("List Engimon yang dapat di Breed :");
        int count = 0, i;
        for (i = 0; i < this.neff; i++)
        {
            if (((PlayerEngimon) this.items.get(i)).getLevel() > 30)
            {

                System.out.printf("%d. ID:%d %s %d\n",count+1,((PlayerEngimon) this.items.get(i)).getId(), ((PlayerEngimon) this.items.get(i)).getName(), ((PlayerEngimon) this.items.get(i)).getLevel() );
                count++;
            }
        }
        if (count <= 1)
        {
            throw new NoBreedableEngimon("Tidak ada Engimon yang bisa di breed!!");
        }
    }

    public void sortEngimon(){
        Comparator<T> compareByElementLevel = (T e1, T e2) -> {
            int c = ( getEngimonElementInString(((PlayerEngimon) e1)).compareTo(getEngimonElementInString(((PlayerEngimon) e2))));
            if (c == 0){
                c = (((Integer) ((PlayerEngimon)e1).getLevel()).compareTo(((Integer) ((PlayerEngimon)e2).getLevel())));
            }
            return c;
        };
        Collections.sort(this.items,compareByElementLevel);
    }

    public void sortSkillItem(){
        Comparator<T> compareByPower = (T s1, T s2) -> ((Integer) ((SkillItem) s1).getPower()).compareTo(((Integer) ((SkillItem) s2).getPower()));
        Collections.sort(this.items,compareByPower);
    }

    public String getEngimonElementInString(PlayerEngimon engimon){
        ArrayList<String> result = new ArrayList<>();
        for (IElements elemen : engimon.getElements()){
            result.add(elemen.toString());
        }
        return String.join("/",result);
    }

    public T getItemByIndex(int index){
        return this.items.get(index);
    }

    public int getEngimonIndexByID(int id) throws EngimonNotFoundException {
        int i;
        for (i = 0; i < this.neff; i++)
        {
            if (((PlayerEngimon) this.items.get(i)).getId() == id)
            {
                return i;
            }
        }
        throw new EngimonNotFoundException("Engimon Tidak Ditemukan");
    }

    public int getSkillItemIndexByName(String name) throws SkillItemNotFound{
        int i;
        for (i = 0; i < this.neff; i++)
        {
            if (((SkillItem) this.items.get(i)).getName().equals(name))
            {
                return i;
            }
        }
        throw new SkillItemNotFound("SkillItem Tidak Ditemukan");
    }

    public int getMaxEngimonLevel() {
        int i;
        int level = 1;
        for (i = 0; i < this.neff; i++)
        {
            if (((PlayerEngimon) this.items.get(i)).getLevel() > level)
            {
                level = ((PlayerEngimon) this.items.get(i)).getLevel();
            }
        }
        return level;
    }

    public ArrayList<PlayerEngimon> getBreedableEngimonList() throws NoBreedableEngimon {
        int i;
        ArrayList<PlayerEngimon> result = new ArrayList<>();
        for (i = 0; i < this.neff; i++)
        {
            if (((PlayerEngimon) this.items.get(i)).getLevel() >= 4)
            {
                ((PlayerEngimon) this.items.get(i)).displayInfo();
                result.add(((PlayerEngimon) this.items.get(i)));
            }
        }
        if (result.size() < 1){
            throw new NoBreedableEngimon("Tidak ada Engimon yang dapat di breed");
        }
        return result;
    }

}
