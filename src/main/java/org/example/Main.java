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

        EquatorialForestMap map = new EquatorialForestMap(10,5, 50);
        System.out.println(map);
        map.removePlant(new Vector2d(0,0));
        System.out.println(map);
    }
}