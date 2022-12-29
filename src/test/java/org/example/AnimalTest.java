package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void moveAndChangePositionSimpleTest() {

        System.out.println();
        System.out.println("-------------------------------------");

        IMap map = new ToxicCorpsesMap(100,100,0);
        Vector2d position = new Vector2d(0,0);
        int[] genotype = {0,2,2,2};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 5;
        int breedEnergy = 5;
        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new HellishPortal(100,100);

        Animal animal = new Animal(map,
                position,
                genotype,
                genotypeLength,
                indexOfActiveGen,
                energy,
                breedEnergy,
                mutationHandler,
                orientationHandler,
                positionHandler);

        Vector2d[] positionCheck = {
                new Vector2d(0,1),
                new Vector2d(1,1),
                new Vector2d(1,0),
                new Vector2d(0,0),
                new Vector2d(-1,0),
                new Vector2d(-1,1)
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
    void moveAndChangePositionSimpleTest2() {

        System.out.println();
        System.out.println("-------------------------------------");

        IMap map = new ToxicCorpsesMap(100,100,0);
        Vector2d position = new Vector2d(0,0);
        int[] genotype = {0,1,2,3,3,4,5,6,1,3};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 5;
        int breedEnergy = 5;
        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new HellishPortal(100,100);

        Animal animal = new Animal(map,
                                position,
                                genotype,
                                genotypeLength,
                                indexOfActiveGen,
                                energy,
                                breedEnergy,
                                mutationHandler,
                                orientationHandler,
                                positionHandler);

        Vector2d[] positionCheks = {
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

            assertEquals(animal.getPosition(), positionCheks[i]);
            assertEquals(animal.getOrientation(), orientationCheck[i]);

            System.out.println();
        }
    }
}