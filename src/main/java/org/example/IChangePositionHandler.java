package org.example;

public interface IChangePositionHandler {
    Vector2d getNewPositionInMap(Vector2d position, Vector2d oldPosition, int orientation);
    int getNewOrientation();
    int getNewEnergy(int animalEnergy);
}
