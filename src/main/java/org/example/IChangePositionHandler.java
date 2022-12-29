package org.example;

public interface IChangePositionHandler {
    Vector2d getNewPositionInMap(Vector2d position, Animal animal, int lowerBound, int upperBound, int leftBound, int rightBound);
    int getNewOrientation();
    int getNewEnergy(Animal animal);
}
