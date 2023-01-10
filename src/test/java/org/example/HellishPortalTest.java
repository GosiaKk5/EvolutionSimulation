package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HellishPortalTest {

    @Test
    void HellishSimpleTest(){
        System.out.println();
        System.out.println("-------------------------------------");

        int width = 5;
        int height = 5;
        IMap map = new ToxicCorpsesMap(width,height, 0);
        Vector2d position = new Vector2d(4,4);
        int[] genotype = {0};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 10;
        int breedReadyEnergy = 0;
        int breedHandoverEnergy = 4;
        int minNumberOfMutations = 0;
        int maxNumberOfMutations = 1;
        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new HellishPortal(width, height, breedHandoverEnergy);

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

        int energyCheck = 5;

        map.placeAnimal(animal);

        System.out.println(map);
        animal.move();

        System.out.println(animal.getEnergy());
        assertEquals(animal.getOrientation(), genotype[0]);
        assertNotSame(animal.position(), position);
        assertEquals(animal.getEnergy(), energyCheck);

        System.out.println(map);

    }
}