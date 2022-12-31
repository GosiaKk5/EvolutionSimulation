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

    public GuiElementBox(IMapElement mapElement){

        Text text = new Text(mapElement.toString());
        Label elementPosition = new Label(mapElement.getPosition().toString());
        this.elementContainer = new VBox(text, elementPosition);
        this.elementContainer.setAlignment(Pos.CENTER);

    }

    public void setBackgroundColor(VBox container){

        BackgroundFill background_fill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
        // create Background
        Background background = new Background(background_fill);
        // set background
        container.setBackground(background);
    }

    public VBox getElementContainer(){
        return this.elementContainer;
    }
}
