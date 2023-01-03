package org.example;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import java.sql.SQLOutput;



public class FileHandler {
    public int width;
    public int height;
    public IChangePositionHandler positionHandler;
    public int numberOfStartPlants;
    public int plantEnergy;
    public int numberOfPlantsGrowDaily;
    public int map;
    public int numberOfStartAnimals;
    public int animalStartEnergy;
    public int breedReadyEnergy;
    public int breedHandoverEnergy;
    public int minNumberOfMutations;
    public int maxNumberOfMutations;
    public IMutationHandler mutationHandler;
    public int genotypeLength;
    public IChangeOrientationHandler orientationHandler;
    HashMap<String, String> dict = new HashMap<>();

    public FileHandler(String path){
        readFile(path);
        checkIfFileIsOk();
        convertValues();
    }
    private void readFile(String path){
        try{
            System.out.println(path);
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line;
            while ((line = br.readLine()) != null){
                if(!(line).trim().equals("") && !(line).trim().equals("/n") && !(line).isEmpty()){

                    String[] keyAndValue = line.split(":");
                    String key = keyAndValue[0];
                    String value = keyAndValue[1];
                    this.dict.put(key, value);
                }
            }
            br.close();
            for(String key : this.dict.keySet()){
                System.out.println(key + ":" + this.dict.get(key));
            }
        }catch(Exception ex){
            System.out.println("Błąd");;
        }
    }

    private void checkIfFileIsOk(){
        String[] needed = {"width",
        "height",
        "positionHandler",
        "numberOfStartPlants",
        "plantEnergy",
        "numberOfPlantsGrowDaily",
        "map",
        "numberOfStartAnimals",
        "animalStartEnergy",
        "breedReadyEnergy",
        "breedHandoverEnergy",
        "minNumberOfMutations",
        "maxNumberOfMutations",
        "mutationHandler",
        "genotypeLength",
        "orientationHandler"};

        for(String toCheck : needed){
            if(!this.dict.containsKey(toCheck)){
                throw new IllegalArgumentException("Add corectly" + toCheck);
            }
        }
    }

    private void convertValues(){
        /*this.width =;
        this.height;
        this.positionHandler;
        this.numberOfStartPlants;
        public int plantEnergy;
        public int numberOfPlantsGrowDaily;
        public int map;
        public int numberOfStartAnimals;
        public int animalStartEnergy;
        public int breedReadyEnergy;
        public int breedHandoverEnergy;
        public int minNumberOfMutations;
        public int maxNumberOfMutations;
        public IMutationHandler mutationHandler;
        public int genotypeLength;
        public IChangeOrientationHandler orientationHandler;*/
    }
    private void writeFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Ania\\Desktop\\POProjektOptions\\writeReport.csv"));

            bw.write("a,b");
            bw.close();
        }catch(Exception ex){
            return;
        }
    }
}
