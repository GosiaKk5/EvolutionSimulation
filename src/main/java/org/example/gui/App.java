package org.example.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.example.*;


public class App extends Application {

    final static int SCENE_WIDTH = 400;
    final static int SCENE_HEIGHT = 250;
    @Override
    public void init() throws Exception {

        super.init();
    }
    @Override
    public void start(Stage primaryStage){

        Text title = new Text("wybierz gotowa opcje\nlub\npodaj sciezke pliku z parametrami symulacji");
        title.setTextAlignment(TextAlignment.CENTER);
        VBox sceneContainer = new VBox(title, this.getStartButtons(), this.getPathTextFieldAndButton());
        sceneContainer.setSpacing(20);
        sceneContainer.setAlignment(Pos.CENTER);

        Scene scene = new Scene(sceneContainer, SCENE_WIDTH, SCENE_HEIGHT);
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
    public Button getOptionButton1(String buttonText){
        Button button = new Button(buttonText);
        button.setOnAction(event -> {
            try {

                int height = 30;
                int width = 30;
                int numberOfStartPlants = 0;
                int plantEnergy = 15;
                int numberOfPlantsGrowDaily = 5;
                int numberOfStartAnimals = 10;
                int startEnergy = 10;
                int breedReadyEnergy = 6;
                int breedHandoverEnergy = 5;
                int minNumberOfMutations = 0;
                int maxNumberOfMutations = 0;
                int genotypeLength = 3;

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

                int height = 3;
                int width = 3;
                int numberOfStartPlants = 0;
                int plantEnergy = 2;
                int numberOfPlantsGrowDaily = 0;
                int numberOfStartAnimals = 1;
                int startEnergy = 20;
                int breedReadyEnergy = 5;
                int breedHandoverEnergy = 3;
                int minNumberOfMutations = 0;
                int maxNumberOfMutations = 0;
                int genotypeLength = 12;

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

    public HBox getPathTextFieldAndButton(){

        TextField textField = new TextField();

        Button button = new Button("przeslij");
        HBox container = new HBox(textField, button);

        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        return container;
    }
}
