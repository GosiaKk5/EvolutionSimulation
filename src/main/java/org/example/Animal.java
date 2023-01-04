package org.example;

import java.util.*;
import java.util.stream.IntStream;

public class Animal implements IMapElement {
    private final IMap map;
    private Vector2d position;
    private int orientation;
    private final int[] genotype;
    private final int genotypeLength;
    private int indexOfActiveGen;
    private int energy;
    private final int breedReadyEnergy;
    private final int breedHandoverEnergy;
    private final int minNumberOfMutations;
    private final int maxNumberOfMutations;
    private final IMutationHandler mutationHandler;
    private final IChangeOrientationHandler orientationHandler;
    private final IChangePositionHandler positionHandler;
    private final List<IPositionChangeObserver> observers;

    private int age;
    private int noChildren;

    private int noEatenPlants;

    public Vector2d getPosition() { return position; }
    public int getOrientation() {
        return orientation;
    }
    public int getIndexOfActiveGen() {
        return indexOfActiveGen;
    }
    public int getGenotypeLength() {
        return genotypeLength;
    }
    public int[] getGenotype(){return genotype;}
    public int getEnergy() {
        return this.energy;
    }
    public int getAge(){ return this.age; }
    public int getNoChildren(){ return this.noChildren; }
    public int getNoEatenPlants(){ return this.noEatenPlants; };



    //konstruktor używany do testów
    public Animal(IMap map,
                  Vector2d position,
                  int[] genotype,
                  int genotypeLength,
                  int indexOfActiveGen,
                  int energy,
                  int breedReadyEnergy,
                  int breedHandoverEnergy,
                  int minNumberOfMutations,
                  int maxNumberOfMutations,
                  IMutationHandler mutationHandler,
                  IChangeOrientationHandler orientationHandler,
                  IChangePositionHandler positionHandler){
        this.map = map;
        this.position = position;
        this.orientation = genotype[indexOfActiveGen];
        this.genotype = genotype;
        this.genotypeLength = genotypeLength;
        this.indexOfActiveGen = indexOfActiveGen;
        this.energy = energy;
        this.breedReadyEnergy = breedReadyEnergy;
        this.breedHandoverEnergy = breedHandoverEnergy;
        this.mutationHandler = mutationHandler;
        this.positionHandler = positionHandler;
        this.orientationHandler = orientationHandler;
        this.observers = new ArrayList<>();
        this.age = 0;
        this.noChildren = 0;
        this.noEatenPlants = 0;
        this.minNumberOfMutations = minNumberOfMutations;
        this.maxNumberOfMutations = maxNumberOfMutations;
    }

    //konstruktor przypisujący losowy genotyp (będzie potrzebny do simulation engine)
    public Animal(IMap map,
                  Vector2d position,
                  int genotypeLength,
                  int indexOfActiveGen,
                  int energy,
                  int breedReadyEnergy,
                  int breedHandoverEnergy,
                  int minNumberOfMutations,
                  int maxNumberOfMutations,
                  IMutationHandler mutationHandler,
                  IChangeOrientationHandler orientationHandler,
                  IChangePositionHandler positionHandler){
        this.map = map;
        this.position = position;
        this.genotype = this.getRandomGenotype(genotypeLength);
        this.orientation = genotype[indexOfActiveGen];
        this.genotypeLength = genotypeLength;
        this.indexOfActiveGen = indexOfActiveGen;
        this.energy = energy;
        this.breedReadyEnergy = breedReadyEnergy;
        this.breedHandoverEnergy = breedHandoverEnergy;
        this.minNumberOfMutations = minNumberOfMutations;
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.mutationHandler = mutationHandler;
        this.positionHandler = positionHandler;
        this.orientationHandler = orientationHandler;
        this.observers = new ArrayList<>();
        this.age = 0;
        this.noChildren = 0;
        this.noEatenPlants = 0;
    }
    private int[] getRandomGenotype(int genotypeLength) {

        Random random = new Random();

        int[] randomGenotype = new int[genotypeLength];
        for (int i = 0; i < genotypeLength; i++) {
            int n = random.nextInt(0, 8);
            randomGenotype[i] = n;
        }

        return randomGenotype;
    }
    public void ageAddOne(){
        this.age += 1;
    }

