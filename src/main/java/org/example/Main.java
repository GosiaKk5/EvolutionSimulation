package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

//        //SHOW BREEDING
//        IMap map = new ToxicCorpsesMap(5,5,0);
//        Vector2d position = new Vector2d(0,0);
//        int[] genotype1 = {0,0,0,0,0};
//        int[] genotype2 = {1,1,1,1,1};
//        int genotypeLength = genotype1.length;
//        int indexOfActiveGen = 0;
//        int energy1 = 15;
//        int energy2 = 5;
//        int breedEnergy = 1;
//        IMutationHandler mutationHandler = new FullRandomness();
//        IChangeOrientationHandler orientationHandler = new FullPredestination();
//        IChangePositionHandler positionHandler = new HellishPortal(100,100);
//
//        Animal a1 = new Animal(map,
//                                position,
//                                genotype1,
//                                genotypeLength,
//                                indexOfActiveGen,
//                                energy1,
//                                breedEnergy,
//                                mutationHandler,
//                                orientationHandler,
//                                positionHandler);
//
//        Animal a2 = new Animal(map,
//                position,
//                genotype2,
//                genotypeLength,
//                indexOfActiveGen,
//                energy2,
//                breedEnergy,
//                mutationHandler,
//                orientationHandler,
//                positionHandler);
//
//        Animal children = a1.breedNewAnimal(a2);
//        System.out.println(children);


//        IMap map = new ToxicCorpsesMap(10,7, 10);
//        Vector2d position = new Vector2d(0,0);
//        int[] genotype = {0,1,2,3,3,4,5,6,1,3};
//        int genotypeLength = genotype.length;
//        int indexOfActiveGen = 0;
//        int energy1 = 1;
//        int energy2 = 5;
//        int breedEnergy = 5;
//        IMutationHandler mutationHandler = new FullRandomness();
//        IChangeOrientationHandler orientationHandler = new LittleCraziness();
//        IChangePositionHandler positionHandler = new HellishPortal();
//
//        Animal a1 = new Animal(map,
//                            position,
//                            genotype,
//                            genotypeLength,
//                            indexOfActiveGen,
//                            energy1,
//                            breedEnergy,
//                            mutationHandler,
//                            orientationHandler,
//                            positionHandler);
//
//        Animal a2 = new Animal(map,
//                position,
//                genotype,
//                genotypeLength,
//                indexOfActiveGen,
//                energy2,
//                breedEnergy,
//                mutationHandler,
//                orientationHandler,
//                positionHandler);
//
//        map.placeAnimal(a1);
//        map.placeAnimal(a2);
//        System.out.println(map.animalsAt(position));
//
//        System.out.println(map);
//        a1.move();
//        a1.changeOrientation();
//
//        System.out.println(map);
//        a1.move();
//        a1.changeOrientation();
//
//        System.out.println(map);


        int height = 5;
        int width = 5;
        String variantMap = "Globe";
        int numberOfStartPlants = 0;
        int plantEnergy = 0;
        int numberOfPlantsGrowDaily = 0;
        String variantGrowingPlants = "EquatorialForest";
        int numberOfStartAnimals = 0;
        int startEnergy = 8;
        int breedReadyEnergy = 6;
        int breedHandoverEnergy = 5;
        int minNumberOfMutations = 0;
        int maxNumberOfMutations = 0;
        String variantMutation = "FullRandomness";
        int genotypeLength = 1;
        String variantOrientation = "FullPredestination";

        SimulationEngine engine = new SimulationEngine(height,
                                width,
                                variantMap,
                                numberOfStartPlants,
                                plantEnergy,
                                numberOfPlantsGrowDaily,
                                variantGrowingPlants,
                                numberOfStartAnimals,
                                startEnergy,
                                breedReadyEnergy,
                                breedHandoverEnergy,
                                minNumberOfMutations,
                                maxNumberOfMutations,
                                variantMutation,
                                genotypeLength,
                                variantOrientation);
        engine.run();
    }
}