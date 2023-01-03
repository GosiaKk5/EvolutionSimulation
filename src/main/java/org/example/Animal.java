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
    private final int breedEnergy;
    private final IMutationHandler mutationHandler;
    private final IChangeOrientationHandler orientationHandler;
    private final IChangePositionHandler positionHandler;
    private final List<IPositionChangeObserver> observers;

    private int age;
    private int noChildren;

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
    public int getBreedEnergy() {
        return this.breedEnergy;
    }
    public int getAge(){ return this.age; }
    public int getNoChildren(){ return this.noChildren; }



    //konstruktor ogólny
    public Animal(IMap map,
                  Vector2d position,
                  int[] genotype,
                  int genotypeLength,
                  int indexOfActiveGen,
                  int energy,
                  int breedEnergy,
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
        this.breedEnergy = breedEnergy;
        this.mutationHandler = mutationHandler;
        this.positionHandler = positionHandler;
        this.orientationHandler = orientationHandler;
        this.observers = new ArrayList<>();
        this.age = 0;
        this.noChildren = 0;
    }

    //konstruktor przypisujący losowy genotyp (będzie potrzebny do simulation engine)
    public Animal(IMap map,
                  Vector2d position,
                  int genotypeLength,
                  int indexOfActiveGen,
                  int energy,
                  int breedEnergy,
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
        this.breedEnergy = breedEnergy;
        this.mutationHandler = mutationHandler;
        this.positionHandler = positionHandler;
        this.orientationHandler = orientationHandler;
        this.observers = new ArrayList<>();
    }
    private int[] getRandomGenotype(int genotypeLength) {

        Random random = new Random();

        int[] randomGenotype = new int[genotypeLength];
        for (int i = 0; i < genotypeLength; i++) {
            int n = random.nextInt(0, 8);
            randomGenotype[i] = n;
        }
        ;
        return randomGenotype;
    }

    public void ageAddOne(){
        this.age += 1;
    }

    public void changeEnergy(int amountOfEnergy){
        this.energy += amountOfEnergy;
    }

    public Animal breedNewAnimal(Animal otherAnimal) {

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
                                        this.breedEnergy * 2,
                                        this.breedEnergy,
                                        this.mutationHandler,
                                        this.orientationHandler,
                                        this.positionHandler);
        //Animal newAnimal = new Animal(genotypeForChild, this.breedEnergy * 2, new Vector2d(0, 0), this.mutationHandler, this.orientationHandler, this.genotypeLength, this.breedEnergy);

        //set parents energy
        strongerAnimal.energy -= this.breedEnergy;
        weakerAnimal.energy -= this.breedEnergy;

        return newAnimal;
    }

    public void changeOrientation() {
        int nextIndexOfActiveGen = this.orientationHandler.changeOrientation(this);
        this.orientation = (this.orientation + this.genotype[nextIndexOfActiveGen]) % 8;
        this.indexOfActiveGen = nextIndexOfActiveGen;
//        System.out.println("INDEX: " + this.indexOfActiveGen);
//        System.out.println("ORIENTATION: " + this.orientation);
    }

    public void move() {

        this.energy -= 1;

        Vector2d newPosition = this.position;
        int newOrientation = this.orientation;
        int newEnergy = this.energy;

        switch (this.orientation) {
            case 0 -> { newPosition = this.position.add(new Vector2d(0, 1)); }
            case 1 -> { newPosition = this.position.add(new Vector2d(1, 1)); }
            case 2 -> { newPosition = this.position.add(new Vector2d(1, 0)); }
            case 3 -> { newPosition = this.position.add(new Vector2d(1, -1)); }
            case 4 -> { newPosition = this.position.add(new Vector2d(0, -1)); }
            case 5 -> { newPosition = this.position.add(new Vector2d(-1, -1));}
            case 6 -> { newPosition = this.position.add(new Vector2d(-1, 0)); }
            case 7 -> { newPosition = this.position.add(new Vector2d(-1, 1)); }
            default -> {
                System.out.println("NIEPRAWIDLOWY GEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?");
            }
        }

        if(!map.inMap(newPosition)){
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
    private boolean chooseSideForStrongerAnimal() {
        Random random = new Random();
        return random.nextInt(0, 2) == 0;
    }

    private int[] createGenotypeFromAnimals(Animal strongerAnimal, Animal weakerAnimal, boolean strongerGenotypeOnLeft) {

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

    private int[] concatenateGenotypes(int[] leftSide, int leftSideLength, int[] rightSide) {
        return IntStream.concat(Arrays.stream(Arrays.copyOfRange(leftSide, 0, leftSideLength)), Arrays.stream(Arrays.copyOfRange(rightSide, leftSideLength, genotypeLength))).toArray();
    }

    private int[] mutate(int[] genotype) {

        //System.out.println("BEFORE MUTATION: " + Arrays.toString(genotype));

        //generate number of mutations
        Random random = new Random();
        int numberOfMutatingGens = random.nextInt(0, genotypeLength + 1);

        List<Integer> gensToMutate = this.getIndexesOfGensToMutate(this.genotypeLength, numberOfMutatingGens);

        //System.out.println("NUMBER OF GENS TO MUTATE: " + numberOfMutatingGens);
        //System.out.println("INDEXES OF GENS TO MUTATE: " + gensToMutate);

        genotype = this.mutationHandler.mutate(genotype, gensToMutate);

        //System.out.println("AFTER MUTATION: " + Arrays.toString(genotype));

        return genotype;
    }

    private List<Integer> getIndexesOfGensToMutate(int genotypeLength, int numberOfMutatingGens) {
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
//        String genotypeString = "";
//        for (Integer gen : this.genotype) {
//            genotypeString += gen;
//        }
        //return "(%s, energia: %d)".formatted(genotypeString, this.energy);
        //return "A";
        return "%d".formatted(this.energy);
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }
}
