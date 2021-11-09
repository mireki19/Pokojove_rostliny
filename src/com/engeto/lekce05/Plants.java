package com.engeto.lekce05;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Plants {

    private Plant plant;
    private String outPutLine;
    List<Plant> plantList= new ArrayList<>();


    public void addPlant(Plant plant){
        plantList.add(plant);
    }

    public Plant getPlantAtIndex(int indexOfPlant) {

        try {
            return plantList.get(indexOfPlant);
        } catch (Exception e) {
            System.out.println("Index přesahuje počet prvků, vypisuji první prvek.");

            e.printStackTrace();
        }
        return plantList.get(0);
    }

    public void removePlant(Plant plant){
        plantList.remove(plant);
    }

    public void removePlantAtIndex(int indexOfPlant){
        plantList.remove(indexOfPlant);
    }

    public int getNumberOfPlants(){
        return plantList.size();
    }

    public void readListFromFile(String fileName){
    plantList.clear();

        try (Scanner scanner = new Scanner(new File(fileName))){
            int lineNumber=0;
            LocalDate parseDatePlanted;
            LocalDate parseDatelastWatering;
            int parseFreqOfWatering;

            while (scanner.hasNextLine()) {
                lineNumber++;
                String nextLine = scanner.nextLine();
                String[] items = nextLine.split(Settings.ELEMENT_SEPARATOR);
                try {
                parseDatePlanted=LocalDate.parse(items[4], DateTimeFormatter.ofPattern("yyyy-M-d"));
                parseDatelastWatering=LocalDate.parse(items[3], DateTimeFormatter.ofPattern("yyyy-M-d"));
                parseFreqOfWatering=Integer.parseInt(items[2]);
                Plant plant = new Plant(items[0], items[1], parseDatePlanted, parseDatelastWatering,parseFreqOfWatering);
                plantList.add(plant);
                } catch (Exception ex) {
                    System.err.println("Chybný formát data nebo frekvence zálivky na řádku "+lineNumber+" v souboru "+fileName+"\n"+
                            "načteny jen bezchybné řádky souboru");
                }

            }
        }
    catch (FileNotFoundException ex) {

        }


    }

    public void writeListToFile(String fileName){

        try
                (PrintWriter writer = new PrintWriter(new File(fileName)))

        {
            for (Plant plant:plantList) {
                outPutLine =plant.getName()+"\t"+plant.getNotes()+"\t"+plant.getFrqOfWatering()+"\t"+plant.getLastWatering()+"\t"+plant.getPlanted();
                writer.println(outPutLine);

            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void getWateringInfo(){
        for (Plant plant:plantList){
            outPutLine=plant.getName()+" - poslední zálivka: "+plant.getLastWatering().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
            System.out.println(outPutLine);
        }
    }

    public void compareListWithFile(String fileName){
        try (Scanner scanner = new Scanner(new File(fileName))){
            int lineNumber=0;
            LocalDate parseDatePlantedFile;
            LocalDate parseDatelastWateringFile;
            LocalDate parseDatelastWateringList;
            LocalDate parseDatePlantedList;


            while (scanner.hasNextLine()) {
                lineNumber++;
                String nextLine = scanner.nextLine();
                String[] items = nextLine.split(Settings.ELEMENT_SEPARATOR);
                if (items.length!=Settings.NUMBER_OF_ELEMENTS) throw new PlantException("Chyba počtu prvků na řádku "+lineNumber);
                parseDatePlantedFile=LocalDate.parse(items[4], DateTimeFormatter.ofPattern("yyyy-M-d"));
                parseDatelastWateringFile=LocalDate.parse(items[3], DateTimeFormatter.ofPattern("yyyy-M-d"));
                parseDatePlantedList=plantList.get(lineNumber-1).getPlanted();
                parseDatelastWateringList=plantList.get(lineNumber-1).getLastWatering();
                if ((!plantList.get(lineNumber-1).getName().equals(items[0])) ||
                    (!parseDatePlantedFile.equals(parseDatePlantedList)) ||
                    (!parseDatelastWateringFile.equals(parseDatelastWateringList)) ||
                        (!plantList.get(lineNumber-1).getNotes().equals(items[1])) ||
                        (plantList.get(lineNumber-1).getFrqOfWatering()!=Integer.parseInt(items[2])))
                    throw new PlantException("Obsah prvků na řádku "+lineNumber+" se neshoduje");

            }
            System.out.println("Obsah seznamu je shodný s obsahem souboru "+fileName);
        }
        catch (FileNotFoundException ex) {

        } catch (PlantException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String vystup="";
        for (Plant plant:plantList){
            vystup=vystup+plant.getName()+" / "+plant.getNotes()+" / zasazeno: "+plant.getPlanted()+" / naposledy zalito: "+plant.getLastWatering()+" / četnost zálivky: "+plant.getFrqOfWatering()+"\n";
        }
        return vystup;
    }
}
