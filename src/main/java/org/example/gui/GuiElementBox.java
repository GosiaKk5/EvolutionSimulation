package org.example.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.example.Animal;
import org.example.IMapElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    final static int IMAGE_SIZE = 42;
    private ImageView image;
    private VBox elementContainer;

    public GuiElementBox(IMapElement mapElement){

//        Circle circle = new Circle();
//
//        // set the position of center of the  circle
//        circle.setCenterX(100.0f);
//        circle.setCenterY(100.0f);
//
//        // set Radius of the circle
//        circle.setRadius(50.0f);

        // set the fill of the circle
        Text text = new Text(mapElement.toString());
        Label elementPosition = new Label(mapElement.getPosition().toString());
        this.elementContainer = new VBox(text, elementPosition);
        this.elementContainer.setAlignment(Pos.CENTER);
    }

    public VBox getElementContainer(){
        return this.elementContainer;
    }
}
