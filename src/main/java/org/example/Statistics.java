package org.example;

import java.util.*;

public class Statistics {

    private int noDeadAnimals;
    private int deadAnimalsAgeSum;
    private final SimulationEngine engine;
    private final IMap map;
    private int[] mostPopularGenotype;

    public Statistics(IMap map, SimulationEngine engine){
        this.engine = engine;
        this.map = map;
        this.noDeadAnimals = 0;
        this.deadAnimalsAgeSum = 0;
    }
    public int getNoAnimals(){
        return engine.getAnimals().size();
    }
    public int getNoPlants(){
        return map.getPlants().size();
    }
    public int getNoFreeFields(){
        return map.getNoFreeFields();
    }
    public double getAvgEnergy(){
        int sumEnergy = 0;

        for(Animal animal : engine.getAnimals()){
            sumEnergy += animal.getEnergy();
        }
        if (getNoAnimals() > 0){
            double avg = (double)sumEnergy/this.getNoAnimals();
            avg *= 100;
            avg = (double)Math.round(avg);
            return avg / 100;
        }
        return -1;
    }
    public double getAvgDeathAge(){
        if(this.noDeadAnimals > 0){
            double avg = (double)this.deadAnimalsAgeSum/this.noDeadAnimals;
            avg *= 100;
            avg = (double)Math.round(avg);
            return avg / 100;
        }
        return -1;
    }
    public int[] getTheMostPopularGenotype(){
        HashMap<int[], Integer> genotypesCounter = new HashMap<>();
        for(Animal animal : engine.getAnimals()){
            int[] genotype = animal.getGenotype();
            if(genotypesCounter.containsKey(genotype)){
                genotypesCounter.put(genotype, genotypesCounter.get(genotype) + 1);
            }else{
                genotypesCounter.put(genotype, 1);
            }
        }

        Map.Entry<int[], Integer> mostPopular = null;

        for(Map.Entry<int[], Integer> e : genotypesCounter.entrySet()){
            if (mostPopular == null || e.getValue() > mostPopular.getValue())
                mostPopular = e;
        }

        if(mostPopular != null){
            this.mostPopularGenotype = mostPopular.getKey();
            return this.mostPopularGenotype;
        }else{
            this.mostPopularGenotype = null;
            return null;
        }
    }
    public ArrayList<Animal> getAnimalsWithMostPopular(){
        ArrayList<Animal> popularGenotypeAnimals = new ArrayList<>();

        System.out.println(this.engine.getAnimals());
        for(Animal animal : this.engine.getAnimals()){


            if(Arrays.equals(animal.getGenotype(), this.mostPopularGenotype)){
                popularGenotypeAnimals.add(animal);
            }

        }

        return popularGenotypeAnimals;
    }
    public void isDead(Animal animal){
        this.deadAnimalsAgeSum += animal.getAge();
        this.noDeadAnimals ++;
    }
}
