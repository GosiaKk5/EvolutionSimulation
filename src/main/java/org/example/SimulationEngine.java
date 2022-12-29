package org.example;

public class SimulationEngine {

    //    Symulacja każdego dnia składa się z poniższej sekwencji kroków:
    //
    //    usunięcie martwych zwierząt z mapy,
    //    skręt i przemieszczenie każdego zwierzęcia,
    //    konsumpcja roślin na których pola weszły zwierzęta,
    //    rozmnażanie się najedzonych zwierząt znajdujących się na tym samym polu,
    //    wzrastanie nowych roślin na wybranych polach mapy.
    //    Daną symulację opisuje szereg parametrów:
    //
    //    wysokość i szerokość mapy,
    //    wariant mapy (wyjaśnione w sekcji poniżej),
    //    startowa liczba roślin,
    //    energia zapewniana przez zjedzenie jednej rośliny,
    //    liczba roślin wyrastająca każdego dnia,
    //    wariant wzrostu roślin (wyjaśnione w sekcji poniżej),
    //    startowa liczba zwierzaków,
    //    startowa energia zwierzaków,
    //    energia konieczna, by uznać zwierzaka za najedzonego (i gotowego do rozmnażania),
    //    energia rodziców zużywana by stworzyć potomka,
    //    minimalna i maksymalna liczba mutacji u potomków (może być równa 0),
    //    wariant mutacji (wyjaśnione w sekcji poniżej),
    //    długość genomu zwierzaków,
    //    wariant zachowania zwierzaków (wyjaśnione w sekcji poniżej)

    //variantMap -> globe/hell
    //variantGrowingPlant -> equatorial/toxicCorpses
    //variantMutation -> randomness/littleCorrect
    //variantOrientation -> predestination/littleCraziness
    public SimulationEngine(int height,
                            int width,
                            String variantMap,
                            int numberOfStartPlants,
                            int plantEnergy,
                            int numberOfPlantsGrowDaily,
                            String variantGrowingPlants,
                            int numberOfStartAnimals,
                            int startEnergy,
                            int breedReadyEnergy,
                            int breedHandoverEnergy,
                            int minNumberOfMutations,
                            int maxNumberOfMutations,
                            String variantMutation,
                            int genotypeLength,
                            String variantOrientation){

    }

    private void deleteDeadAnimals(){}
    private void moveAnimals(){}
    private void eatPlants(){}
    private void breedAnimals(){}
    private void growPlants(){}
}
