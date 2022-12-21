package org.example;

import java.util.List;

public interface IMutationHandler {
    int[] mutate(int[] genotype, List<Integer> indexesOfGensToMutate);
}
