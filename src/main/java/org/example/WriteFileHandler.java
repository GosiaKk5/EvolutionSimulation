package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class WriteFileHandler{

    private final String filePath;
    private final Statistics statistics;

    public WriteFileHandler(String filePath, Statistics statistics){
        this.filePath = filePath;
        this.statistics = statistics;
        if(!filePath.equals("null")){
            createHeadlines();
        }
    }
    private void createHeadlines(){
        try{
            File file = new File(this.filePath);
            PrintWriter output = new PrintWriter(new FileWriter(file,true));
            output.printf("%s; %s; %s; %s; %s; %s\n",
                    "liczba zwierzat",
                    "liczba roslin",
                    "liczba wolnych pol",
                    "najpopularniajszy genotyp",
                    "srednia energia zwierzat",
                    "sredni wiek smierci");
            output.close();
        }catch(Exception ex){
            throw new IllegalArgumentException("write file error");
        }
    }
    public void writeToFile() {
        try{
            File file = new File(this.filePath);
            PrintWriter output = new PrintWriter(new FileWriter(file,true));
            output.printf("%d; %d; %d; %s; %f; %f\n",
                    statistics.getNoAnimals(),
                    statistics.getNoPlants(),
                    statistics.getNoFreeFields(),
                    Arrays.toString(statistics.getTheMostPopularGenotype()),
                    statistics.getAvgEnergy(),
                    statistics.getAvgDeathAge());
            output.close();
        }catch(Exception ex){
            throw new IllegalArgumentException("write file error");
        }
    }
}
