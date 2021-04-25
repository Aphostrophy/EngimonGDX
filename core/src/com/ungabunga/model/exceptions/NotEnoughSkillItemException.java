package com.ungabunga.model.exceptions;

public class NotEnoughSkillItemException extends Exception{
    public NotEnoughSkillItemException(String errMessage){
        super(errMessage);
    }
}