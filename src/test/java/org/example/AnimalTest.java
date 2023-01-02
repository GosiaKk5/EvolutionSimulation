package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void checkMoveAndPositionOrientation1() {

        System.out.println();
        System.out.println("-------------------------------------");

        int width = 100;
        int height = 100;
        IMap map = new ToxicCorpsesMap(width,height,0);
        Vector2d position = new Vector2d(2,2);
        int[] genotype = {0,2,2,2};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 5;
        int breedReadyEnergy = 5;
        int breedHandoverEnergy = 0;
        int minNumberOfMutations = 0;
        int maxNumberOfMutations = 4;
        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new Globe(width, height);

        Animal animal = new Animal(map,
                position,
                genotype,
                genotypeLength,
                indexOfActiveGen,
                energy,
                breedReadyEnergy,
                breedHandoverEnergy,
                minNumberOfMutations,
                maxNumberOfMutations,
                mutationHandler,
                orientationHandler,
                positionHandler);

        Vector2d[] positionCheck = {
                new Vector2d(2,3),
                new Vector2d(3,3),
                new Vector2d(3,2),
                new Vector2d(2,2),
                new Vector2d(1,2),
                new Vector2d(1,3)
        };
        int[] orientationCheck = {2,4,6,6,0,2};


        System.out.println(animal.getPosition());
        for(int i = 0; i < positionCheck.length; i++){
            System.out.println("i: " + i);
            System.out.println("ORIENTATION: " + animal.getOrientation());
            animal.move();
            animal.changeOrientation();

            System.out.println("POSITION: " + animal.getPosition());

            assertEquals(animal.getPosition(), positionCheck[i]);
            assertEquals(animal.getOrientation(), orientationCheck[i]);

            System.out.println();
        }
    }

    @Test
    void checkMoveAndPositionOrientation2() {

        System.out.println();
        System.out.println("-------------------------------------");

        int width = 100;
        int height = 100;
        IMap map = new ToxicCorpsesMap(width,height,0);
        Vector2d position = new Vector2d(0,0);
        int[] genotype = {0,1,2,3,3,4,5,6,1,3};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 5;
        int breedReadyEnergy = 5;
        int breedHandoverEnergy = 0;
        int minNumberOfMutations = 0;
        int maxNumberOfMutations = 5;
        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new Globe(width, height);

        Animal animal = new Animal(map,
                                position,
                                genotype,
                                genotypeLength,
                                indexOfActiveGen,
                                energy,
                                breedReadyEnergy,
                                breedHandoverEnergy,
                                minNumberOfMutations,
                                maxNumberOfMutations,
                                mutationHandler,
                                orientationHandler,
                                positionHandler);

        Vector2d[] positonCheks = {
                new Vector2d(0,1),
                new Vector2d(1,2),
                new Vector2d(2,1),
                new Vector2d(1,1),
                new Vector2d(2,2),
                new Vector2d(1,1),
                new Vector2d(2,1),
                new Vector2d(2,2),
                new Vector2d(3,3),
                new Vector2d(3,2),
        };
        int[] orientationCheck = {1,3,6,1,5,2,0,1,4,4};


        System.out.println(animal.getPosition());
        for(int i = 0; i < genotype.length; i++){
            System.out.println("i: " + i);
            System.out.println("ORIENTATION: " + animal.getOrientation());
            animal.move();
            animal.changeOrientation();

            System.out.println("POSITION: " + animal.getPosition());

            assertEquals(animal.getPosition(), positonCheks[i]);
            assertEquals(animal.getOrientation(), orientationCheck[i]);

            System.out.println();
        }
    }

    @Test
    void checkIfEnergyWorksProperly(){
        System.out.println();
        System.out.println("-------------------------------------");

        int width = 100;
        int height = 100;
        IMap map = new ToxicCorpsesMap(width,width,0);
        Vector2d position = new Vector2d(2,2);
        int[] genotype = {0,2,2,2};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 5;
        int breedReadyEnergy = 5;
        int breedHandoverEnergy = 0;
        int minNumberOfMutations = 0;
        int maxNumberOfMutations = 4;

        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new Globe(width, height);

        Animal animal = new Animal(map,
                position,
                genotype,
                genotypeLength,
                indexOfActiveGen,
                energy,
                breedReadyEnergy,
                breedHandoverEnergy,
                minNumberOfMutations,
                maxNumberOfMutations,
                mutationHandler,
                orientationHandler,
                positionHandler);

        int[] energyChecks = {4,3,2,1,0};

        for(int i = 0; i < energyChecks.length; i++){
            animal.move();
            assertEquals(animal.getEnergy(), energyChecks[i]);
        }
    }

    //aby puścić CheckMinAndMaxNumberOfMutationNumber trzeba zmienić mutate w Animal na public
