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
import javafx.scene.text.Font;
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
    static final Color MOST_POPULAR_GENOTYPE_COLOR = Color.YELLOW;
    static final Font STATISTICS_TITLE_FONT = new Font(22);
    static final Font STATISTICS_TEXT_FONT = new Font(18);
    static final Font BUTTON_FONT = new Font(18);
    static final Insets BUTTON_INSETS = new Insets(10);
    private final Stage stage;
    private final VBox mapStatisticsBox;
    private final VBox animalStatisticsBox;
    private final Statistic mapStatistics;
    private boolean showMostPopularGenotype = false;

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

        this.mapStatistics = new Statistic(map, engine);

        int longerEdge = Math.max(height, width) + 1;
        this.cellSize = Math.round(GRID_PANE_SIZE / longerEdge);
        this.radius = cellSize*RADIUS_PROPORTION_TO_CELL_SIZE;

        this.gridPane = new GridPane();
        this.gridPane.setBackground(this.getBackgroundOfColor(GRID_PANE_COLOR));

        this.mapStatisticsBox = new VBox();
        this.animalStatisticsBox = new VBox();

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
        VBox asideContainer = new VBox(this.getButtonContainer(), this.mapStatisticsBox, this.animalStatisticsBox);
        asideContainer.setPadding(new Insets(40,0,0,40));


        HBox sceneContainer = new HBox(simulationAreaContainer, asideContainer);
        Insets insets = new Insets(20);
        sceneContainer.setPadding(insets);
        sceneContainer.setAlignment(Pos.CENTER);

        Scene scene = new Scene(sceneContainer, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    public HBox getButtonContainer(){

        Button buttonChangeSimulationPause = new Button("stop");

        buttonChangeSimulationPause.setPadding(BUTTON_INSETS);
        buttonChangeSimulationPause.setFont(BUTTON_FONT);

        Button buttonShowMostPopularGenotypes = new Button("najpopularniejsze genotypy");

        buttonShowMostPopularGenotypes.setPadding(BUTTON_INSETS);
        buttonShowMostPopularGenotypes.setFont(BUTTON_FONT);

        HBox buttonContainer = new HBox(buttonChangeSimulationPause, buttonShowMostPopularGenotypes);

        buttonChangeSimulationPause.setOnAction(event -> {
            this.engine.setPaused(!this.engine.isPaused());
            if(this.engine.isPaused()){
                buttonChangeSimulationPause.setText("start");
            }
            else{
                this.showMostPopularGenotype = false;
                buttonChangeSimulationPause.setText("stop");
            }
        });

        buttonShowMostPopularGenotypes.setOnAction(event -> {
            if(this.engine.isPaused()){
                this.showMostPopularGenotype = !this.showMostPopularGenotype;
                refresh();
            }
        });

        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        buttonContainer.setSpacing(10);

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

        System.out.println(animal);
        //most popular genotype
        if(this.showMostPopularGenotype && mapStatistics.getAnimalsWithMostPopular().contains(animal)){
            elementContainer.setBackground(this.getBackgroundOfColor(MOST_POPULAR_GENOTYPE_COLOR));
        }

        //manage animal following - only when simulation is paused
        elementContainer.setOnMouseClicked(event -> {
            if(this.engine.isPaused()){

                //following animal
                if(Objects.equals(this.followedAnimal, animal)){
                    this.followedAnimal = null;
                }
                else{
                    this.followedAnimal = animal;
                }
                this.refresh();
            }
        });

        //set background color for followed animal when simulation is not paused
        if(Objects.equals(this.followedAnimal, animal)){
            circle.setFill(FOLLOWED_ANIMAL_COLOR);
        }

        return elementContainer;
    }
    private void createMapStatistics(){

        Text title = new Text("\nstatystyki mapy\n");
        title.setFont(STATISTICS_TITLE_FONT);

        Text t1 = new Text("liczba wszystkich zwierzat: " + mapStatistics.getNoAnimals());
        Text t2 = new Text("liczba wszystkich roslin: " + mapStatistics.getNoPlants());
        Text t3 = new Text("liczba wolnych pol: " + mapStatistics.getNoFreeFields());
        Text t4 = new Text("najpopularniejszy genotyp: " + mapStatistics.getTheMostPopularGenotype());
        Text t5 = new Text("sredni poziom energii dla zyjacych zwierzat: " + mapStatistics.getAvgEnergy());
        Text t6 = new Text("srednia dlugosc zycia zwierzat dla martwych zwierzat: " + mapStatistics.getAvgDeathAge());

        t1.setFont(STATISTICS_TEXT_FONT);
        t2.setFont(STATISTICS_TEXT_FONT);
        t3.setFont(STATISTICS_TEXT_FONT);
        t4.setFont(STATISTICS_TEXT_FONT);
        t5.setFont(STATISTICS_TEXT_FONT);
        t6.setFont(STATISTICS_TEXT_FONT);

        this.mapStatisticsBox.getChildren().clear();
        this.mapStatisticsBox.getChildren().setAll(title, t1, t2, t3, t4, t5, t6);
    }
    private void createAnimalStatistics(){
        Text title = new Text("\nstatystyki zwierzatka\n");
        title.setFont(STATISTICS_TITLE_FONT);

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

        t1.setFont(STATISTICS_TEXT_FONT);
        t2.setFont(STATISTICS_TEXT_FONT);
        t3.setFont(STATISTICS_TEXT_FONT);
        t4.setFont(STATISTICS_TEXT_FONT);
        t5.setFont(STATISTICS_TEXT_FONT);
        t6.setFont(STATISTICS_TEXT_FONT);

        this.animalStatisticsBox.getChildren().clear();
        this.animalStatisticsBox.getChildren().setAll(title, t1, t2, t3, t4, t5, t6);
    }
    public void refresh() {
        Platform.runLater( () -> {
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
        this.refresh();
    }
}


