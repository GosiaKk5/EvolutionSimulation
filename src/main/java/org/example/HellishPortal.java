package org.example;

public class HellishPortal implements IChangePositionHandler {

    @Override
    public Vector2d getNewPositionInMap(Vector2d position, Animal animal, int lowerBound, int upperBound, int leftBound, int rightBound) {
        return new Vector2d(0,0);
    }

    @Override
    public int getNewOrientation() {
        return 0;
    }
}
