package org.example;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMap implements IMap{

    protected int height;
    protected int width;

    protected Map<Vector2d, Plant> plants;
    protected Map<Vector2d, Animal[]> animals;

    public AbstractMap(int width, int height, int noStartPlants){
        this.height = height;
        this.width = width;
        this.plants = new HashMap<>();
    }



    public Object objectAt(Vector2d position){ //To dokonczyc

        if(plants.size() != 0){
            return plants.get(position);
        }
        return null;
    }
    public boolean isOccupied(Vector2d position){

        return objectAt(position) != null;
    }

    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(new Vector2d(0,0), new Vector2d(width-1, height-1));
    }

    public Map<Vector2d, Plant> getPlants(){
        return this.plants;
    }


}
