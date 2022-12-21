package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LittleCorrectTest {
    @Test
    void checkIfDifferenceIsOne(){
        int[] genotype = {0,0,0,0,0};
        int[] genotypeCopy = Arrays.copyOf(genotype, genotype.length);
        Integer[] indexes = {0,1,2};
        List<Integer> gens = Arrays.asList(indexes);

        IMutationHandler handler = new LittleCorrect();
        genotype = handler.mutate(genotype, gens);

        System.out.println("BEFORE: " + Arrays.toString(genotypeCopy));
        System.out.println("AFTER: " + Arrays.toString(genotype));

        for (Integer index : indexes){
            assertEquals(Math.abs(genotype[index] - genotypeCopy[index]), 1);
        }
    }
}