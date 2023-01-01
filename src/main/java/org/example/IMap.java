package org.example;

import java.util.ArrayList;
import java.util.Map;

public interface IMap {
    void placeAnimal(Animal animal);
    Object objectAt(Vector2d position);
    boolean isOccupied(Vector2d position);
    void addPlants(int noPlants);
    void removePlant(Vector2d position);

    Plant plantAt(Vector2d position);

    Map<Vector2d, Plant> getPlants();
    ArrayList<Animal> animalsAt(Vector2d position);

    void removeAnimal(Animal animal);
    boolean inMap(Vector2d position);
    int getUpperBound();
    int getLowerBound();
    int getLeftBound();
    int getRightBound();

    int getNoFreeFields();
}
