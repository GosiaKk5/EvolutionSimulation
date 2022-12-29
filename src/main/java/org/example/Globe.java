package org.example;

public class Globe implements IChangePositionHandler {

    private int newOrientation;
    private final int upperBound;
    private final int lowerBound;
    private final int rightBound;
    private final int leftBound;

    public Globe(int width, int height){
        this.newOrientation = 0;
        this.upperBound = height - 1;
        this.lowerBound = 0;
        this.leftBound = 0;
        this.rightBound = width - 1;
    }
    @Override
    public Vector2d getNewPositionInMap(Vector2d position, Vector2d oldPosition, int oldOrientation) {

        int newX = oldPosition.x;
        int newY = oldPosition.y;
        this.newOrientation = oldOrientation;

        //position;
        if(position.x < leftBound){
            newX = rightBound;
        }
        if(position.x > rightBound){
            newX = leftBound;
        }

        //orientation
        if(position.y < lowerBound){
            this.newOrientation = 0;
        }
        if(position.y > upperBound){
            this.newOrientation = 4;
        }

        Vector2d newPosition = new Vector2d(newX, newY);
        return newPosition;
    }

    @Override
    public int getNewOrientation() {
        return this.newOrientation;
    }

    @Override
    public int getNewEnergy(int animalEnergy) {
        return animalEnergy;
    }


}
