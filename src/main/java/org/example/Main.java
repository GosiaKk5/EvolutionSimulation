package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        /*
        IMutationHandler mutationHandler = new FullRandomness();
        IChangePositionHandler positionHandler = new LittleCraziness();
        int genotypeLength = 15;
        int breedEnergy = 5;
        Vector2d position = new Vector2d(0,0);

        Animal a1 = new Animal(1, position, mutationHandler, positionHandler, genotypeLength, breedEnergy);
        Animal a2 = new Animal(5, position, mutationHandler, positionHandler, genotypeLength, breedEnergy);
        */

        IMutationHandler mutationHandler = new FullRandomness();
        IChangePositionHandler positionHandler = new LittleCraziness();
        int genotypeLength = 15;
        int breedEnergy = 5;
        Vector2d position = new Vector2d(0,0);
        int[] genotype = {0,1,2,3,3,4,5,6,1,3};
        ToxicCorpsesMap map = new ToxicCorpsesMap(10,7, 10);

        Animal a1 = new Animal(map, genotype, 1, position, mutationHandler, positionHandler, genotypeLength, breedEnergy);
        Animal a2 = new Animal(map, genotype, 5, position, mutationHandler, positionHandler, genotypeLength, breedEnergy);

        map.placeAnimal(a1);
        map.placeAnimal(a2);
        System.out.println(map.animalsAt(position));

        System.out.println(map);
        a1.move();
        a1.changeOrientation();

        System.out.println(map);
        a1.move();
        a1.changeOrientation();

        System.out.println(map);




    }
}