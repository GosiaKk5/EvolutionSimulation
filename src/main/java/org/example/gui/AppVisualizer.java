package org.example.gui;

import org.example.Animal;
import org.example.IPositionChangeObserver;
import org.example.Vector2d;

public class AppVisualizer{
    private final App app;
    public AppVisualizer(App app){
        this.app = app;
    }
//    @Override
//    public void positionChanged(Animal element, Vector2d oldPosition, Vector2d newPosition) {
//        this.app.refresh();
//    }
    public void appRefresh(){
        this.app.refresh();
    }
}
