package org.example;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements Runnable {

    static final int MOVE_DELAY = 400;
    private final int height;
    private final int width;
    private final int plantEnergy;
    private final int numberOfPlantsGrowDaily;
    private final int numberOfStartAnimals;
    private final int startEnergy;
    private final int breedReadyEnergy;
    private final int breedHandoverEnergy;
    private final int minNumberOfMutations;
    private final int maxNumberOfMutations;
    private final int genotypeLength;
    private final IChangePositionHandler changePositionHandler;
    private final IChangeOrientationHandler changeOrientationHandler;
    private final IMutationHandler mutationHandler;
    private final IMap map;
    private final List<Animal> animals;
    private boolean paused;
    private final ArrayList<INextSimulationDayObserver> observers = new ArrayList<>();
    public final Statistics statistic;
    private int noDay;


    public IMap getMap() {
        return map;
    }

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
        this.noDay = 0;

        this.addAnimals();

        this.dayChanged();

    }

    private void addAnimals(){

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
            animal.setDeathAge(this.noDay);
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
                        animal.noEatenPlantsAddOne();
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
    public void addObserver(INextSimulationDayObserver observer) {
        observers.add(observer);
    }
    private void dayChanged() {
        for (INextSimulationDayObserver observer : observers) {
            observer.dayChanged();
            this.noDay ++;
        }
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    public List<Animal> getAnimals(){
        return this.animals;
    }
}
