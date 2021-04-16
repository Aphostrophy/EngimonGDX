package com.ungabunga.model.entities;


import java.util.*;
import com.ungabunga.model.exceptions.*;
import com.ungabunga.model.enums.CONSTANTS;


public class Inventory<T> {
    ArrayList<T> items;
    Integer capacity;
    Integer neff;

    Inventory(){
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
    public void insertToInventory(T item, int currCapacity) throws FeatureNotImplementedException {
        if (currCapacity < capacity)
        {
            this.items.add(neff,item);
            this.neff += 1;
        }
        else
        {
            throw new FeatureNotImplementedException("Inventory belum siap");
        }
    }

    public void deleteFromInventory(T item){
        this.items.remove(item);
        this.neff -= 1;
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
}
