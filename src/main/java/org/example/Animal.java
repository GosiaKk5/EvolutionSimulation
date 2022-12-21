package org.example;

import java.util.*;
import java.util.stream.IntStream;

public class Animal {
    private final int[] genotype;

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    private Vector2d position;
    private int orientation;
    private final int genotypeLength;
    private int energy;
    private final IMutationHandler mutationHandler;
    private final IChangePositionHandler positionHandler;
    private final int breedEnergy;
    private int indexOfActiveGen;


    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getIndexOfActiveGen() {
        return indexOfActiveGen;
    }

    public void setIndexOfActiveGen(int indexOfActiveGen) {
        this.indexOfActiveGen = indexOfActiveGen;
    }

    public Animal(int[] genotype, int energy, Vector2d position, IMutationHandler mutationHandler, IChangePositionHandler positionHandler, int genotypeLength, int breedEnergy) {
        this.genotype = genotype;
        this.position = position;
        this.energy = energy;
        this.mutationHandler = mutationHandler;
        this.positionHandler = positionHandler;
        this.genotypeLength = genotypeLength;
        this.breedEnergy = breedEnergy;
        this.indexOfActiveGen = 0;
        this.orientation = 0;
    }
    public Animal(int energy, Vector2d position, IMutationHandler mutationHandler, IChangePositionHandler positionHandler, int genotypeLength, int breedEnergy){
        int[] genotype = this.getRandomGenotype(genotypeLength);
        this.genotype = genotype;
        this.position = position;
        this.energy = energy;
        this.mutationHandler = mutationHandler;
        this.positionHandler = positionHandler;
        this.genotypeLength = genotypeLength;
        this.breedEnergy = breedEnergy;
        this.indexOfActiveGen = 0;
        this.orientation = 0;
    }

