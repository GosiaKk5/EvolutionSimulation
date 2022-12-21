package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class LittleCraziness implements IChangePositionHandler{
    private final Random random;
    private final int[] probability;
    public LittleCraziness() {
        this.random = new Random();
        this.probability = new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1};
    }
    //aributes: genotypeLength, indexes

    @Override
    public int changePosition(Animal animal) {

        int genotypeLength = animal.getGenotypeLength();

        int indexProbability = random.nextInt(0, 10);
        int variant = this.probability[indexProbability];

        int nextGenIndex;

        //1 - fullPredistination
        //0 - littleCraziness
        if (variant == 1){

            int activeGenIndex = animal.getIndexOfActiveGen();
            nextGenIndex = (activeGenIndex + 1) % genotypeLength;
        }
        else{

            System.out.println();
            System.out.println("FIRE!");
            System.out.println();

            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < genotypeLength ; i++){
                if (animal.getIndexOfActiveGen() != i){
                    indexes.add(i);
                }
            }
            nextGenIndex = indexes.get(random.nextInt(0, genotypeLength - 1));
        }

        return nextGenIndex;
    }
}