//    @Test
//    void CheckMinAndMaxNumberOfMutationNumber(){
//
//        int width = 100;
//        int height = 100;
//        IMap map = new ToxicCorpsesMap(width,width,0);
//        Vector2d position = new Vector2d(2,2);
//        int genotypeLength = 6;
//        int indexOfActiveGen = 0;
//        int energy = 5;
//        int breedReadyEnergy = 5;
//        int breedHandoverEnergy = 0;
//        int minNumberOfMutations = 0;
//        int maxNumberOfMutations = 0;
//
//        IMutationHandler mutationHandler = new FullRandomness();
//        IChangeOrientationHandler orientationHandler = new FullPredestination();
//        IChangePositionHandler positionHandler = new Globe(width, height);
//
//        Animal animal = new Animal(map,
//                position,
//                genotypeLength,
//                indexOfActiveGen,
//                energy,
//                breedReadyEnergy,
//                breedHandoverEnergy,
//                minNumberOfMutations,
//                maxNumberOfMutations,
//                mutationHandler,
//                orientationHandler,
//                positionHandler);
//
//        int[] genotype = animal.getGenotype();
//        int[] genotypeCopy = genotype.clone();
//
//        animal.mutate(genotype);
//
//        int changedGens = 0;
//
//        for(int i = 0; i < genotypeLength; i++){
//            if(genotype[i] != genotypeCopy[i]){
//                changedGens++;
//            }
//        }
//
//        System.out.println("BEFORE: " + Arrays.toString(genotypeCopy));
//        System.out.println("AFTER: " + Arrays.toString(genotype));
//
//        assertEquals(changedGens, 0);
//
//    }
//
//    @Test
//    void CheckMinAndMaxNumberOfMutationNumber2(){
//
//        int width = 100;
//        int height = 100;
//        IMap map = new ToxicCorpsesMap(width,width,0);
//        Vector2d position = new Vector2d(2,2);
//        int genotypeLength = 100;
//        int indexOfActiveGen = 0;
//        int energy = 5;
//        int breedReadyEnergy = 5;
//        int breedHandoverEnergy = 0;
//        int minNumberOfMutations = 3;
//        int maxNumberOfMutations = 7;
//
//        IMutationHandler mutationHandler = new FullRandomness();
//        IChangeOrientationHandler orientationHandler = new FullPredestination();
//        IChangePositionHandler positionHandler = new Globe(width, height);
//
//        Animal animal = new Animal(map,
//                position,
//                genotypeLength,
//                indexOfActiveGen,
//                energy,
//                breedReadyEnergy,
//                breedHandoverEnergy,
//                minNumberOfMutations,
//                maxNumberOfMutations,
//                mutationHandler,
//                orientationHandler,
//                positionHandler);
//
//        int[] genotype = animal.getGenotype();
//        int[] genotypeCopy = genotype.clone();
//
//        animal.mutate(genotype);
//
//        int changedGens = 0;
//
//        for(int i = 0; i < genotypeLength; i++){
//            if(genotype[i] != genotypeCopy[i]){
//                changedGens++;
//            }
//        }
//
//        System.out.println("BEFORE: " + Arrays.toString(genotypeCopy));
//        System.out.println("AFTER: " + Arrays.toString(genotype));
//
//        assertTrue( 3 <= changedGens && changedGens <= 7);
//
//    }

}