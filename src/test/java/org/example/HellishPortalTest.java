package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HellishPortalTest {

    @Test
    void HellishSimpleTest(){
        System.out.println();
        System.out.println("-------------------------------------");

        IMap map = new ToxicCorpsesMap(5,5, 0);
        Vector2d position = new Vector2d(4,4);
        int[] genotype = {0};
        int genotypeLength = genotype.length;
        int indexOfActiveGen = 0;
        int energy = 10;
        int breedEnergy = 4;
        IMutationHandler mutationHandler = new FullRandomness();
        IChangeOrientationHandler orientationHandler = new FullPredestination();
        IChangePositionHandler positionHandler = new HellishPortal();

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

        int energyCheck = 5;

        map.placeAnimal(animal);

        System.out.println(map);
        animal.move();

        System.out.println(animal.getEnergy());
        assertEquals(animal.getOrientation(), genotype[0]);
        assertNotSame(animal.getPosition(), position);
        assertEquals(animal.getEnergy(), energyCheck);

        System.out.println(map);

    }
}