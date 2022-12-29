package org.example;

public class SimulationEngine {

    private final int height;
    private final int width;
    private final int numberOfStartPlants;
    private final int plantEnergy;
    private final int numberOfPlantsGrowDaily;
    private final int numberOfStartAnimals;
    private final int startEnergy;
    private final int breedReadyEnergy;
    private final int breedHandoverEnergy;
    private final int minNumberOfMutations;
    private final int maxNumberOfMutations;
    private final int genotypeLength;

    IChangePositionHandler changePositionHandler;
    IChangeOrientationHandler changeOrientationHandler;
    IMutationHandler mutationHandler;
    IMap map;

    String variantGrowingPlants;

    String variantMutation;

    String variantOrientation;
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

    //STRINGI (do ustalenia) -> inaczej wyjątek:
    //variantMap -> Globe/Hell
    //variantGrowingPlant -> EquatorialForest/ToxicCorpses
    //variantMutation -> FullRandomness/LittleCorrect
    //variantOrientation -> FullPredestination/LittleCraziness

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

        //UWAGA W TYM MIEJSCU RZUCAMY WYJĄTAKI JEŻELI PODANE WARTOŚCI SA BLEDNE
        // height, widht > 0, width >= 5
        // wszystkie inty itd > 0
        // minNumberOfMutation <= max
        // number of plants < widht*height? nie wiem jak to jest zaimplementowane

        this.height = height;
        this.width = width;
        this.numberOfStartPlants = numberOfStartPlants;
        this.plantEnergy = plantEnergy;
        this.numberOfPlantsGrowDaily = numberOfPlantsGrowDaily;
        this.numberOfStartAnimals = numberOfStartAnimals;
        this.startEnergy = startEnergy;
        this.breedReadyEnergy = breedReadyEnergy;
        this.breedHandoverEnergy = breedHandoverEnergy;
        this.minNumberOfMutations = minNumberOfMutations;
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.genotypeLength = genotypeLength;

        switch(variantMap){
            case "Globe" -> { this.changePositionHandler = new Globe(); }
            case "Hell" -> {  this.changePositionHandler = new HellishPortal(); }
            default -> {
                System.out.println("NAZWA WARIANTU ZMIANY POZYCJI");
            }
        }

        switch(variantGrowingPlants){
            case "EquatorialForest" -> { this.map = new EquatorialForestMap(width, height, numberOfStartPlants); }
            case "ToxicCorpses" -> { this.map = new ToxicCorpsesMap(width, height, numberOfStartPlants); }
            default -> {
                System.out.println("NAZWA WARIANTU ZMIANY ORIENTACJI");
            }
        }

        switch(variantMutation){
            case "FullRandomness" -> { this.mutationHandler = new FullRandomness(); }
            case "LittleCorrect" -> { this.mutationHandler = new LittleCorrect(); }
            default -> {
                System.out.println("NAZWA WARIANTU MUTACJI");
            }
        }

        switch(variantOrientation){
            case "FullPredestination" -> { this.changeOrientationHandler = new FullPredestination(); }
            case "FullRandomness" -> { this.changeOrientationHandler = new LittleCraziness(); }
            default -> {
                System.out.println("NAZWA WARIANTU ORIENTACJI");
            }
        }
    }

    private void deleteDeadAnimals(){}
    private void moveAnimals(){}
    private void eatPlants(){}
    private void breedAnimals(){}
    private void growPlants(){}
}
