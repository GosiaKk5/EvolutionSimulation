package org.example;

public interface IMap {
    void placeAnimal(Animal animal);
    Object objectAt(Vector2d position);

    boolean isOccupied(Vector2d position);
    void addPlants(int noPlants);

    void removePlant(Vector2d position);

}