    public void noEatenPlantsAddOne(){
        this.noEatenPlants ++;
    }
    public void changeEnergy(int amountOfEnergy){
        this.energy += amountOfEnergy;
    }
    public Animal breedNewAnimal(Animal otherAnimal){

        // dodanie zwierzakom liczby dzieci
        this.noChildren ++;
        otherAnimal.noChildren ++;
        //

        Animal strongerAnimal;
        Animal weakerAnimal;

        //set stronger animal
        if (this.energy > otherAnimal.energy) {
            strongerAnimal = this;
            weakerAnimal = otherAnimal;
        } else {
            strongerAnimal = otherAnimal;
            weakerAnimal = this;
        }

        boolean strongerGenotypeOnLeft = this.chooseSideForStrongerAnimal();
        int[] genotypeForChild = createGenotypeFromAnimals(strongerAnimal, weakerAnimal, strongerGenotypeOnLeft);

        Animal newAnimal = new Animal(this.map,
                                        this.position,
                                        genotypeForChild,
                                        this.genotypeLength,
                                        0,
                                        this.breedHandoverEnergy * 2,
                                        this.breedReadyEnergy,
                                        this.breedHandoverEnergy,
                                        this.minNumberOfMutations,
                                        this.maxNumberOfMutations,
                                        this.mutationHandler,
                                        this.orientationHandler,
                                        this.positionHandler);

        //set parents energy
        strongerAnimal.energy -= this.breedHandoverEnergy;
        weakerAnimal.energy -= this.breedHandoverEnergy;

        return newAnimal;
    }
    public void changeOrientation(){

        int nextIndexOfActiveGen = this.orientationHandler.changeOrientation(this);
        this.orientation = (this.orientation + this.genotype[nextIndexOfActiveGen]) % 8;
        this.indexOfActiveGen = nextIndexOfActiveGen;
    }
    public void move() {

        this.energy -= 1;

        Vector2d newPosition = this.position;
        int newOrientation = this.orientation;
        int newEnergy = this.energy;

        switch (this.orientation) {
            case 0 -> newPosition = this.position.add(new Vector2d(0, 1));
            case 1 -> newPosition = this.position.add(new Vector2d(1, 1));
            case 2 -> newPosition = this.position.add(new Vector2d(1, 0));
            case 3 -> newPosition = this.position.add(new Vector2d(1, -1));
            case 4 -> newPosition = this.position.add(new Vector2d(0, -1));
            case 5 -> newPosition = this.position.add(new Vector2d(-1, -1));
            case 6 -> newPosition = this.position.add(new Vector2d(-1, 0));
            case 7 -> newPosition = this.position.add(new Vector2d(-1, 1));
            default -> System.out.println("NIEPRAWIDLOWY GEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?");
        }

        if(!map.isPositionInMapBounds(newPosition)){
            newPosition = this.positionHandler.getNewPositionInMap(newPosition, this.position, this.orientation);
            newOrientation = this.positionHandler.getNewOrientation();
            newEnergy = this.positionHandler.getNewEnergy(this.energy);
        }

        this.positionChanged(this.position, newPosition);
        this.position = newPosition;
        this.orientation = newOrientation;
        this.energy = newEnergy;
    }

    //ASIDE BREEDING FUNCTIONS////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean chooseSideForStrongerAnimal(){

        Random random = new Random();
        return random.nextInt(0, 2) == 0;
    }
    private int[] createGenotypeFromAnimals(Animal strongerAnimal, Animal weakerAnimal, boolean strongerGenotypeOnLeft){

        int strongerSideLength = (int) Math.round(((double) strongerAnimal.energy / (double) (strongerAnimal.energy + weakerAnimal.energy)) * (double) genotypeLength);
        int weakerSideLength = this.genotypeLength - strongerSideLength;

        int[] genotypeToMutate;
        if (strongerGenotypeOnLeft) {
            genotypeToMutate = concatenateGenotypes(strongerAnimal.genotype, strongerSideLength, weakerAnimal.genotype);
        } else {
            genotypeToMutate = concatenateGenotypes(weakerAnimal.genotype, weakerSideLength, strongerAnimal.genotype);
        }

        int[] mutatedGenotype = this.mutate(genotypeToMutate);
        return mutatedGenotype;
    }
    private int[] concatenateGenotypes(int[] leftSide, int leftSideLength, int[] rightSide){

        return IntStream.concat(Arrays.stream(Arrays.copyOfRange(leftSide, 0, leftSideLength)), Arrays.stream(Arrays.copyOfRange(rightSide, leftSideLength, genotypeLength))).toArray();
    }
    private int[] mutate(int[] genotype){

        //System.out.println("BEFORE MUTATION: " + Arrays.toString(genotype));

        //generate number of mutations
        Random random = new Random();
        int numberOfMutatingGens = random.nextInt(minNumberOfMutations, maxNumberOfMutations + 1);

        List<Integer> gensToMutate = this.getIndexesOfGensToMutate(this.genotypeLength, numberOfMutatingGens);

        //System.out.println("NUMBER OF GENS TO MUTATE: " + numberOfMutatingGens);
        //System.out.println("INDEXES OF GENS TO MUTATE: " + gensToMutate);

        genotype = this.mutationHandler.mutate(genotype, gensToMutate);

        //System.out.println("AFTER MUTATION: " + Arrays.toString(genotype));

        return genotype;
    }
    private List<Integer> getIndexesOfGensToMutate(int genotypeLength, int numberOfMutatingGens){

        List<Integer> indexes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < genotypeLength; i++) {
            indexes.add(i);
        }

        List<Integer> gensToMutate = new ArrayList<>();
        for (int i = 0; i < numberOfMutatingGens; i++) {
            int index = indexes.remove(random.nextInt(0, indexes.size()));
            gensToMutate.add(index);
        }

        return gensToMutate;
    }

    @Override
    public String toString() {
        return "%d".formatted(this.energy);
    }
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){

        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }
}
