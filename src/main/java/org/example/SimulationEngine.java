package org.example;

import java.util.ArrayList;
import java.util.List;

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
    private final List<Animal> animals;


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
        // maxnumber of mutation <= genotypelenght

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

        this.animals = new ArrayList<>();

        switch(variantMap){
            case "Globe" -> { this.changePositionHandler = new Globe(width, height); }
            case "Hell" -> {  this.changePositionHandler = new HellishPortal(width, height, breedHandoverEnergy); }
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

        System.out.println(this.map);
        this.addAnimals();

        System.out.println("ANIMALS ADDED:");
        System.out.println();
        System.out.println(this.map);

        System.out.println("ANIMALS: " + this.animals);
        System.out.println();
    }

    private void addAnimals(){

        int[] genotype = new int[genotypeLength];

        for(int i = 0; i < genotypeLength; i++){
            genotype[i] = 0;
        }

        int indexOfActiveGen = 0;

        IMutationHandler mutationHandler = this.mutationHandler;
        IChangeOrientationHandler orientationHandler = this.changeOrientationHandler;
        IChangePositionHandler positionHandler = this.changePositionHandler;
        Animal a1 = new Animal(this.map,
                            new Vector2d(0,0),
                            genotype,
                            this.genotypeLength,
                            indexOfActiveGen,
                            this.startEnergy,
                            this.breedHandoverEnergy,
                            this.mutationHandler,
                            this.changeOrientationHandler,
                            this.changePositionHandler);

        int[] genotype2 = new int[genotypeLength];

        for(int i = 0; i < genotypeLength; i++){
            genotype2[i] = 1;
        }

        Animal a2 = new Animal(this.map,
                            new Vector2d(3,3),
                            genotype2,
                            this.genotypeLength,
                            indexOfActiveGen,
                            this.startEnergy,
                            this.breedHandoverEnergy,
                            this.mutationHandler,
                            this.changeOrientationHandler,
                            this.changePositionHandler);

        //wyjątek na umieszczenie poza mapą

        this.map.placeAnimal(a1);
        this.animals.add(a1);
        this.map.placeAnimal(a2);
        this.animals.add(a2);
    }
    public void run(){
        this.moveAnimals();
    }
    private void deleteDeadAnimals(){}
    private void moveAnimals(){
        for(int i = 0; i < 5; i++){
            for(Animal animal : this.animals){
                animal.move();
                animal.changeOrientation();
            }
            System.out.println("i: " + i);
            System.out.println(map);
        }
    }
    private void eatPlants(){}
    private void breedAnimals(){}
    private void growPlants(){}
}
