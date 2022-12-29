package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HellishPortal implements IChangePositionHandler {

    private int orientation = 0;
    public HellishPortal(){
        this.orientation = 0;
    }
    @Override
    public Vector2d getNewPositionInMap(Vector2d position, Animal animal, int lowerBound, int upperBound, int leftBound, int rightBound) {

        int oldX = position.x;
        int oldY = position.y;
        this.orientation = animal.getOrientation();

        return new Vector2d(getNewXOrY(oldX, rightBound), getNewXOrY(oldY, upperBound));
    }

    @Override
    public int getNewOrientation() {
        return this.orientation;
    }

    @Override
    public int getNewEnergy(Animal animal) {
        return animal.getEnergy() - animal.getBreedEnergy();
    }

    private int getNewXOrY(int oldXorY, int bound){
        List<Integer> possibleValues = new ArrayList<>();

        Random random = new Random();

        for(int i = 0; i < bound + 1; i++){
            possibleValues.add(i);
        }

        //wyciągam starą wartość, żeby jej nie wylosować
        possibleValues.remove(Integer.valueOf(oldXorY));

        int index = random.nextInt(0,bound);
        int newXorY = possibleValues.get(index);

        possibleValues.add(oldXorY);

        return newXorY;
    }
}
