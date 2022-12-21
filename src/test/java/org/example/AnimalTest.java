package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void moveAndChangePositionSimpleTest() {
        int[] genotype = {2,2,2,2};
        Animal animal = new Animal(genotype, 10, new Vector2d(0,0), new FullRandomness(), new FullPredestination(), genotype.length, 5);
        Vector2d[] positionCheck = {
                new Vector2d(1,0),
                new Vector2d(1,-1),
                new Vector2d(0,-1),
                new Vector2d(0,0)
        };
        int[] orientationCheck = {2,4,6,0};


        //System.out.println(animal.getPosition());
        for(int i = 0; i < 4; i++){
            animal.changeOrientation();
            animal.move();
//            System.out.println("POSITION: " + animal.getPosition());
//            System.out.println("ORIENTATION: " + animal.getOrientation());
//            System.out.println();
            assertEquals(animal.getPosition(), positionCheck[i]);
            assertEquals(animal.getOrientation(), orientationCheck[i]);
        }
    }

    @Test
    void moveAndChangePositionSimpleTest2() {
        int[] genotype = {0,1,2,3,3,4,5,6,1,3};
        Animal animal = new Animal(genotype, 10, new Vector2d(0,0), new FullRandomness(), new FullPredestination(), genotype.length, 5);
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


        //System.out.println(animal.getPosition());
        for(int i = 0; i < genotype.length; i++){
            //System.out.println("i: " + i);
            //System.out.println("ORIENTATION: " + animal.getOrientation());
            animal.move();
            animal.changeOrientation();

            //System.out.println("POSITION: " + animal.getPosition());

            assertEquals(animal.getPosition(), positionCheks[i]);
            assertEquals(animal.getOrientation(), orientationCheck[i]);

            //System.out.println();
        }
    }
}