package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HellishPortal implements IChangePositionHandler {

    private int orientation = 0;
    private final int xBound;
    private final int yBound;
    private final int breedHandoverEnergy;
    public HellishPortal(int width, int height, int breedHandoverEnergy){
        this.orientation = 0;
        this.xBound = width - 1;
        this.yBound = height - 1;
        this.breedHandoverEnergy = breedHandoverEnergy;
    }
    @Override
    public Vector2d getNewPositionInMap(Vector2d position, Vector2d oldPosition, int orientation) {

        int oldX = position.x;
        int oldY = position.y;
        this.orientation = orientation;

        return new Vector2d(getNewXOrY(oldX, xBound), getNewXOrY(oldY, yBound));
    }

    @Override
    public int getNewOrientation() {
        return this.orientation;
    }

    @Override
    public int getNewEnergy(int animalEnergy) {
        return animalEnergy - this.breedHandoverEnergy;
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
