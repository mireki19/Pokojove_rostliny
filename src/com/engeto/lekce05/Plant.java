package com.engeto.lekce05;

import com.sun.deploy.security.SelectableSecurityManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Plant {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate lastWatering;
    private int frqOfWatering;

    public Plant(String name, String notes, LocalDate planted, LocalDate lastWatering, int frqOfWatering) {
        this.name = name;
        this.notes = notes;
        this.planted=planted;
        this.lastWatering = lastWatering;
        this.frqOfWatering=frqOfWatering;
    }

    public Plant(String name, LocalDate planted, int frqOfWatering) {
        this.name = name;
        this.notes="";
        this.lastWatering =LocalDate.now();
        this.planted=planted;
        this.frqOfWatering=frqOfWatering;
    }


public Plant(String name){
        this.name=name;
        this.notes="";
        this.lastWatering =LocalDate.now();
        this.planted=LocalDate.now();;
        this.frqOfWatering=Settings.FREQ_OF_WATERING;
}

public String getWateringInfo(){
        LocalDate recomendedWatering;
        recomendedWatering=getLastWatering().plusDays(frqOfWatering);
        return "Jméno: "+getName()+"\n poslední zálivka: "+getLastWatering().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))+" doporučeno zalít znovu dne: "+recomendedWatering.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getLastWatering() {
        return lastWatering;
    }

    public void setLastWatering(LocalDate lastWatering) {
        if (lastWatering.isBefore(planted))
            try {
                throw new PlantException("Chybné zadání! Zadané datum ("+lastWatering.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))+") nemůže předcházet datu zasazení! ("+planted.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))+")");
            } catch (PlantException e) {
                System.out.println(e.getMessage());
                System.out.println("Zadané datum poslední zálivky NEBYLO nastaveno! Poslední zálivka:"+this.lastWatering.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            }
        else
        this.lastWatering = lastWatering;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public void setPlanted(LocalDate planted) {


        this.planted = planted;
    }

    public int getFrqOfWatering() {
        return frqOfWatering;
    }

    public void setFrqOfWatering(int frqOfWatering)  {
        if (frqOfWatering<=0)
            try {
                throw new PlantException("Chybné zadání! ("+frqOfWatering+"), frekvence zálivky musí být celé kladné číslo!");
            } catch (PlantException e) {
                System.out.println(e.getMessage());
                System.out.println("Frekvence zálivky byla nastavena na standardních "+Settings.FREQ_OF_WATERING+" dnů.");
                this.frqOfWatering=Settings.FREQ_OF_WATERING;
            }
        else

        this.frqOfWatering = frqOfWatering;
    }

    @Override
    public String toString() {
        return name+", zasazeno: "+planted.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }
}
