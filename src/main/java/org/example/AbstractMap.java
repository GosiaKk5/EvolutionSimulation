package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMap implements IMap, IPositionChangeObserver{

    protected final int height;

    protected final int width;
    protected final Vector2d lowerLeft;
    protected final Vector2d upperRight;

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    protected Map<Vector2d, Plant> plants = new HashMap<>();
    protected Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();

    public AbstractMap(int width, int height, int noStartPlants){

        this.height = height;
        this.width = width;
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width - 1, height - 1);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                animals.put(new Vector2d(x,y), new ArrayList<>());
            }
        }
    }

    @Override
    public void placeAnimal(Animal animal) {

        Vector2d position = animal.getPosition();

        if(isPositionInMapBounds(position)){
            this.animals.get(position).add(animal);
            animal.addObserver(this);
        }
        else{
            throw new IllegalArgumentException(position + "is not on map ");
        }
    }

    public void removeAnimal(Animal animal){

        Vector2d position = animal.getPosition();
        animals.get(position).remove(animal);
    }

    public ArrayList<Animal> animalsAt(Vector2d position){

        if(animals.get(position) != null){
            return animals.get(position);
        }

        return null;
    }

    public Plant plantAt(Vector2d position){

        if(plants.size() != 0){
            return plants.get(position);
        }

        return null;
    }

    // do rysowania mapy
    public Object objectAt(Vector2d position){

        if (animals.get(position).size() != 0 ) {
            return animals.get(position).get(0);
        }

        return plants.get(position);
    }
    public boolean isOccupied(Vector2d position){

        return objectAt(position) != null;
    }

    public boolean isPositionInMapBounds(Vector2d position){

        return(position.follows(this.lowerLeft) && position.precedes(this.upperRight));
    }

    public String toString() {

        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(this.lowerLeft, this.upperRight);
    }

    public Map<Vector2d, Plant> getPlants(){
        return this.plants;
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {

        animals.get(oldPosition).remove(animal);
        animals.get(newPosition).add(animal);

    }



}
