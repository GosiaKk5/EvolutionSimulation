package org.example.gui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.*;

public class SingleSimulationVisualizer implements INextSimulationDayObserver{
    private final IMap map;
    private final GridPane gridPane;
    private final int xBound;
    private final int yBound;
    static final int CELL_WIDTH = 60;
    static final int CELL_HEIGHT = 60;
    private final SimulationEngine engine;

    public SingleSimulationVisualizer(int height,
                                      int width,
                                      IMap map,
                                      int plantEnergy,
                                      int numberOfPlantsGrowDaily,
                                      int numberOfStartAnimals,
                                      int startEnergy,
                                      int breedReadyEnergy,
                                      int breedHandoverEnergy,
                                      int minNumberOfMutations,
                                      int maxNumberOfMutations,
                                      int genotypeLength,
                                      IMutationHandler mutationHandler,
                                      IChangePositionHandler positionHandler,
                                      IChangeOrientationHandler orientationHandler){
        this.map = map;
        this.xBound = width - 1;
        this.yBound = height - 1;
        this.engine = new SimulationEngine(map,
                positionHandler,
                plantEnergy,
                numberOfPlantsGrowDaily,
                numberOfStartAnimals,
                startEnergy,
                breedReadyEnergy,
                breedHandoverEnergy,
                minNumberOfMutations,
                maxNumberOfMutations,
                mutationHandler,
                genotypeLength,
                orientationHandler);
        this.engine.addObserver(this);
        this.gridPane = new GridPane();
    }

    public void start(){

        this.createNewSimulationView();
        Thread thread = new Thread(this.engine);
        thread.start();
    }
    public void createNewSimulationView(){

        this.createScene();

        VBox sceneContainer = new VBox(this.getButtonContainer(), this.gridPane);

        Stage newStage = new Stage();
        Scene scene = new Scene(sceneContainer, 400, 400);
        newStage.setScene(scene);
        newStage.show();
    }

    public HBox getButtonContainer(){
        Button buttonStop = new Button("stop");
        Button buttonContinue = new Button("continue");

        HBox buttonContainer = new HBox(buttonContinue, buttonStop);

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

    @Override
    public void dayChanged() {
        this.refresh();
    }
}


