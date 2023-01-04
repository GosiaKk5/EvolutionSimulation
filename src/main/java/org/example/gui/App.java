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

        Button buttonOption1 = this.getOptionButton("opcja 1", "src/main/resources/variant1.txt");
        Button buttonOption2 = this.getOptionButton("opcja 2", "src/main/resources/variant2.txt");
        Button buttonOption3 = this.getOptionButton("opcja 3", "src/main/resources/variant3.txt");

        HBox buttonContainer = new HBox(buttonOption1, buttonOption2, buttonOption3);

        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(10);
        return buttonContainer;
    }
    public Button getOptionButton(String buttonText, String stringPath){

        Button button = new Button(buttonText);
        button.setOnAction(event -> {
            try {
                FileHandler fileHandler = new FileHandler(stringPath);
                fileHandler.getSimulationVisualizer().startSingleSimulation();

            } catch (IllegalArgumentException ex) {
                System.err.println(ex);
                System.exit(1);
            }
        });
        return button;
    }
    public HBox getPathTextFieldAndButton(){

        TextField textField = new TextField();

        Button buttonSubmit = new Button("przeslij");
        HBox container = new HBox(textField, buttonSubmit);

        buttonSubmit.setOnAction(event -> {
            String path = textField.getText();

            try{
                FileHandler fileHandler = new FileHandler(path);
                fileHandler.getSimulationVisualizer().startSingleSimulation();
            }
            catch(IllegalArgumentException ex){
                System.err.println(ex);
                System.exit(1);
            }
        });

        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        return container;
    }
}
