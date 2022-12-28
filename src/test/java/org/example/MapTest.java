package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    IMutationHandler mutationHandler = new FullRandomness();
    IChangePositionHandler positionHandler = new LittleCraziness();
    int genotypeLength = 15;
    int breedEnergy = 5;
    Vector2d position = new Vector2d(0,0);
    int[] genotype = {0,1,2,3,3,4,5,6,1,3};
    ToxicCorpsesMap map = new ToxicCorpsesMap(10,10, 10);
    Animal a1 = new Animal(map, genotype, 1, new Vector2d(2,2), mutationHandler, positionHandler, genotypeLength, breedEnergy);
    Animal a2 = new Animal(map, genotype, 5,  new Vector2d(2,3), mutationHandler, positionHandler, genotypeLength, breedEnergy);
    Animal a3 = new Animal(map, genotype, 5,  new Vector2d(2,3), mutationHandler, positionHandler, genotypeLength, breedEnergy);
    Animal a4 = new Animal(map, genotype, 5,  new Vector2d(5,5), mutationHandler, positionHandler, genotypeLength, breedEnergy);

   @Test
   public void placeAnimalTest(){

       assertArrayEquals(new Animal[]{}, map.animalsAt(new Vector2d(5,5) ).toArray());
       map.placeAnimal(a4);
       assertArrayEquals(new Animal[]{a4}, map.animalsAt(new Vector2d(5,5) ).toArray());

   }

    @Test
    public void animalsAtTest() {

        ArrayList<Animal> animals1 = new ArrayList<>();
        animals1.add(a1);

        ArrayList<Animal> animals2 = new ArrayList<>();
        animals2.add(a2);
        animals2.add(a3);

        map.placeAnimal(a1);
        map.placeAnimal(a2);
        map.placeAnimal(a3);


        assertArrayEquals(animals1.toArray(), map.animalsAt(new Vector2d(2,2) ).toArray());
        assertArrayEquals(animals2.toArray(), map.animalsAt(new Vector2d(2,3) ).toArray());
    }

    @Test
    public void removeAnimalTest(){
        map.placeAnimal(a1);
        map.placeAnimal(a2);
        map.placeAnimal(a3);

        assertArrayEquals(new Animal[]{a1}, map.animalsAt(new Vector2d(2,2) ).toArray());
        map.removeAnimal(a1);
        assertArrayEquals(new Animal[]{}, map.animalsAt(new Vector2d(2,2) ).toArray());

        assertArrayEquals(new Animal[]{a2,a3}, map.animalsAt(new Vector2d(2,3) ).toArray());
        map.removeAnimal(a2);
        assertArrayEquals(new Animal[]{a3}, map.animalsAt(new Vector2d(2,3) ).toArray());
        map.removeAnimal(a3);
        assertArrayEquals(new Animal[]{}, map.animalsAt(new Vector2d(2,3) ).toArray());
    }
}
