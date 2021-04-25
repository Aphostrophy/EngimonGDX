package com.ungabunga.model.exceptions;

public class DuplicateSkillException extends Exception{
    public DuplicateSkillException(String errMessage){
        super(errMessage);
    }
}
