package org.example;

public class FullPredestination implements IChangeOrientationHandler {
    //aributes: genotypeLength
    @Override
    public int changeOrientation(Animal animal) {
        int activeGenIndex = animal.getIndexOfActiveGen();
        int genotypeLength = animal.getGenotypeLength();

        int nextGenIndex = (activeGenIndex + 1) % genotypeLength;
        return nextGenIndex;
    }
}
