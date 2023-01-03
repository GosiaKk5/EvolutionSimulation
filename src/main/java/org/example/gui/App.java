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
                FileHandler fileHandler = new FileHandler("src/main/resources/variant1.txt");
                fileHandler.getSimulationVisualizer().startSingleSimulation();

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
                FileHandler fileHandler = new FileHandler("src/main/resources/variant2.txt");
                fileHandler.getSimulationVisualizer().startSingleSimulation();
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
                FileHandler fileHandler = new FileHandler("src/main/resources/variant3.txt");
                fileHandler.getSimulationVisualizer().startSingleSimulation();
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

//        try{
//            FileHandler fileHandler = new FileHandler("src/main/resources/variant3.txt");
//            fileHandler.getSimulationVisualizer().startSingleSimulation();
//        }
//        catch(IllegalArgumentException ex){
//            System.err.println(ex);
//            System.exit(1);
//        }

        return container;
    }
}
