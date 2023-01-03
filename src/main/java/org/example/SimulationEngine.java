package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements Runnable {

    static final int MOVE_DELAY = 500;
    int height;
    int width;
    int numberOfStartPlants;
    private final int plantEnergy;
    private final int numberOfPlantsGrowDaily;
    private final int numberOfStartAnimals;
    private final int startEnergy;
    private final int breedReadyEnergy;
    private final int breedHandoverEnergy;
    private final int minNumberOfMutations;
    private final int maxNumberOfMutations;
    private final int genotypeLength;

    IChangePositionHandler changePositionHandler;
    IChangeOrientationHandler changeOrientationHandler;
    IMutationHandler mutationHandler;
    IMap map;
    private final List<Animal> animals;
    private boolean paused;
    private ArrayList<INextSimulationDayObserver> observers = new ArrayList<INextSimulationDayObserver>();

    public Statistics statistic;


    public IMap getMap() {
        return map;
    }

    //pomocniczy konstruktor do testowania
    public SimulationEngine(int height,
                            int width,
                            String variantMap,
                            int numberOfStartPlants,
                            int plantEnergy,
                            int numberOfPlantsGrowDaily,
                            String variantGrowingPlants,
                            int numberOfStartAnimals,
                            int startEnergy,
                            int breedReadyEnergy,
                            int breedHandoverEnergy,
                            int minNumberOfMutations,
                            int maxNumberOfMutations,
                            String variantMutation,
                            int genotypeLength,
                            String variantOrientation){

        this.paused = false;

        this.height = height;
        this.width = width;
        this.numberOfStartPlants = numberOfStartPlants;
        this.plantEnergy = plantEnergy;
        this.numberOfPlantsGrowDaily = numberOfPlantsGrowDaily;
        this.numberOfStartAnimals = numberOfStartAnimals;
        this.startEnergy = startEnergy;
        this.breedReadyEnergy = breedReadyEnergy;
        this.breedHandoverEnergy = breedHandoverEnergy;
        this.minNumberOfMutations = minNumberOfMutations;
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.genotypeLength = genotypeLength;

        this.animals = new ArrayList<>();


        switch(variantMap){
            case "Globe" -> { this.changePositionHandler = new Globe(width, height); }
            case "Hell" -> {  this.changePositionHandler = new HellishPortal(width, height, breedHandoverEnergy); }
            default -> {
                System.out.println("NAZWA WARIANTU ZMIANY POZYCJI");
            }
        }

        switch(variantGrowingPlants){
            case "EquatorialForest" -> { this.map = new EquatorialForestMap(width, height, numberOfStartPlants); }
            case "ToxicCorpses" -> { this.map = new ToxicCorpsesMap(width, height, numberOfStartPlants); }
            default -> {
                System.out.println("NAZWA WARIANTU ZMIANY ORIENTACJI");
            }
        }

        this.statistic = new Statistics(map, this);

        switch(variantMutation){
            case "FullRandomness" -> { this.mutationHandler = new FullRandomness(); }
            case "LittleCorrect" -> { this.mutationHandler = new LittleCorrect(); }
            default -> {
                System.out.println("NAZWA WARIANTU MUTACJI");
            }
        }

        switch(variantOrientation){
            case "FullPredestination" -> { this.changeOrientationHandler = new FullPredestination(); }
            case "FullRandomness" -> { this.changeOrientationHandler = new LittleCraziness(); }
            default -> {
                System.out.println("NAZWA WARIANTU ORIENTACJI");
            }
        }

        this.addAnimals();
    }

    //konstruktor do app
    public SimulationEngine(IMap map,
                            IChangePositionHandler changePositionHandler,
                            int plantEnergy,
                            int numberOfPlantsGrowDaily,
                            int numberOfStartAnimals,
                            int startEnergy,
                            int breedReadyEnergy,
                            int breedHandoverEnergy,
                            int minNumberOfMutations,
                            int maxNumberOfMutations,
                            IMutationHandler mutationHandler,
                            int genotypeLength,
                            IChangeOrientationHandler changeOrientationHandler){


        this.height = map.getHeight();
        this.width = map.getWidth();

        this.paused = false;

        this.plantEnergy = plantEnergy;
        this.numberOfPlantsGrowDaily = numberOfPlantsGrowDaily;
        this.numberOfStartAnimals = numberOfStartAnimals;
        this.startEnergy = startEnergy;
        this.breedReadyEnergy = breedReadyEnergy;
        this.breedHandoverEnergy = breedHandoverEnergy;
        this.minNumberOfMutations = minNumberOfMutations;
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.genotypeLength = genotypeLength;

        this.animals = new ArrayList<>();

        this.changePositionHandler = changePositionHandler;
        this.map = map;
        this.mutationHandler = mutationHandler;
        this.changeOrientationHandler = changeOrientationHandler;
        this.statistic = new Statistics(map, this);

        this.addAnimals();

        this.dayChanged();

    }

    private void addAnimals(){

        Random random = new Random();

        for(int i = 0; i < this.numberOfStartAnimals; i++){

            Animal animal = new Animal(this.map,
                            this.genotypeLength,
                            this.startEnergy,
                            this.breedReadyEnergy,
                            this.breedHandoverEnergy,
                            this.minNumberOfMutations,
                            this.maxNumberOfMutations,
                            this.mutationHandler,
                            this.changeOrientationHandler,
                            this.changePositionHandler);

            this.map.placeAnimal(animal);
            this.animals.add(animal);
        }
    }
    public void run(){

        try{
            while(true) {
                if(!this.paused){
                    this.deleteDeadAnimals();
                    this.moveAnimals();
                    this.eatPlants();
                    this.breedAnimals();
                    this.growPlants();
                    System.out.println("----------");
                    for(Animal animal : animals){
                        System.out.println(Arrays.toString(animal.getGenotype()));
                    }
                    System.out.println("most popular: " + this.statistic.getTheMostPopularGenotype());
                    System.out.println("noAnimals: "+ this.statistic.getNoAnimals());
                    System.out.println("animals popular: " + this.statistic.getAnimalsWithMostPopular());
                    System.out.println("----------");
                    this.dayChanged();
                }
                Thread.sleep(MOVE_DELAY);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void deleteDeadAnimals(){
        ArrayList <Animal> animalsToDelate = new ArrayList<>();
        for(Animal animal: animals){
            if(animal.getEnergy() <= 0){
                animalsToDelate.add(animal);
            }
        }
        for(Animal animal : animalsToDelate) {
            map.removeAnimal(animal);
            this.animals.remove(animal);
            this.statistic.isDead(animal);
        }
    }
    private void moveAnimals(){
            for(Animal animal : this.animals){
                animal.ageAddOne(); //postarza zwierzę o jeden dzień
                animal.move();
                animal.changeOrientation();
            }

        }
    private void eatPlants(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Vector2d position = new Vector2d(x,y);
                if(map.plantAt(position) != null && map.animalsAt(position).size() > 0){
                    map.removePlant(position);
                    Animal animal = chooseWhoEat(position);
                    if(animal.getEnergy() >= 0){ //jedzą żywe zwierzaki xd
                        animal.changeEnergy(this.plantEnergy);
                    }

                }
            }
        }
    }

    private Animal chooseWhoEat(Vector2d position){

        ArrayList<Animal> fighters = map.animalsAt(position);

        if (fighters.size() == 1){
            return fighters.get(0);
        }
        fighters.sort((a1, a2) -> {
            {
                if(a2.getEnergy() != a1.getEnergy()){
                    return a2.getEnergy() - a1.getEnergy();
                }
                if(a2.getAge() != a1.getAge()){
                    return a2.getAge() - a1.getAge();
                }
                return a2.getNoChildren() - a1.getNoChildren();
            }
        });

        return fighters.get(0);

    }
    private void breedAnimals(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Vector2d position = new Vector2d(x,y);
                ArrayList<Animal> fighters = map.animalsAt(position);
                if(fighters.size() >= 2){
                    Animal animal1 = fighters.get(0); // zwierzeta zostaly posortowane przy walce o jedzenie
                    Animal animal2 = fighters.get(1);
                    if(animal1.getEnergy() > breedReadyEnergy && animal2.getEnergy() > breedReadyEnergy){
                        Animal newAnimal = animal1.breedNewAnimal(animal2);
                        this.map.placeAnimal(newAnimal);
                        this.animals.add(newAnimal);
                    }


                }
            }
        }
    }
    private void growPlants(){
        map.addPlants(numberOfPlantsGrowDaily);
    }
    public boolean isPaused(){
        return this.paused;
    }
    public void unPause(){
        this.paused = false;
    }
    public void pause(){
        this.paused = true;
    }

    public void addObserver(INextSimulationDayObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(INextSimulationDayObserver observer) {
        observers.remove(observer);
    }

    private void dayChanged() {
        for (INextSimulationDayObserver observer : observers) {
            observer.dayChanged();
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public List<Animal> getAnimals(){
        return this.animals;
    }
}
