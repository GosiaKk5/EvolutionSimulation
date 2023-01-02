package org.example.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.*;

public class App extends Application {

    @Override
    public void init() throws Exception {

        super.init();

    }
    @Override
    public void start(Stage primaryStage){

        HBox sceneContainer = this.getStartButtons();

        Scene scene = new Scene(sceneContainer, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox getStartButtons(){
        Button buttonOption1 = this.getOptionButton1("option 1");
        Button buttonOption2 = this.getOptionButton2("option 2");
        Button buttonOption3 = this.getOptionButton3("option 3");


        HBox buttonContainer = new HBox(buttonOption1, buttonOption2, buttonOption3);

        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(10);
        return buttonContainer;
    }
    public Button getOptionButton1(String buttonText) {
        Button button = new Button(buttonText);
        button.setOnAction(event -> {
            try {

                int height = 7;
                int width = 7;
                int numberOfStartPlants = 3;
                int plantEnergy = 2;
                int numberOfPlantsGrowDaily = 5;
                int numberOfStartAnimals = 6;
                int startEnergy = 8;
                int breedReadyEnergy = 6;
                int breedHandoverEnergy = 5;
                int minNumberOfMutations = 0;
                int maxNumberOfMutations = 0;
                int genotypeLength = 7;

                IMap map = new EquatorialForestMap(width, height, numberOfStartPlants);
                IMutationHandler mutationHandler = new FullRandomness();
                IChangePositionHandler positionHandler = new Globe(width, height);
                IChangeOrientationHandler orientationHandler = new FullPredestination();

                SingleSimulationVisualizer visualizer = new SingleSimulationVisualizer(height,
                        width,
                        map,
                        plantEnergy,
                        numberOfPlantsGrowDaily,
                        numberOfStartAnimals,
                        startEnergy,
                        breedReadyEnergy,
                        breedHandoverEnergy,
                        minNumberOfMutations,
                        maxNumberOfMutations,
                        genotypeLength,
                        mutationHandler,
                        positionHandler,
                        orientationHandler);
                visualizer.startSingleSimulation();
            } catch (IllegalArgumentException ex) {
                System.err.println(ex);
                System.exit(1);
            }
        });
        return button;
    }

    public Button getOptionButton2(String buttonText){
        Button button = new Button(buttonText);
        button.setOnAction(event -> {
            try{

                int height = 30;
                int width = 30;
                int numberOfStartPlants = 0;
                int plantEnergy = 2;
                int numberOfPlantsGrowDaily = 3;
                int numberOfStartAnimals = 4;
                int startEnergy = 20;
                int breedReadyEnergy = 5;
                int breedHandoverEnergy = 1;
                int minNumberOfMutations = 0;
                int maxNumberOfMutations = 0;
                int genotypeLength = 1;

                IMap map = new EquatorialForestMap(width, height, numberOfStartPlants);
                IMutationHandler mutationHandler = new FullRandomness();
                IChangePositionHandler positionHandler = new HellishPortal(width, height, breedHandoverEnergy);
                IChangeOrientationHandler orientationHandler = new FullPredestination();

                SingleSimulationVisualizer visualizer = new SingleSimulationVisualizer(height,
                        width,
                        map,
                        plantEnergy,
                        numberOfPlantsGrowDaily,
                        numberOfStartAnimals,
                        startEnergy,
                        breedReadyEnergy,
                        breedHandoverEnergy,
                        minNumberOfMutations,
                        maxNumberOfMutations,
                        genotypeLength,
                        mutationHandler,
                        positionHandler,
                        orientationHandler);
                visualizer.startSingleSimulation();
            }
            catch(IllegalArgumentException ex){
                System.err.println(ex);
                System.exit(1);
            }
        });
        return button;
    }

    public Button getOptionButton3(String buttonText){
        Button button = new Button(buttonText);
        button.setOnAction(event -> {
            try{

                int height = 20;
                int width = 20;
                int numberOfStartPlants = 5;
                int plantEnergy = 10;
                int numberOfPlantsGrowDaily = 3;
                int numberOfStartAnimals = 6;
                int startEnergy = 10;
                int breedReadyEnergy = 6;
                int breedHandoverEnergy = 5;
                int minNumberOfMutations = 0;
                int maxNumberOfMutations = 0;
                int genotypeLength = 1;

                IMap map = new EquatorialForestMap(width, height, numberOfStartPlants);
                IMutationHandler mutationHandler = new FullRandomness();
                IChangePositionHandler positionHandler = new HellishPortal(width, height, breedHandoverEnergy);
                IChangeOrientationHandler orientationHandler = new FullPredestination();

                SingleSimulationVisualizer visualizer = new SingleSimulationVisualizer(height,
                        width,
                        map,
                        plantEnergy,
                        numberOfPlantsGrowDaily,
                        numberOfStartAnimals,
                        startEnergy,
                        breedReadyEnergy,
                        breedHandoverEnergy,
                        minNumberOfMutations,
                        maxNumberOfMutations,
                        genotypeLength,
                        mutationHandler,
                        positionHandler,
                        orientationHandler);
                visualizer.startSingleSimulation();
            }
            catch(IllegalArgumentException ex){
                System.err.println(ex);
                System.exit(1);
            }
        });
        return button;
    }
}
