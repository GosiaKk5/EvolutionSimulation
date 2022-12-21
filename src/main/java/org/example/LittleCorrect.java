package org.example;

import java.util.List;
import java.util.Random;

public class LittleCorrect implements IMutationHandler {
    @Override
    public int[] mutate(int[] genotype, List<Integer> indexesOfGensToMutate) {

        Random random = new Random();
        int[] upOrDown = {-1, 1};

        for (int index : indexesOfGensToMutate){
            int difference = upOrDown[random.nextInt(0,2)];
            genotype[index] += difference;
        }

        return genotype;
    }
}
