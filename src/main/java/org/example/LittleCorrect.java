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
            int newGen = genotype[index] + difference;

            switch(newGen){
                case -1 -> newGen = 7;
                case 8 -> newGen = 0;
            }

            genotype[index] = newGen;
        }

        return genotype;
    }
}
