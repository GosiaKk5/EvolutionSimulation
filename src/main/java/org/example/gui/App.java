package org.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.*;

public class App extends Application {
    private IMap map;
    private GridPane gridPane;
    private int xBound;
    private int yBound;

//    private int xBound;
//    private int yBound;

    static final int CELL_WIDTH = 60;
    static final int CELL_HEIGHT = 60;

    private SimulationEngine engine;

    @Override
    public void init() throws Exception {

        super.init();

        try{

            int height = 5;
            int width = 5;
            String variantMap = "Globe";
            int numberOfStartPlants = 3;
            int plantEnergy = 2;
            int numberOfPlantsGrowDaily = 0;
            String variantGrowingPlants = "EquatorialForest";
            int numberOfStartAnimals = 4;
            int startEnergy = 8;
            int breedReadyEnergy = 6;
            int breedHandoverEnergy = 5;
            int minNumberOfMutations = 0;
            int maxNumberOfMutations = 0;
            String variantMutation = "FullRandomness";
            int genotypeLength = 1;
            String variantOrientation = "FullPredestination";

            AppVisualizer appVisualizer = new AppVisualizer(this);

            SimulationEngine engine = new SimulationEngine(height,
                    width,
                    variantMap,
                    numberOfStartPlants,
                    plantEnergy,
                    numberOfPlantsGrowDaily,
                    variantGrowingPlants,
                    numberOfStartAnimals,
                    startEnergy,
                    breedReadyEnergy,
                    breedHandoverEnergy,
                    minNumberOfMutations,
                    maxNumberOfMutations,
                    variantMutation,
                    genotypeLength,
                    variantOrientation,
                    appVisualizer);

            this.xBound = width - 1;
            this.yBound = height - 1;
            this.map = engine.getMap();

            this.engine = engine;
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            System.exit(1);
        }
    }
    @Override
    public void start(Stage primaryStage){

        this.gridPane = new GridPane();
        this.createScene();

        VBox sceneContainer = new VBox(this.getButtonContainer(), this.gridPane);

        Scene scene = new Scene(sceneContainer, 800, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public HBox getButtonContainer(){
        Button buttonStart = new Button("start");
        Button buttonStop = new Button("stop");
        Button buttonContinue = new Button("continue");

        HBox buttonContainer = new HBox(buttonStart, buttonContinue, buttonStop);

        buttonStart.setOnAction(event -> {
            Thread thread = new Thread(this.engine);
            thread.start();
        });

        buttonStop.setOnAction(event -> {
            if(!this.engine.isPaused()){
                this.engine.changePaused();
            }
        });

        buttonContinue.setOnAction(event -> {
            if(this.engine.isPaused()){
                this.engine.changePaused();
            }
        });

        return buttonContainer;
    }
    public void createScene(){

        this.addXYLabel();

        this.addColumns();
        this.addRows();
        this.addAnimalsAndGrass();

        gridPane.setGridLinesVisible(true);
    }
    public void addXYLabel(){
        Label labelXY = new Label("y/x");
        GridPane.setHalignment(labelXY, HPos.CENTER);

        gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        gridPane.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        gridPane.add(labelXY, 0, 0);
    }
    public void addColumns(){
        for (int i = 1; i - 1 <= xBound; i++){
            Label label = new Label(String.valueOf( i - 1));

            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            gridPane.add(label, i, 0);
        }
    }
    public void addRows(){
        for (int i = 1; yBound + 1 - i >= 0; i++){
            Label label = new Label(String.valueOf(yBound + 1 - i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            gridPane.add(label, 0, i);
        }
    }
    public void addAnimalsAndGrass(){
        for (int x = 0; x <= xBound; x++) {
            for (int y = 0; y <= yBound; y++) {
                Vector2d position = new Vector2d(x, y);
                if (this.map.isOccupied(position)) {
                    Object mapElement = this.map.objectAt(position);
                    GuiElementBox guiElementBox = new GuiElementBox((IMapElement) mapElement);
                    VBox elementContainer = guiElementBox.getElementContainer();
                    gridPane.add(elementContainer, position.x + 1, yBound - position.y + 1);
                    GridPane.setHalignment(elementContainer, HPos.CENTER);
                }
            }
        }
    }
    public void refresh() {
        Platform.runLater( () -> {
            System.out.println("\n\n\n\n\n\n\n\nREFRESH");
            this.gridPane.getChildren().clear();
            this.gridPane.getColumnConstraints().clear();
            this.gridPane.getRowConstraints().clear();
            gridPane.setGridLinesVisible(false);
            this.createScene();
        });
    }
}
