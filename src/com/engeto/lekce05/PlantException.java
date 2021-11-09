package com.engeto.lekce05;

public class PlantException extends Exception{

    public PlantException(String message){
        super(message);
//        volám konstruktor "rodičovské třídy" Exception

    }
}
