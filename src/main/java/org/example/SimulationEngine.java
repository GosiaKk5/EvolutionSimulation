package org.example;

import org.example.gui.SingleSimulationVisualizer;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements Runnable {

    static final int MOVE_DELAY = 200;
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


    //STRINGI (do ustalenia) -> inaczej wyjątek:
    //variantMap -> Globe/Hell
    //variantGrowingPlant -> EquatorialForest/ToxicCorpses
    //variantMutation -> FullRandomness/LittleCorrect
    //variantOrientation -> FullPredestination/LittleCraziness

    public IMap getMap() {
        return map;
    }

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

        //UWAGA W TYM MIEJSCU RZUCAMY WYJĄTAKI JEŻELI PODANE WARTOŚCI SA BLEDNE
        // height, widht > 0, width >= 5
        // wszystkie inty itd > 0
        // minNumberOfMutation <= max
        // number of plants < widht*height? nie wiem jak to jest zaimplementowane
        // maxnumber of mutation <= genotypelenght

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

//        System.out.println(this.map);
        this.addAnimals();

//        System.out.println("ANIMALS ADDED:");
//        System.out.println();
//        System.out.println(this.map);
//
//        System.out.println("ANIMALS: " + this.animals);
//        System.out.println();

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


        this.height = map.getUpperBound() + 1;
        this.width = map.getRightBound() + 1;

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

        this.addAnimals();

        this.dayChanged();

    }

    private void addAnimals(){

        int[] genotype = new int[genotypeLength];

        for(int i = 0; i < genotypeLength; i++){
            genotype[i] = 0;
        }

        int indexOfActiveGen = 0;

        Animal a1 = new Animal(this.map,
                            new Vector2d(0,0),
                            genotype,
                            this.genotypeLength,
                            indexOfActiveGen,
                            this.startEnergy,
                            this.breedHandoverEnergy,
                            this.mutationHandler,
                            this.changeOrientationHandler,
                            this.changePositionHandler);

        int[] genotype2 = new int[genotypeLength];

        for(int i = 0; i < genotypeLength; i++){
            genotype2[i] = 1;
        }

        Animal a2 = new Animal(this.map,
                            new Vector2d(3,3),
                            genotype2,
                            this.genotypeLength,
                            indexOfActiveGen,
                            this.startEnergy,
                            this.breedHandoverEnergy,
                            this.mutationHandler,
                            this.changeOrientationHandler,
                            this.changePositionHandler);

        Animal a3 = new Animal(this.map,
                new Vector2d(3,2),
                genotype2,
                this.genotypeLength,
                indexOfActiveGen,
                this.startEnergy,
                this.breedHandoverEnergy,
                this.mutationHandler,
                this.changeOrientationHandler,
                this.changePositionHandler);

        Animal a4 = new Animal(this.map,
                new Vector2d(3,2),
                genotype2,
                this.genotypeLength,
                indexOfActiveGen,
                this.startEnergy,
                this.breedHandoverEnergy,
                this.mutationHandler,
                this.changeOrientationHandler,
                this.changePositionHandler);

        //wyjątek na umieszczenie poza mapą

        this.map.placeAnimal(a1);
        this.animals.add(a1);
        this.map.placeAnimal(a2);
        this.animals.add(a2);
        this.map.placeAnimal(a3);
        this.animals.add(a3);
        this.map.placeAnimal(a4);
        this.animals.add(a4);

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
                    //System.out.println(map);
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
                    if(animal1.getEnergy() > breedHandoverEnergy && animal2.getEnergy() > breedHandoverEnergy){
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
}
