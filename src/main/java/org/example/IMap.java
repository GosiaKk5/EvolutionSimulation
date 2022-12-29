package org.example;

import java.util.ArrayList;

public interface IMap {
    void placeAnimal(Animal animal);
    Object objectAt(Vector2d position);
    boolean isOccupied(Vector2d position);
    void addPlants(int noPlants);
    void removePlant(Vector2d position);
    ArrayList<Animal> animalsAt(Vector2d position);
    boolean inMap(Vector2d position);

}
