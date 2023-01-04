package org.example;
import org.example.gui.SingleSimulationVisualizer;

import java.io.*;
import java.util.HashMap;

import java.sql.SQLOutput;



public class FileHandler {
    private int width;
    private int height;
    private IChangePositionHandler positionHandler;
    private int numberOfStartPlants;
    private int plantEnergy;
    private int numberOfPlantsGrowDaily;
    private IMap map;
    private int numberOfStartAnimals;
    private int animalStartEnergy;
    private int breedReadyEnergy;
    private int breedHandoverEnergy;
    private int minNumberOfMutations;
    private int maxNumberOfMutations;
    private IMutationHandler mutationHandler;
    private int genotypeLength;
    private IChangeOrientationHandler orientationHandler;
    private HashMap<String, String> dict = new HashMap<>();
    private String pathForStatistics;
    public FileHandler(String path){
        readFile(path);
        checkIfFileHasEverything();
        convertValues();
        checkIfValuesAreOk();
    }
    private void readFile(String path){
        try{
            System.out.println(path);
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line;
            while ((line = br.readLine()) != null){
                if(!(line).trim().equals("") && !(line).trim().equals("/n") && !(line).isEmpty()){

                    String[] keyAndValue = line.split(",");
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

    private void checkIfFileHasEverything(){
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
        "orientationHandler",
        "pathForStatistics"};

        for(String toCheck : needed){
            if(!this.dict.containsKey(toCheck)){
                throw new IllegalArgumentException("Add corectly" + toCheck);
            }
        }
    }

    private void convertValues() {
        width = Integer.parseInt(this.dict.get("width"));
        height = Integer.parseInt(this.dict.get("height"));
        numberOfStartPlants = Integer.parseInt(this.dict.get("numberOfStartPlants"));
        plantEnergy = Integer.parseInt(this.dict.get("plantEnergy"));
        numberOfPlantsGrowDaily = Integer.parseInt(this.dict.get("numberOfPlantsGrowDaily"));
        numberOfStartAnimals = Integer.parseInt(this.dict.get("numberOfStartAnimals"));
        animalStartEnergy = Integer.parseInt(this.dict.get("animalStartEnergy"));
        breedReadyEnergy = Integer.parseInt(this.dict.get("breedReadyEnergy"));
        breedHandoverEnergy = Integer.parseInt(this.dict.get("breedHandoverEnergy"));
        minNumberOfMutations = Integer.parseInt(this.dict.get("minNumberOfMutations"));
        maxNumberOfMutations = Integer.parseInt(this.dict.get("maxNumberOfMutations"));
        genotypeLength = Integer.parseInt(this.dict.get("genotypeLength"));

        pathForStatistics = this.dict.get("pathForStatistics");


        switch (dict.get("positionHandler")) {
            case "kula ziemska" -> {
                this.positionHandler = new Globe(width, height);
            }
            case "piekielny portal" -> {
                this.positionHandler = new HellishPortal(width, height, breedHandoverEnergy);
            }
            default -> {
                throw new IllegalArgumentException("positionHandler can't have value:" + dict.get("positionHandler"));
            }
        }

        switch (dict.get("map")) {
            case "zalesione rowniki" -> {
                this.map = new EquatorialForestMap(width, height, numberOfStartPlants);
            }
            case "toksyczne trupy" -> {
                this.map = new ToxicCorpsesMap(width, height, numberOfStartPlants);
            }
            default -> {
                throw new IllegalArgumentException("map can't have value:" + dict.get("map"));
            }
        }

        switch (dict.get("mutationHandler")) {
            case "pelna losowosc" -> {
                this.mutationHandler = new FullRandomness();
            }
            case "lekka korekta" -> {
                this.mutationHandler = new LittleCorrect();
            }
            default -> {
                throw new IllegalArgumentException("mutationHandler can't have value:" + dict.get("mutationHandler"));
            }
        }

        switch (dict.get("orientationHandler")) {
            case "pelna predestynacja" -> {
                this.orientationHandler = new FullPredestination();
            }
            case "nieco szalenstwa" -> {
                this.orientationHandler = new LittleCraziness();
            }
            default -> {
                throw new IllegalArgumentException("orientationHandler can't have value:" + dict.get("orientationHandler"));
            }
        }
    }

    private void checkIfValuesAreOk(){
        if(width < 5 || height < 5 || width > 50 || height > 50){
            throw new IllegalArgumentException("width and height should be greater than 5 and less than 51");
        }
        if(maxNumberOfMutations < 0){
            throw new IllegalArgumentException("maxNumberOfMutations can't be less than 0");
        }
        if(minNumberOfMutations < 0){
            throw new IllegalArgumentException("minNumberOfMutations can't be less than 0");
        }
        if(numberOfStartPlants < 0){
            throw new IllegalArgumentException("numberOfStartPlants can't be less than 0");
        }
        if(plantEnergy < 0){
            throw new IllegalArgumentException("plantEnergy can't be less than 0");
        }
        if(numberOfPlantsGrowDaily < 0){
            throw new IllegalArgumentException("numberOfPlantsGrowDaily can't be less than 0");
        }
        if(numberOfStartAnimals < 0){
            throw new IllegalArgumentException("numberOfStartAnimals can't be less than 0");
        }
        if(breedReadyEnergy  < 0){
            throw new IllegalArgumentException("breedReadyEnergy can't be less than 0");
        }
        if(animalStartEnergy < 0){
            throw new IllegalArgumentException("plantEnergy can't be less than 0");
        }
        if(breedHandoverEnergy  < 0){
            throw new IllegalArgumentException("plantEnergy can't be less than 0");
        }

        if(genotypeLength < 1 || genotypeLength > 30){
            throw new IllegalArgumentException("genotypeLenght should be greater than 0 and less than 31");
        }

        if(maxNumberOfMutations > genotypeLength){
            throw new IllegalArgumentException("maxNumberOfMutations can't be greater than genotypeLenght");
        }

        if(minNumberOfMutations > maxNumberOfMutations){
            throw new IllegalArgumentException("minNumberOfMutations can't be greater than maxNumberOfMutations");
        }

        if(breedHandoverEnergy >= breedReadyEnergy){
            throw new IllegalArgumentException("breedHandoverEnergy can't be greater than breedReadyEnergy");
        }

        if(!pathForStatistics.equals("null")){
            File file = new File(pathForStatistics);
            if (!file.exists()){
                throw new IllegalArgumentException("pathForStatistics don't provide a file");
            }
        }
    }

    public SingleSimulationVisualizer getSimulationVisualizer(){
        SingleSimulationVisualizer visualizer = new SingleSimulationVisualizer(height,
                width,
                map,
                plantEnergy,
                numberOfPlantsGrowDaily,
                numberOfStartAnimals,
                animalStartEnergy,
                breedReadyEnergy,
                breedHandoverEnergy,
                minNumberOfMutations,
                maxNumberOfMutations,
                genotypeLength,
                mutationHandler,
                positionHandler,
                orientationHandler,
                pathForStatistics);

        return visualizer;
    }

}
