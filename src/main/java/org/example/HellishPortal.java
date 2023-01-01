package org.example;

import java.util.ArrayList;
import java.util.Random;

public class HellishPortal implements IChangePositionHandler {

    private int orientation;
    private final int xBound;
    private final int yBound;
    private final int breedHandoverEnergy;
    private final ArrayList<Integer> ox;
    private final ArrayList<Integer> oy;
    private final Random random;

    public HellishPortal(int width, int height, int breedHandoverEnergy){
        this.orientation = 0;
        this.xBound = width - 1;
        this.yBound = height - 1;
        this.breedHandoverEnergy = breedHandoverEnergy;

        this.ox = new ArrayList<>();
        for(int i = 0; i < xBound + 1; i++){
            ox.add(i);
        }

        this.oy = new ArrayList<>();
        for(int i = 0; i < yBound + 1; i++){
            oy.add(i);
        }

        this.random = new Random();
    }
    @Override
    public Vector2d getNewPositionInMap(Vector2d position, Vector2d oldPosition, int orientation) {

        int oldX = oldPosition.x;
        int oldY = oldPosition.y;
        this.orientation = orientation;

        return new Vector2d(getNewXOrY(oldX, this.xBound, this.ox), getNewXOrY(oldY, this.yBound, this.oy));
    }

    @Override
    public int getNewOrientation() {
        return this.orientation;
    }

    @Override
    public int getNewEnergy(int animalEnergy) {
        return animalEnergy - this.breedHandoverEnergy;
    }

    private int getNewXOrY(int OldXOrY, int bound, ArrayList<Integer> possibleValues){

        possibleValues.remove(Integer.valueOf(OldXOrY));

        int index = this.random.nextInt(0, bound);
        int newXorY = possibleValues.get(index);

        possibleValues.add(OldXOrY);

        return newXorY;
    }
}
