package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FullPredestination implements IChangePositionHandler{
    //aributes: genotypeLength
    @Override
    public int changePosition(Animal animal) {
        int activeGenIndex = animal.getIndexOfActiveGen();
        int genotypeLength = animal.getGenotypeLength();

        int nextGenIndex = (activeGenIndex + 1) % genotypeLength;
        return nextGenIndex;
    }
}
