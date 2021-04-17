package com.ungabunga.model.exceptions;

public class FullInventoryException extends Exception{
    public FullInventoryException(String errMessage){
        super(errMessage);
    }
}
