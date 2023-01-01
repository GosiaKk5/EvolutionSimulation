package org.example.gui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.*;

import java.util.Arrays;
import java.util.Objects;

public class SingleSimulationVisualizer implements INextSimulationDayObserver{
    private final IMap map;
    private final GridPane gridPane;
    private final SimulationEngine engine;
    private Animal followedAnimal;
    private final int xBound;
    private final int yBound;
    final int cellSize;
    final double radius;
    static final int GRID_PANE_SIZE = 760;
    static final int SCENE_WIDTH = 1400;
    static final int SCENE_HEIGHT = 800;
    static final double RADIUS_PROPORTION_TO_CELL_SIZE = 0.3;
    static final Color GRID_PANE_COLOR = Color.grayRgb(220);
    static final Color PLANT_COLOR = Color.GREEN;
    static final Color FOLLOWED_ANIMAL_COLOR = Color.PINK;
    private final Stage stage;
    private final VBox mapStatistics;
    private final VBox animalStatistics;

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

        int longerEdge = Math.max(height, width) + 1;
        this.cellSize = Math.round(GRID_PANE_SIZE / longerEdge);
        this.radius = cellSize*RADIUS_PROPORTION_TO_CELL_SIZE;

        this.gridPane = new GridPane();
        this.gridPane.setBackground(this.getBackgroundOfColor(GRID_PANE_COLOR));

        this.mapStatistics = new VBox();
        this.animalStatistics = new VBox();

