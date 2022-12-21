package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FullRandomnessTest {
    @Test
    void checkIfMutatedToOtherGene(){
        int[] genotype = {0,0,0,0,0};
        int[] genotypeCopy = Arrays.copyOf(genotype, genotype.length);
        Integer[] indexes = {0,1,2};
        List<Integer> gens = Arrays.asList(indexes);

        IMutationHandler handler = new FullRandomness();
        genotype = handler.mutate(genotype, gens);

        System.out.println("BEFORE: " + Arrays.toString(genotypeCopy));
        System.out.println("AFTER: " + Arrays.toString(genotype));

        for (Integer index : indexes){
            assertNotEquals(genotype[index], genotypeCopy[index]);
        }
    }
}