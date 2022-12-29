package org.example;

public class Globe implements IChangePositionHandler {

    @Override
    public Vector2d getNewPositionInMap(Vector2d position, Animal animal, int lowerBound, int upperBound, int leftBound, int rightBound) {

        int newX = animal.getPosition().x;
        int newY = animal.getPosition().y;

        //System.out.println(position);
        if(position.x < leftBound){
            newX = rightBound;
        }
        if(position.x > rightBound){
            newX = leftBound;
        }

        //System.out.println(new Vector2d(newX, newY));
        return new Vector2d(newX, newY);
    }
}
