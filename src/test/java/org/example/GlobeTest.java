package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobeTest {

    @Test
    void canGoOutOfUpperAndLowerBound(){

        System.out.println();
        System.out.println("-------------------------------------");

        int width = 5;
        int height = 5;
        IMap map = new ToxicCorpsesMap(width,height, 0);
        Vector2d position = new Vector2d(0,0);
        int[] genotype = {4,0,0,0,0,0,0,0,0,0,0};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 1;
        int breedEnergy = 5;
        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new Globe(width, height);

        Vector2d[] positionChecks = {
                new Vector2d(0,0),
                new Vector2d(0,1),
                new Vector2d(0,2),
                new Vector2d(0,3),
                new Vector2d(0,4),
                new Vector2d(0,4)
        };

        int[] orientationChecks = {0,0,0,0,0,4};

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

        map.placeAnimal(animal);

        System.out.println(map);
        for(int i = 0; i < positionChecks.length; i++){
            animal.move();
            //assertEquals(animal.getOrientation(), orientationChecks[i]);

            animal.changeOrientation();
            System.out.println(animal.getOrientation());

            //assertEquals(animal.getPosition(), positionChecks[i]);
            System.out.println(map);
        }
    }

    @Test
    void doesLeftAndRightLoop(){

        System.out.println();
        System.out.println("-------------------------------------");

        int width = 3;
        int height = 3;
        IMap map = new ToxicCorpsesMap(width,height, 0);
        Vector2d position = new Vector2d(0,0);
        int[] genotype = {6,2,2,4};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 1;
        int breedEnergy = 5;
        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new Globe(width, height);

        Vector2d[] positionChecks = {
                new Vector2d(2,0),
                new Vector2d(2,1),
                new Vector2d(0,1),
                new Vector2d(2,1)
        };

        int[] orientationChecks = {6,0,2,6};

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

        map.placeAnimal(animal);

        System.out.println(map);
        for(int i = 0; i < positionChecks.length; i++){

            animal.move();
            assertEquals(animal.getOrientation(), orientationChecks[i]);

            animal.changeOrientation();
            System.out.println(animal.getOrientation());

            assertEquals(animal.getPosition(), positionChecks[i]);
            System.out.println(map);
        }

        //ma sens tylko gdy w move nie ma -energia przy ruchu
        //assertEquals(animal.getEnergy(), energy);
    }
}