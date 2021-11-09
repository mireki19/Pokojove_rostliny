package com.engeto.lekce05;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Main {

    public static void main(String[] args) {
//ověření první - načtení ze souboru a výpis o zálivce každé květiny
        Plants plants = new Plants();
        plants.readListFromFile(Settings.FILENAME_STANDARD);
        plants.getWateringInfo();

// ověření druhé - přidání dvou květin a výmaz jedné
        Plant plant=new Plant("Bazalka v kuchyni", "", LocalDate.of(2021,9,4), LocalDate.of(2021,9,4),3);
        plants.addPlant(plant);
        plant=new Plant("tchýnin jazyk", "velmi odolná", LocalDate.of(2002,9,21), LocalDate.of(2021,10,31),77);
        plants.addPlant(plant);
        plants.removePlantAtIndex(2);
        plants.writeListToFile(Settings.FILENAME_OUTPUT);
// načtení vygenerovaného souboru
        System.out.println("Načtení nově vytvořeného souboru a následný výpis zálivky ke každé květině:");
        plants.readListFromFile(Settings.FILENAME_OUTPUT);
        plants.getWateringInfo();


    }


    //testovací hřišťátko
    public static void testMain (){
        Plants plants = new Plants();
        Plant plant=new Plant("fialka");
        System.out.println("Výpis nově zadané květiny fialka, automaticky zadáno dnešní datum u zasazení a poslední zálivky.");
        System.out.println(plant.getWateringInfo());
        System.out.println("\nTest ošetření zadání záporné frekvence zálivky");
        System.out.println();
        plant.setFrqOfWatering(-1);

        System.out.println("\nTest nastavení data zálivky předcházejícímu datu zasazení");
        plant.setLastWatering(LocalDate.of(2021, 11, 1));
        System.out.println(plant.getLastWatering().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        System.out.println();

        plants.readListFromFile(Settings.FILENAME_STANDARD);
        System.out.println("Výpis květin po prvním načtení ze souboru:");
        System.out.println(plants);
        System.out.println("Změna parametru u druhé květiny, zápis upraveného souboru, načtení upraveného souboru");
        plants.getPlantAtIndex(1).setLastWatering(LocalDate.now());
        plants.writeListToFile(Settings.FILENAME_STANDARD);
        plants.readListFromFile(Settings.FILENAME_STANDARD);
        System.out.println(plants);

        plant=new Plant("bazalka", "moc dobrá do salátu", LocalDate.of(2021,3,13), LocalDate.of(2021,10,31),7);
        plants.addPlant(plant);
        System.out.println(plants.getPlantAtIndex(2));
        plants.writeListToFile(Settings.FILENAME_STANDARD);
        System.out.println("nový soubor:");
        plants.readListFromFile(Settings.FILENAME_STANDARD);
        System.out.println("načtení ze souboru a náslený výpis zálivky ke každé květině");
        plants.getWateringInfo();
        plant=new Plant("tchýnin jazyk", "velmi odolná", LocalDate.of(2002,9,21), LocalDate.of(2021,10,31),77);
        plants.addPlant(plant);
        plant=new Plant("fuchsie", "bez komentáře", LocalDate.of(2021,10,1), LocalDate.of(2021,10,31),77);
        plants.addPlant(plant);
        plants.removePlantAtIndex(0);
        plants.writeListToFile(Settings.FILENAME_OUTPUT);
        plants.compareListWithFile(Settings.FILENAME_OUTPUT);
    }
}
