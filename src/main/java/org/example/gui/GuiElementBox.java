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
import java.util.Objects;

public class GuiElementBox {

    private VBox elementContainer;
    private int containerSize;
    private int radius;


    public GuiElementBox(IMapElement mapElement, int containerSize){

        this.containerSize = containerSize;

        if(mapElement instanceof Animal){
            Circle circle = new Circle();
            circle.setRadius(containerSize*0.3);

            Text energy = new Text(String.valueOf(((Animal) mapElement).getEnergy()));
            this.elementContainer = new VBox(circle, energy);
            this.elementContainer.setAlignment(Pos.CENTER);
        }
        else{
            this.elementContainer = new VBox();
        }

        //Text text = new Text(mapElement.toString());
        //Label elementPosition = new Label(mapElement.getPosition().toString());

    }

    public void setBackgroundColor(VBox container, Color color){

        BackgroundFill background_fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        // create Background
        Background background = new Background(background_fill);
        // set background
        container.setBackground(background);
    }

    public VBox getElementContainer(){
        return this.elementContainer;
    }
}
