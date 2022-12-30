package org.example.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.example.Animal;
import org.example.IMapElement;
import org.example.SimulationEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    final static int IMAGE_SIZE = 42;
    private ImageView image;
    private VBox elementContainer;

    private SimulationEngine engine;
    public GuiElementBox(IMapElement mapElement, SimulationEngine engine){

        // set the fill of the circle
        Text text = new Text(mapElement.toString());
        Label elementPosition = new Label(mapElement.getPosition().toString());
        this.elementContainer = new VBox(text, elementPosition);
        this.elementContainer.setAlignment(Pos.CENTER);

        this.engine = engine;

        this.elementContainer.setOnMouseClicked(event -> {
            if(this.engine.isPaused()){
                System.out.println(mapElement.getPosition());

                BackgroundFill background_fill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);

                // create Background
                Background background = new Background(background_fill);

                // set background
                this.elementContainer.setBackground(background);
            }
        });
    }

    public VBox getElementContainer(){
        return this.elementContainer;
    }
}