    private int[] getRandomGenotype(int genotypeLength) {

        Random random = new Random();

        int[] randomGenotype = new int[genotypeLength];
        for (int i = 0; i < genotypeLength; i++){
            int n = random.nextInt(0,8);
            randomGenotype[i] = n;
        };
        return randomGenotype;
    }
    public Animal breedNewAnimal(Animal otherAnimal) {

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

        Animal newAnimal = new Animal(genotypeForChild, this.breedEnergy*2, new Vector2d(0,0), this.mutationHandler, this.positionHandler, this.genotypeLength, this.breedEnergy);

        //set parents energy
        strongerAnimal.energy -= this.breedEnergy;
        weakerAnimal.energy -= this.breedEnergy;

        return newAnimal;
    }
    public void changeOrientation(){
        int nextIndexOfActiveGen = this.positionHandler.changePosition(this);
        this.orientation = (this.orientation + this.genotype[nextIndexOfActiveGen]) % 8;
        this.indexOfActiveGen = nextIndexOfActiveGen;
//        System.out.println("INDEX: " + this.indexOfActiveGen);
//        System.out.println("ORIENTATION: " + this.orientation);
    }
    public void move(){

        Vector2d newPosition = this.position;

        switch (this.orientation) {
            case 0 -> { newPosition = this.position.add(new Vector2d(0,1)); }
            case 1 -> { newPosition = this.position.add(new Vector2d(1,1)); }
            case 2 -> { newPosition = this.position.add(new Vector2d(1,0)); }
            case 3 -> { newPosition = this.position.add(new Vector2d(1,-1)); }
            case 4 -> { newPosition = this.position.add(new Vector2d(0,-1)); }
            case 5 -> { newPosition = this.position.add(new Vector2d(-1,-1)); }
            case 6 -> { newPosition = this.position.add(new Vector2d(-1,0)); }
            case 7 -> { newPosition = this.position.add(new Vector2d(-1,1)); }
            default -> {
                System.out.println("NIEPRAWIDLOWY !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }

        this.position = newPosition;
        System.out.println(this.position);

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
            genotypeToMutate =  concatenateGenotypes(strongerAnimal.genotype, strongerSideLength, weakerAnimal.genotype);
        } else {
            genotypeToMutate =  concatenateGenotypes(weakerAnimal.genotype, weakerSideLength, strongerAnimal.genotype);
        }

        int[] mutatedGenotype = this.mutate(genotypeToMutate);
        return mutatedGenotype;
    }
    private int[] concatenateGenotypes(int[] leftSide, int leftSideLength, int[] rightSide) {
        return IntStream.concat(Arrays.stream(Arrays.copyOfRange(leftSide, 0, leftSideLength)), Arrays.stream(Arrays.copyOfRange(rightSide, leftSideLength, genotypeLength))).toArray();
    }
    private int[] mutate(int[] genotype) {

        System.out.println("BEFORE MUTATION: " + Arrays.toString(genotype));

        //generate number of mutations
        Random random = new Random();
        int numberOfMutatingGens = random.nextInt(0, genotypeLength + 1);

        List<Integer> gensToMutate = this.getIndexesOfGensToMutate(this.genotypeLength, numberOfMutatingGens);

        System.out.println("NUMBER OF GENS TO MUTATE: " + numberOfMutatingGens);
        System.out.println("INDEXES OF GENS TO MUTATE: " + gensToMutate);

        genotype = this.mutationHandler.mutate(genotype, gensToMutate);

        System.out.println("AFTER MUTATION: " + Arrays.toString(genotype));

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
    };

    @Override
    public String toString() {
        String genotypeString = "";
        for (Integer gen : this.genotype) {
            genotypeString += gen;
        }
        return "(%s, energia: %d)".formatted(genotypeString, this.energy);
    }


    public int getGenotypeLength() {
        return genotypeLength;
    }
}










//package org.example;
//
//        import java.util.ArrayList;
//        import java.util.Arrays;
//        import java.util.List;
//        import java.util.Random;
//        import java.util.stream.IntStream;
//
//public class Animal {
//    private int[] genotype;
//    private int energy;
//
//    public int[] getGenotype() {
//        return genotype;
//    }
//
//    public int getEnergy() {
//        return energy;
//    }
//
//    public Animal(int[] genotype, int energy){
//        this.genotype = genotype;
//        this.energy = energy;
//    }
//    @Override
//    public String toString(){
//        String genotypeString = "";
//        for (Integer gen : this.genotype){
//            genotypeString += gen;
//        }
//        return "(%s, energia: %d)".formatted(genotypeString,this.energy);
//    }
//    public void breedNewAnimal(Animal animal1, Animal animal2){
//        Animal strongerAnimal;
//        Animal weakerAnimal;
//
//        //set stronger animal
//        if (animal1.energy > animal2.energy){
//            strongerAnimal = animal1;
//            weakerAnimal = animal2;
//        }
//        else{
//            strongerAnimal = animal2;
//            weakerAnimal = animal1;
//        }
//
//        boolean strongerGenotypeOnLeft = this.chooseSideForStrongerAnimal();
//        int[] newGenotype = createGenotype(strongerAnimal, weakerAnimal, strongerGenotypeOnLeft);
//        System.out.println(Arrays.toString(newGenotype));
//        //mutate
//        //create animal
//        //update energy
//        //return animal
//    }
//    private boolean chooseSideForStrongerAnimal(){
//        Random random = new Random();
//        return random.nextInt(0, 2) == 0;
//    }
//    //public createGenotype do testów - trzeba będzie zmienić
//    public int[] createGenotype(Animal strongerAnimal, Animal weakerAnimal, boolean strongerGenotypeOnLeft){
//
//        int genotypeLength = this.genotype.length;
//
//        int strongerSideLength = (int) Math.round(((double)strongerAnimal.energy / (double)(strongerAnimal.energy + weakerAnimal.energy)) * (double)genotypeLength);
//        int weakerSideLength = genotypeLength - strongerSideLength;
//
//        if (strongerGenotypeOnLeft){
//            return concatenateGenotypes(strongerAnimal.genotype, strongerSideLength, weakerAnimal.genotype, genotypeLength);
//        }
//        else {
//            return concatenateGenotypes(weakerAnimal.genotype, weakerSideLength, strongerAnimal.genotype, genotypeLength);
//        }
//    }
//    private int[] concatenateGenotypes(int[] leftSide, int leftSideLength, int[] rightSide, int genotypeLength) {
//        return IntStream.concat(Arrays.stream(Arrays.copyOfRange(leftSide, 0, leftSideLength)), Arrays.stream(Arrays.copyOfRange(rightSide, leftSideLength, genotypeLength))).toArray();
//    }
//    private void mutate(Animal animal){
//
//        //System.out.println("BEFORE MUTATION: " + Arrays.toString(animal.genotype));
//
//        //generate number of mutations
//        Random random = new Random();
//        int numberOfMutatingGens = random.nextInt(0, animal.genotype.length + 1);
//
//        List<Integer> gensToMutate = this.getIndexesOfGensToMutate(animal.genotype.length, numberOfMutatingGens);
//
//        //System.out.println("NUMBER OF GENS TO MUTATE: " + numberOfMutatingGens);
//        //System.out.println("INDEXES OF GENS TO MUTATE: " + gensToMutate);
//
//        this.mutationToAnyOtherGen(gensToMutate, animal);
//        //this.mutationUpOrDown(gensToMutate, animal);
//
//        //System.out.println("AFTER MUTATION: " + Arrays.toString(animal.genotype));
//
//    }
//    private List<Integer> getIndexesOfGensToMutate(int genotypeLength, int numberOfMutatingGens){
//        List<Integer> indexes = new ArrayList<>();
//        Random random = new Random();
//
//        for (int i = 0; i < genotypeLength; i++){
//            indexes.add(i);
//        }
//
//        List<Integer> gensToMutate = new ArrayList<>();
//        for (int i = 0; i < numberOfMutatingGens; i++){
//            int index = indexes.remove(random.nextInt(0,indexes.size()));
//            gensToMutate.add(index);
//        }
//
//        return gensToMutate;
//    };
//    private void mutationToAnyOtherGen(List<Integer> gensToMutate, Animal animal){
//        Random random = new Random();
//
//        List<Integer> gens = new ArrayList<>();
//        for (int i = 0; i <= 7; i++){
//            gens.add(i);
//        }
//
//        for (int index : gensToMutate){
//            int removedGen = gens.remove(animal.genotype[index]);
//            animal.genotype[index] = gens.get(random.nextInt(0,7));
//            //removedGen goes back to the original index
//            gens.add(removedGen);
//        }
//    }
//    private void mutationUpOrDown(List<Integer> gensToMutate, Animal animal){
//        Random random = new Random();
//        int[] upOrDown = {-1, 1};
//        for (int index : gensToMutate){
//            int difference = upOrDown[random.nextInt(0,2)];
//            animal.genotype[index] += difference;
//        }
//    }
//}