        this.stage = new Stage();
    }

    public void startSingleSimulation(){

        this.createSceneView();

        Thread thread = new Thread(this.engine);
        thread.start();
        stage.setOnHiding( event -> thread.interrupt());
    }
    public Background getBackgroundOfColor(Color color){
        BackgroundFill background_fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        return new Background(background_fill);
    }
    public void createSceneView(){

        this.setSimulationMapGripPane();
        this.createAnimalStatistics();
        this.createMapStatistics();

        VBox simulationAreaContainer = new VBox(this.gridPane);
        VBox asideContainer = new VBox(this.getButtonContainer(), this.mapStatistics, this.animalStatistics);
        asideContainer.setPadding(new Insets(0,0,0,20));

        HBox sceneContainer = new HBox(simulationAreaContainer, asideContainer);
        Insets insets = new Insets(20);
        sceneContainer.setPadding(insets);
        sceneContainer.setAlignment(Pos.CENTER);

        Scene scene = new Scene(sceneContainer, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    public HBox getButtonContainer(){
        //Button buttonStop = new Button("stop");
        //Button buttonContinue = new Button("continue");
        Button buttonChangeSimulationPause = new Button("stop");

        HBox buttonContainer = new HBox(buttonChangeSimulationPause);

        //zostawiam na wszselki wypadek na razie
        //buttonStop.setOnAction(event -> this.engine.pause());
        //buttonContinue.setOnAction(event -> this.engine.unPause());

        buttonChangeSimulationPause.setOnAction(event -> {
            this.engine.setPaused(!this.engine.isPaused());
            if(this.engine.isPaused()){
                buttonChangeSimulationPause.setText("start");
            }
            else{
                buttonChangeSimulationPause.setText("stop");
            }
        });

        return buttonContainer;
    }
    public void setSimulationMapGripPane(){

        this.addXYLabel();

        this.addColumns();
        this.addRows();
        this.addAnimalsAndGrass();

        gridPane.setGridLinesVisible(true);
    }
    public void addXYLabel(){
        Label labelXY = new Label("y/x");
        GridPane.setHalignment(labelXY, HPos.CENTER);

        gridPane.getColumnConstraints().add(new ColumnConstraints(cellSize));
        gridPane.getRowConstraints().add(new RowConstraints(cellSize));
        gridPane.add(labelXY, 0, 0);
    }
    public void addColumns(){
        for (int i = 1; i - 1 <= xBound; i++){
            Label label = new Label(String.valueOf( i - 1));

            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getColumnConstraints().add(new ColumnConstraints(cellSize));
            gridPane.add(label, i, 0);
        }
    }
    public void addRows(){
        for (int i = 1; yBound + 1 - i >= 0; i++){
            Label label = new Label(String.valueOf(yBound + 1 - i));
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.getRowConstraints().add(new RowConstraints(cellSize));
            gridPane.add(label, 0, i);
        }
    }
    public void addAnimalsAndGrass(){
        for (int x = 0; x <= xBound; x++) {
            for (int y = 0; y <= yBound; y++) {
                Vector2d position = new Vector2d(x, y);
                if (this.map.isOccupied(position)) {
                    Object mapElement = this.map.objectAt(position);
                    VBox elementContainer;

                    if(mapElement instanceof Animal){
                        elementContainer = this.getAnimalVBox((Animal) mapElement);
                    }
                    else{
                        elementContainer = this.getPlantVBox();
                    }

                    gridPane.add(elementContainer, position.x + 1, yBound - position.y + 1);
                    GridPane.setHalignment(elementContainer, HPos.CENTER);
                }
            }
        }
    }
    public VBox getPlantVBox(){

        VBox elementContainer = new VBox();
        elementContainer.setBackground(this.getBackgroundOfColor(PLANT_COLOR));

        return elementContainer;
    }
    public VBox getAnimalVBox(Animal animal){

        VBox elementContainer;
        Circle circle = new Circle(radius);

        //TODO: energy color levels, text is temporaty

        Text energyText = new Text(String.valueOf((animal.getEnergy())));
        elementContainer = new VBox(circle, energyText);
        elementContainer.setAlignment(Pos.CENTER);

        //manage animal following - only when simulation is paused
        elementContainer.setOnMouseClicked(event -> {
            if(this.engine.isPaused()){

                if(Objects.equals(this.followedAnimal, animal)){
                    this.followedAnimal = null;
                }
                else{
                    this.followedAnimal = animal;
                    elementContainer.setBackground(this.getBackgroundOfColor(FOLLOWED_ANIMAL_COLOR));
                }
                this.refresh();
            }
        });

        //set background color for followed animal when simulation is not paused
        if(Objects.equals(this.followedAnimal, animal)){
            elementContainer.setBackground(this.getBackgroundOfColor(FOLLOWED_ANIMAL_COLOR));
        }

        return elementContainer;
    }
    private void createMapStatistics(){
        Text title = new Text("statystyki mapy");

        Text t1 = new Text("liczba wszystkich zwierzat: ");
        Text t2 = new Text("liczba wszystkich roslin: ");
        Text t3 = new Text("liczba wolnych pol: ");
        Text t4 = new Text("najpopularniejszy genotyp: ");
        Text t5 = new Text("sredni poziom energii dla zyjacych zwierzat: ");
        Text t6 = new Text("srednia dlugosc zycia zwierzat dla martwych zwierzat: ");

        //return new VBox(title, new Text(""), t1, t2, t3, t4, t5, t6);
        this.mapStatistics.getChildren().clear();
        this.mapStatistics.getChildren().setAll(title, new Text(""), t1, t2, t3, t4, t5, t6);
    }
    private void createAnimalStatistics(){
        Text title = new Text("statystyki zwierzatka");

        String genotype = "";
        String activeGen = "";
        String energy = "";
        String eatenPlants = "";
        String children = "";
        String age = "";

        if(this.followedAnimal != null){
            genotype = Arrays.toString(this.followedAnimal.getGenotype());
            activeGen = String.valueOf(this.followedAnimal.getIndexOfActiveGen());
            energy = String.valueOf(this.followedAnimal.getEnergy());
            eatenPlants = "";
            children = String.valueOf(this.followedAnimal.getNoChildren());
            age = String.valueOf(this.followedAnimal.getAge());
        }
        Text t1 = new Text("genotyp: " + genotype);
        Text t2 = new Text("aktywny index genu: " + activeGen);
        Text t3 = new Text("energia: " + energy);
        Text t4 = new Text("zjedzone rosliny: " + eatenPlants);
        Text t5 = new Text("dzieci: " + children);
        Text t6 = new Text("liczba dni: " + age);

        this.animalStatistics.getChildren().clear();
        this.animalStatistics.getChildren().setAll(title, new Text(""), t1, t2, t3, t4, t5, t6);
    }
    public void refresh() {
        Platform.runLater( () -> {
            //System.out.println("\n\n\n\n\n\n\n\nREFRESH");
            this.gridPane.getChildren().clear();
            this.gridPane.getColumnConstraints().clear();
            this.gridPane.getRowConstraints().clear();
            gridPane.setGridLinesVisible(false);

            this.setSimulationMapGripPane();
            this.createAnimalStatistics();
            this.createMapStatistics();
        });
    }

    @Override
    public void dayChanged() {
        //System.out.println("REFRESH---------------------------------------------------------------------------------");
        this.refresh();
    }
}


