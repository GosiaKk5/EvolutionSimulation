package org.example;

public class Globe implements IChangePositionHandler {

    private int newOrientation;

    public Globe(){
        this.newOrientation = 0;
    }
    @Override
    public Vector2d getNewPositionInMap(Vector2d position, Animal animal, int lowerBound, int upperBound, int leftBound, int rightBound) {

        int newX = animal.getPosition().x;
        int newY = animal.getPosition().y;
        this.newOrientation = animal.getOrientation();

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


}
