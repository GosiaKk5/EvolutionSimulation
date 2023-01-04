package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FullRandomness implements IMutationHandler {
    @Override
    public int[] mutate(int[] genotype, List<Integer> indexesOfGensToMutate) {

        Random random = new Random();

        List<Integer> gens = new ArrayList<>();
        for (int i = 0; i <= 7; i++){
            gens.add(i);
        }

        for (int index : indexesOfGensToMutate) {

            int removedGen = genotype[index];
            gens.remove(Integer.valueOf(removedGen));
            genotype[index] = gens.get(random.nextInt(0, 7));

            //removedGen goes back to the original index
            gens.add(removedGen);
        }

        return genotype;
    }
}
